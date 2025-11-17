package com.example.metronapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var btnGoogle: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    companion object{
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }

    // Credenciales del admin por defecto
    private val adminEmail = "admin@metron.com"
    private val adminPassword = "Admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Programación de Login
        sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE)
        val emailAddress = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("pass", null)
        val emailDigitado = findViewById<EditText>(R.id.loginEditTextEmail)
        val passDigitada = findViewById<EditText>(R.id.loginEditTextPassword)
        val buttonLogin = findViewById<Button>(R.id.loginButtonLogin)

        buttonLogin.setOnClickListener {
            validarLogin(emailAddress, password, emailDigitado, passDigitada)
        }

        // Programación de Boton Forgot Password
        val buttonForgotPass = findViewById<TextView>(R.id.loginTextForgotPass)
        buttonForgotPass.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        }

        // Programación de Boton Register
        val buttonRegister = findViewById<TextView>(R.id.loginTextRegister)
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //Programacion de ingreso google
        btnGoogle = findViewById(R.id.loginButtonGoogle)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("822649312545-1fniv3n958b5soejnuqlot4ilf3fftp7.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso) // Inicializada aquí


        btnGoogle.setOnClickListener {
            signInWithGoogle()

        }

    }
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Login fallido", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun validarLogin(
        emailAddress: String?,
        password: String?,
        emailDigitado: EditText,
        passDigitada: EditText
    ) {
        val email = emailDigitado.text.toString()
        val pass = passDigitada.text.toString()

        // Verificar si es admin
        if (email == adminEmail && pass == adminPassword) {
            // Guardar información del admin en SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_admin", true)
            editor.putString("email", email)
            editor.putString("user_role", "admin")
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Validación para usuarios normales
        if (emailAddress.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
            return
        }

        if (email == emailAddress && pass == password) {
            // Guardar información de usuario normal
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_admin", false)
            editor.putString("user_role", "cliente")
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }
}