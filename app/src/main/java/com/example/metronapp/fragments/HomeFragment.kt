package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hacer las tarjetas clickeables
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Card Agendar Cita - navegar al fragment de agendar cita
        val agendarCitaCard = requireView().findViewById<View>(R.id.card_agendar_cita)
        agendarCitaCard?.setOnClickListener {
            navigateToAgendarCita()
        }

        // Card Revisar Citas - navegar al fragment de revisar citas
        val revisarCitasCard = requireView().findViewById<View>(R.id.card_revisar_citas)
        revisarCitasCard?.setOnClickListener {
            navigateToRevisarCitas()
        }
    }

    private fun navigateToAgendarCita() {
        val fragment = AgendarCitaFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToRevisarCitas() {
        val fragment = RevisarCitasFragment()
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