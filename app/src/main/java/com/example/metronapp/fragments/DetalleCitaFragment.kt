package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.metronapp.data.Cita
import com.example.metronapp.databinding.FragmentDetalleCitaBinding

class DetalleCitaFragment : Fragment() {

    private var _binding: FragmentDetalleCitaBinding? = null
    private val binding get() = _binding!!

    private lateinit var cita: Cita

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cita = it.getSerializable(ARG_CITA) as Cita  // Cambia getParcelable por getSerializable
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        binding.tvDetalleTipoAgendamiento.text = cita.tipoAgendamiento
        binding.tvDetalleFechaHora.text = "${cita.fecha} - ${cita.hora}"
        binding.tvDetalleServicio.text = cita.tipoServicio
        binding.tvDetalleUbicacion.text = cita.ubicacion
        binding.tvDetalleDescripcion.text = cita.descripcion
        binding.tvDetalleCosto.text = cita.costo
    }

    private fun setupClickListeners() {
        binding.btnCancelar.setOnClickListener {
            showCancelConfirmation()
        }

        binding.btnReagendar.setOnClickListener {
            // Por ahora solo mensaje, luego podrías navegar al formulario de agendamiento
            Toast.makeText(
                requireContext(),
                "Función de reagendar próximamente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showCancelConfirmation() {
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Cancelar Cita")
            .setMessage("¿Estás seguro de que quieres cancelar esta cita?")
            .setPositiveButton("Sí, cancelar") { dialog, which ->
                cancelAppointment()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun cancelAppointment() {
        Toast.makeText(
            requireContext(),
            "Cita cancelada exitosamente",
            Toast.LENGTH_SHORT
        ).show()

        // Regresar a la lista de citas
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_CITA = "cita"

        fun newInstance(cita: Cita): DetalleCitaFragment {
            val fragment = DetalleCitaFragment()
            val args = Bundle()
            args.putSerializable(ARG_CITA, cita)  // Cambia putParcelable por putSerializable
            fragment.arguments = args
            return fragment
        }
    }
}