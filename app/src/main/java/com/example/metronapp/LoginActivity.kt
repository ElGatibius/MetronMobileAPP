package com.example.metronapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

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