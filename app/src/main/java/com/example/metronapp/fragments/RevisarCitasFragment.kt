package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metronapp.R
import com.example.metronapp.adapters.CitasAdapter
import com.example.metronapp.data.Cita
import com.example.metronapp.databinding.FragmentRevisarCitasBinding

class RevisarCitasFragment : Fragment() {

    private var _binding: FragmentRevisarCitasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRevisarCitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val citas = Cita.getCitasEjemplo()

        if (citas.isEmpty()) {
            binding.tvSinCitas.visibility = View.VISIBLE
            binding.rvCitas.visibility = View.GONE
        } else {
            binding.tvSinCitas.visibility = View.GONE
            binding.rvCitas.visibility = View.VISIBLE

            val adapter = CitasAdapter(citas) { cita ->
                // Navegar al detalle de la cita
                navigateToCitaDetail(cita)
            }

            binding.rvCitas.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCitas.adapter = adapter
        }
    }

    private fun navigateToCitaDetail(cita: Cita) {
        val fragment = DetalleCitaFragment.newInstance(cita)
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