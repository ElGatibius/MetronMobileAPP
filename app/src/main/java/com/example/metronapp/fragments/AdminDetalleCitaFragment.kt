package com.example.metronapp.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.data.Cita
import com.example.metronapp.databinding.FragmentAdminDetalleCitaBinding

class AdminDetalleCitaFragment : Fragment() {

    private var _binding: FragmentAdminDetalleCitaBinding? = null
    private val binding get() = _binding!!

    private lateinit var cita: Cita

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cita = it.getSerializable(DetalleCitaFragment.Companion.ARG_CITA) as Cita
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDetalleCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        // Configurar la información de la cita
        binding.tvClienteNombre.text = cita.clienteNombre
        binding.tvClienteEmail.text = cita.clienteEmail
        binding.tvTipoServicio.text = cita.tipoServicio
        binding.tvTipoAgendamiento.text = cita.tipoAgendamiento
        binding.tvFechaHora.text = "${cita.fecha} - ${cita.hora}"
        binding.tvUbicacion.text = cita.ubicacion
        binding.tvDescripcion.text = cita.descripcion
        binding.tvCosto.text = cita.costo

        // Configurar estado actual
        updateEstadoUI(cita.estado)

        // Mostrar botones según el estado actual
        setupEstadoButtons(cita.estado)
    }

    private fun updateEstadoUI(estado: String) {
        binding.tvEstadoActual.text = "Estado: $estado"

        when (estado) {
            "Pendiente" -> binding.tvEstadoActual.setBackgroundResource(R.drawable.estado_pendiente_background)
            "Confirmada" -> binding.tvEstadoActual.setBackgroundResource(R.drawable.estado_confirmada_background)
            "Completada" -> binding.tvEstadoActual.setBackgroundResource(R.drawable.estado_completada_background)
            "Cancelada" -> binding.tvEstadoActual.setBackgroundResource(R.drawable.estado_cancelada_background)
        }
    }

    private fun setupEstadoButtons(estado: String) {
        // Ocultar todos los botones primero
        binding.btnConfirmar.visibility = View.GONE
        binding.btnCompletar.visibility = View.GONE
        binding.btnCancelar.visibility = View.GONE

        // Mostrar botones según el estado actual
        when (estado) {
            "Pendiente" -> {
                binding.btnConfirmar.visibility = View.VISIBLE
                binding.btnCancelar.visibility = View.VISIBLE
            }
            "Confirmada" -> {
                binding.btnCompletar.visibility = View.VISIBLE
                binding.btnCancelar.visibility = View.VISIBLE
            }
            "Completada" -> {
                // No mostrar botones de cambio de estado para citas completadas
            }
            "Cancelada" -> {
                // No mostrar botones de cambio de estado para citas canceladas
            }
        }
    }

    private fun setupClickListeners() {
        // Botones de cambio de estado
        binding.btnConfirmar.setOnClickListener {
            cambiarEstado("Confirmada")
        }

        binding.btnCompletar.setOnClickListener {
            cambiarEstado("Completada")
        }

        binding.btnCancelar.setOnClickListener {
            showCancelConfirmation()
        }

        // Botones de acciones administrativas
        binding.btnContactarCliente.setOnClickListener {
            contactarCliente()
        }

        binding.btnEditarCosto.setOnClickListener {
            editarCosto()
        }

        binding.btnEliminarCita.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun cambiarEstado(nuevoEstado: String) {
        // En una app real, aquí actualizarías la base de datos
        // Por ahora, simulamos el cambio actualizando el objeto cita

        val citaActualizada = cita.copy(estado = nuevoEstado)
        this.cita = citaActualizada

        updateEstadoUI(nuevoEstado)
        setupEstadoButtons(nuevoEstado)

        Toast.makeText(
            requireContext(),
            "Estado cambiado a: $nuevoEstado",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showCancelConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Cancelar Cita")
            .setMessage("¿Estás seguro de que quieres cancelar esta cita? Se notificará al cliente.")
            .setPositiveButton("Sí, cancelar") { dialog, which ->
                cambiarEstado("Cancelada")
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun contactarCliente() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${cita.clienteEmail}")
            putExtra(Intent.EXTRA_SUBJECT, "Metron - Consulta sobre cita programada")
            putExtra(Intent.EXTRA_TEXT, "Estimado ${cita.clienteNombre},\n\nEn relación a su cita programada para el ${cita.fecha} a las ${cita.hora}...")
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "No hay aplicación de email instalada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editarCosto() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_editar_costo, null)
        val editTextCosto = dialogView.findViewById<EditText>(R.id.et_nuevo_costo)

        // Establecer el costo actual
        editTextCosto.setText(cita.costo.replace("$", ""))

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Costo")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, which ->
                val nuevoCosto = editTextCosto.text.toString().trim()
                if (nuevoCosto.isNotEmpty()) {
                    // En una app real, actualizarías la base de datos
                    val costoFormateado = "$${nuevoCosto}"
                    binding.tvCosto.text = costoFormateado

                    Toast.makeText(requireContext(), "Costo actualizado", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Cita")
            .setMessage("¿Estás seguro de que quieres eliminar permanentemente esta cita? Esta acción no se puede deshacer.")
            .setPositiveButton("Eliminar") { dialog, which ->
                eliminarCita()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarCita() {
        // En una app real, aquí eliminarías la cita de la base de datos
        Toast.makeText(requireContext(), "Cita eliminada exitosamente", Toast.LENGTH_SHORT).show()

        // Regresar a la lista de citas
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CITA = "cita"

        fun newInstance(cita: Cita): AdminDetalleCitaFragment {
            val fragment = AdminDetalleCitaFragment()
            val args = Bundle()
            args.putSerializable(ARG_CITA, cita)
            fragment.arguments = args
            return fragment
        }
    }
}