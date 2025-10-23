package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.databinding.FragmentAgendarCitaBinding

class AgendarCitaFragment : Fragment() {

    private var _binding: FragmentAgendarCitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendarCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar clics de los botones
        binding.btnCalibracion.setOnClickListener {
            // Navegar al siguiente fragment para calibración
            navigateToServiceDetail("Calibración de surtidores")
        }

        binding.btnAjustePresion.setOnClickListener {
            // Navegar al siguiente fragment para ajuste de presión
            navigateToServiceDetail("Ajuste de presión")
        }
    }

    private fun navigateToServiceDetail(serviceType: String) {
        // Navegar al fragment de tipo de agendamiento
        val fragment = TipoAgendamientoFragment.newInstance(serviceType)
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