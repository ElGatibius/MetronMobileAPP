package com.example.metronapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE)

        setupOnClickedListeners()
    }

    private fun setupOnClickedListeners() {
        val editTextRazonSocial = findViewById<EditText>(R.id.registerEditTextRazonSocial)
        val editTextNIT = findViewById<EditText>(R.id.registerEditTextNIT)
        val editTextEmail = findViewById<EditText>(R.id.registerEditTextEmail)
        val editTextRepName = findViewById<EditText>(R.id.registerEditTextRepName)
        val editTextPass = findViewById<EditText>(R.id.registerEditTextPass)
        val editTextConfirmPass = findViewById<EditText>(R.id.registerEditTextConfirmPass)
        val buttonRegister = findViewById<Button>(R.id.registerBtn)

        buttonRegister.setOnClickListener {
            val razonSocial = editTextRazonSocial.text.toString().trimEnd()
            val nit = editTextNIT.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val repName = editTextRepName.text.toString().trimEnd()
            val pass = editTextPass.text.toString()
            val confirmPass = editTextConfirmPass.text.toString()

            if(validarDatos(razonSocial, nit, email, repName, pass, confirmPass)) {
                guardarDatos(razonSocial, nit, email, repName, pass, confirmPass)
            }

        }

    }

    private fun validarDatos(
    razonSocial: String,
    nit: String,
    email: String,
    repName: String,
    pass: String,
    confirmPass: String
    ) : Boolean
    {
        if (razonSocial.isEmpty()) {
            Toast.makeText(this, "Ingrese una razon social", Toast.LENGTH_SHORT).show()
            return false
        }
        if (nit.isEmpty()) {
            Toast.makeText(this, "Ingrese un NIT", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Ingrese un email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (repName.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pass.isEmpty()) {
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (confirmPass.isEmpty()) {
            Toast.makeText(this, "Confirme su contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pass != confirmPass) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun guardarDatos(
        razonSocial: String,
        nit: String,
        email: String,
        repName: String,
        pass: String,
        confirmPass: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString("razonSocial", razonSocial)
        editor.putString("nit", nit)
        editor.putString("email", email)
        editor.putString("repName", repName)
        editor.putString("pass", pass)
        editor.putString("confirmPass", confirmPass)
        editor.apply()
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}

