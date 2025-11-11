package com.example.metronapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metronapp.R
import com.example.metronapp.data.Cita

class AdminCitasAdapter(
    var citas: List<Cita>,
    private val onCitaClickListener: (Cita) -> Unit
) : RecyclerView.Adapter<AdminCitasAdapter.AdminCitaViewHolder>() {

    inner class AdminCitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvClienteNombre: TextView = itemView.findViewById(R.id.tv_cliente_nombre)
        private val tvEstado: TextView = itemView.findViewById(R.id.tv_estado)
        private val tvServicio: TextView = itemView.findViewById(R.id.tv_servicio)
        private val tvFechaHora: TextView = itemView.findViewById(R.id.tv_fecha_hora)
        private val tvCosto: TextView = itemView.findViewById(R.id.tv_costo)
        private val tvClienteEmail: TextView = itemView.findViewById(R.id.tv_cliente_email)

        fun bind(cita: Cita) {
            tvClienteNombre.text = cita.clienteNombre
            tvServicio.text = "${cita.tipoServicio} - ${cita.tipoAgendamiento}"
            tvFechaHora.text = "${cita.fecha} - ${cita.hora}"
            tvCosto.text = cita.costo
            tvClienteEmail.text = cita.clienteEmail

            // Configurar estado con color correspondiente
            tvEstado.text = cita.estado
            when (cita.estado) {
                "Pendiente" -> tvEstado.setBackgroundResource(R.drawable.estado_pendiente_background)
                "Confirmada" -> tvEstado.setBackgroundResource(R.drawable.estado_confirmada_background)
                "Completada" -> tvEstado.setBackgroundResource(R.drawable.estado_completada_background)
                "Cancelada" -> tvEstado.setBackgroundResource(R.drawable.estado_cancelada_background)
            }

            itemView.setOnClickListener {
                onCitaClickListener(cita)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminCitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita_admin, parent, false)
        return AdminCitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminCitaViewHolder, position: Int) {
        holder.bind(citas[position])
    }

    override fun getItemCount(): Int = citas.size
}