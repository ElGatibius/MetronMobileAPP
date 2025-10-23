package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.databinding.FragmentTipoAgendamientoBinding

class TipoAgendamientoFragment : Fragment() {

    private var _binding: FragmentTipoAgendamientoBinding? = null
    private val binding get() = _binding!!

    // Variable para almacenar el tipo de servicio (calibración o ajuste)
    private var serviceType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recibir el tipo de servicio desde los argumentos
        arguments?.let {
            serviceType = it.getString(ARG_SERVICE_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTipoAgendamientoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar clics de los botones
        binding.btnEmergencia.setOnClickListener {
            // Navegar al siguiente fragment (o activity) para agendar emergencia
            navigateToSchedule("Emergencia")
        }

        binding.btnPreventivo.setOnClickListener {
            // Navegar al siguiente fragment (o activity) para agendar preventivo
            navigateToSchedule("Preventivo")
        }
    }

    private fun navigateToSchedule(tipoAgendamiento: String) {
        // Navegar al fragment del formulario de agendamiento
        val fragment = FormularioAgendamientoFragment.newInstance(serviceType ?: "", tipoAgendamiento)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SERVICE_TYPE = "service_type"

        // Método para crear una nueva instancia del fragment con argumentos
        fun newInstance(serviceType: String): TipoAgendamientoFragment {
            val fragment = TipoAgendamientoFragment()
            val args = Bundle()
            args.putString(ARG_SERVICE_TYPE, serviceType)
            fragment.arguments = args
            return fragment
        }
    }
}