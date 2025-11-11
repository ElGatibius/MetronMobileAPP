package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metronapp.R
import com.example.metronapp.adapters.AdminCitasAdapter
import com.example.metronapp.data.Cita
import com.example.metronapp.databinding.FragmentAdminGestionCitasBinding

class AdminGestionCitasFragment : Fragment() {

    private var _binding: FragmentAdminGestionCitasBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AdminCitasAdapter
    private var todasLasCitas: List<Cita> = emptyList()
    private var citasFiltradas: List<Cita> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminGestionCitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFiltros()
        setupRecyclerView()
        loadCitas()
    }

    private fun setupFiltros() {
        // Configurar spinner de filtro por estado
        val estados = arrayOf("Todos", "Pendiente", "Confirmada", "Completada", "Cancelada")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, estados)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFiltroEstado.adapter = adapterSpinner

        binding.spinnerFiltroEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtrarCitas(estados[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupRecyclerView() {
        adapter = AdminCitasAdapter(citasFiltradas) { cita ->
            // Navegar al detalle de la cita para el admin
        navigateToAdminCitaDetail(cita)
        }

        binding.rvCitasAdmin.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCitasAdmin.adapter = adapter
    }

    private fun loadCitas() {
        todasLasCitas = Cita.getCitasEjemplo()
        citasFiltradas = todasLasCitas
        adapter.citas = citasFiltradas
        adapter.notifyDataSetChanged()

        // Actualizar contador
        binding.tvContadorCitas.text = "Total: ${citasFiltradas.size} citas"
    }

    private fun filtrarCitas(estado: String) {
        citasFiltradas = if (estado == "Todos") {
            todasLasCitas
        } else {
            todasLasCitas.filter { it.estado == estado }
        }

        adapter.citas = citasFiltradas
        adapter.notifyDataSetChanged()

        // Actualizar contador
        binding.tvContadorCitas.text = "Total: ${citasFiltradas.size} citas"
    }

    private fun navigateToAdminCitaDetail(cita: Cita) {
        val fragment = AdminDetalleCitaFragment.newInstance(cita)
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