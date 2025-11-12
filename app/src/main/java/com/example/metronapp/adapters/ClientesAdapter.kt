package com.example.metronapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metronapp.R
import com.example.metronapp.data.Cliente

class ClientesAdapter(
    var clientes: List<Cliente>,
    private val onClienteClickListener: (Cliente) -> Unit
) : RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder>() {

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRazonSocial: TextView = itemView.findViewById(R.id.tv_razon_social)
        private val tvRepName: TextView = itemView.findViewById(R.id.tv_rep_name)
        private val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        private val tvNit: TextView = itemView.findViewById(R.id.tv_nit)
        private val tvTotalCitas: TextView = itemView.findViewById(R.id.tv_total_citas)
        private val tvFechaRegistro: TextView = itemView.findViewById(R.id.tv_fecha_registro)

        fun bind(cliente: Cliente) {
            tvRazonSocial.text = cliente.razonSocial
            tvRepName.text = cliente.repName
            tvEmail.text = cliente.email
            tvNit.text = "NIT: ${cliente.nit}"
            tvTotalCitas.text = "${cliente.totalCitas} citas"
            tvFechaRegistro.text = "Registrado: ${cliente.fechaRegistro}"

            itemView.setOnClickListener {
                onClienteClickListener(cliente)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        holder.bind(clientes[position])
    }

    override fun getItemCount(): Int = clientes.size
}