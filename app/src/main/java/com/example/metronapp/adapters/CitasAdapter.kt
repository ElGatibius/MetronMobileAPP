package com.example.metronapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metronapp.R
import com.example.metronapp.data.Cita

class CitasAdapter(
    private val citas: List<Cita>,
    private val onCitaClickListener: (Cita) -> Unit
) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTipoAgendamiento: TextView = itemView.findViewById(R.id.tv_tipo_agendamiento)
        private val tvFecha: TextView = itemView.findViewById(R.id.tv_fecha)
        private val tvTipoServicio: TextView = itemView.findViewById(R.id.tv_tipo_servicio)

        fun bind(cita: Cita) {
            tvTipoAgendamiento.text = cita.tipoAgendamiento
            tvFecha.text = cita.fecha
            tvTipoServicio.text = cita.tipoServicio

            itemView.setOnClickListener {
                onCitaClickListener(cita)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(citas[position])
    }

    override fun getItemCount(): Int = citas.size
}