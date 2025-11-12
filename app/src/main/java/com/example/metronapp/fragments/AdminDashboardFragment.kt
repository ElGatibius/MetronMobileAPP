package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.databinding.FragmentAdminDashboardBinding

class AdminDashboardFragment : Fragment() {

    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        loadDashboardData()
    }

    private fun setupClickListeners() {
        binding.btnGestionCitas.setOnClickListener {
            navigateToGestionCitas()
        }

        binding.btnGestionClientes.setOnClickListener {
            navigateToGestionClientes()
        }

        binding.btnReportes.setOnClickListener {
           navigateToReportes()
        }
    }

    private fun loadDashboardData() {
        // Por ahora, datos de ejemplo
        // En una app real, aquí cargarías datos de una base de datos o API
        binding.tvTotalCitas.text = "25"
        binding.tvIngresosTotales.text = "$3.250.000"
        binding.tvCitasEmergencia.text = "15"
        binding.tvCitasPreventivo.text = "10"
    }

    private fun navigateToGestionCitas() {
        val fragment = AdminGestionCitasFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToGestionClientes() {
        val fragment = AdminGestionClientesFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToReportes() {
        val fragment = AdminReportesFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}