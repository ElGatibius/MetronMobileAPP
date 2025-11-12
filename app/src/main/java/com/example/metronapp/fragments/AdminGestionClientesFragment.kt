package com.example.metronapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metronapp.R
import com.example.metronapp.adapters.ClientesAdapter
import com.example.metronapp.data.Cliente
import com.example.metronapp.databinding.FragmentAdminGestionClientesBinding

class AdminGestionClientesFragment : Fragment() {

    private var _binding: FragmentAdminGestionClientesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ClientesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminGestionClientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadClientes()
    }

    private fun setupRecyclerView() {
        adapter = ClientesAdapter(emptyList()) { cliente ->
            //navigateToClienteDetail(cliente)
        }

        binding.rvClientes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvClientes.adapter = adapter
    }

    private fun loadClientes() {
        val clientes = Cliente.getClientesEjemplo()
        adapter.clientes = clientes
        adapter.notifyDataSetChanged()

        // Actualizar contador
        binding.tvContadorClientes.text = "Total: ${clientes.size} clientes"

        // Mostrar/ocultar mensaje de no clientes
        if (clientes.isEmpty()) {
            binding.tvSinClientes.visibility = View.VISIBLE
            binding.rvClientes.visibility = View.GONE
        } else {
            binding.tvSinClientes.visibility = View.GONE
            binding.rvClientes.visibility = View.VISIBLE
        }
    }
/**
    private fun navigateToClienteDetail(cliente: Cliente) {
        val fragment = AdminDetalleClienteFragment.newInstance(cliente)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
**/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}