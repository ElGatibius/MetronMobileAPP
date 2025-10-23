package com.example.metronapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.databinding.FragmentEditarPerfilBinding

class EditarPerfilFragment : Fragment() {

    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserName", Context.MODE_PRIVATE)

        // Cargar datos actuales
        loadUserData()

        // Configurar el botón de guardar
        binding.btnGuardarPerfil.setOnClickListener {
            saveUserData()
        }
    }

    private fun loadUserData() {
        // Obtener datos actuales de SharedPreferences
        val razonSocial = sharedPreferences.getString("razonSocial", "") ?: ""
        val nit = sharedPreferences.getString("nit", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val repName = sharedPreferences.getString("repName", "") ?: ""

        // Mostrar datos en los EditText
        binding.etRazonSocial.setText(razonSocial)
        binding.etNit.setText(nit)
        binding.etEmail.setText(email)
        binding.etRepName.setText(repName)
    }

    private fun saveUserData() {
        // Obtener nuevos valores de los EditText
        val nuevaRazonSocial = binding.etRazonSocial.text.toString().trim()
        val nuevoNit = binding.etNit.text.toString().trim()
        val nuevoEmail = binding.etEmail.text.toString().trim()
        val nuevoRepName = binding.etRepName.text.toString().trim()

        // Validar campos obligatorios
        if (nuevaRazonSocial.isEmpty() || nuevoNit.isEmpty() || nuevoEmail.isEmpty() || nuevoRepName.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar formato de email
        if (!isValidEmail(nuevoEmail)) {
            Toast.makeText(requireContext(), "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar en SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("razonSocial", nuevaRazonSocial)
        editor.putString("nit", nuevoNit)
        editor.putString("email", nuevoEmail)
        editor.putString("repName", nuevoRepName)
        editor.apply()

        // Mostrar mensaje de éxito
        Toast.makeText(requireContext(), "Perfil actualizado exitosamente", Toast.LENGTH_SHORT).show()

        // Regresar al fragment anterior
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}