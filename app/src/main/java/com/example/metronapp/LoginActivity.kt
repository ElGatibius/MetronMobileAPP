package com.example.metronapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Programacion de Login
        sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE)
        val emailAddress = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("pass", null)
        val emailDigitado = findViewById<EditText>(R.id.loginEditTextEmail)
        val passDigitada = findViewById<EditText>(R.id.loginEditTextPassword)
        val buttonLogin = findViewById<Button>(R.id.loginButtonLogin)
        buttonLogin.setOnClickListener {
            validarLogin(emailAddress, password, emailDigitado, passDigitada)
        }






        // Programacion de Boton Forgot Password
        val buttonForgotPass = findViewById<TextView>(R.id.loginTextForgotPass)
        buttonForgotPass.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        }

        // Programacion de Boton Register
        val buttonRegister = findViewById<TextView>(R.id.loginTextRegister)
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
    private fun validarLogin(
        emailAddress: String?,
        password: String?,
        emailDigitado: EditText,
        passDigitada: EditText
    ){
        if (emailAddress.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
        }
        val email = emailDigitado.text.toString()
        val pass = passDigitada.text.toString()
        if (email == emailAddress && pass == password) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }

    }

}

