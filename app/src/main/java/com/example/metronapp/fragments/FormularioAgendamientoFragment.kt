package com.example.metronapp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.databinding.FragmentFormularioAgendamientoBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FormularioAgendamientoFragment : Fragment() {

    private var _binding: FragmentFormularioAgendamientoBinding? = null
    private val binding get() = _binding!!

    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    private var selectedHour = 0
    private var selectedMinute = 0

    // Variables para almacenar el tipo de servicio y agendamiento
    private var serviceType: String? = null
    private var appointmentType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            serviceType = it.getString(ARG_SERVICE_TYPE)
            appointmentType = it.getString(ARG_APPOINTMENT_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormularioAgendamientoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        // Mostrar la información del servicio seleccionado
        binding.tvServicioSeleccionado.text = "Servicio: $serviceType"
        binding.tvTipoSeleccionado.text = "Tipo: $appointmentType"
    }

    private fun setupClickListeners() {
        // Listener para el campo de fecha
        binding.etFecha.setOnClickListener {
            showDatePicker()
        }

        // Listener para el campo de hora
        binding.etHora.setOnClickListener {
            showTimePicker()
        }

        // Listener para el botón de confirmar
        binding.btnConfirmarAgendamiento.setOnClickListener {
            confirmAppointment()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedYear = year
                selectedMonth = month
                selectedDay = dayOfMonth

                // Mostrar la fecha seleccionada en el EditText
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.etFecha.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                selectedHour = hourOfDay
                selectedMinute = minute

                // Formatear la hora para mostrarla
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                binding.etHora.setText(selectedTime)
            },
            hour,
            minute,
            true // 24 horas
        )
        timePickerDialog.show()
    }

    private fun confirmAppointment() {
        val fecha = binding.etFecha.text.toString()
        val hora = binding.etHora.text.toString()
        val descripcion = binding.etDescripcion.text.toString()

        if (fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, selecciona fecha y hora", Toast.LENGTH_SHORT).show()
            return
        }

        // Aquí iría la lógica para guardar la cita (en base de datos, API, etc.)
        // Por ahora, mostramos un mensaje de confirmación

        Toast.makeText(
            requireContext(),
            "¡Cita agendada exitosamente!\n\nServicio: $serviceType\nTipo: $appointmentType\nFecha: $fecha\nHora: $hora\nDescripción: $descripcion",
            Toast.LENGTH_LONG
        ).show()

        // Podríamos navegar de vuelta al home o al fragment anterior
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SERVICE_TYPE = "service_type"
        private const val ARG_APPOINTMENT_TYPE = "appointment_type"

        fun newInstance(serviceType: String, appointmentType: String): FormularioAgendamientoFragment {
            val fragment = FormularioAgendamientoFragment()
            val args = Bundle()
            args.putString(ARG_SERVICE_TYPE, serviceType)
            args.putString(ARG_APPOINTMENT_TYPE, appointmentType)
            fragment.arguments = args
            return fragment
        }
    }
}