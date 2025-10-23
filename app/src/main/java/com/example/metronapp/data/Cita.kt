package com.example.metronapp.data

import java.io.Serializable

data class Cita(
    val id: String,
    val tipoAgendamiento: String,
    val tipoServicio: String,
    val fecha: String,
    val hora: String,
    val descripcion: String,
    val costo: String,
    val ubicacion: String = "EDS Suba"
) : Serializable {
    companion object {
        fun getCitasEjemplo(): List<Cita> {
            return listOf(
                Cita(
                    id = "1",
                    tipoAgendamiento = "Emergencia",
                    tipoServicio = "Calibración de surtidores",
                    fecha = "Septiembre 11",
                    hora = "10:00 AM",
                    descripcion = "Problema con la medición en surtidor #3",
                    costo = "$150.000"
                ),
                Cita(
                    id = "2",
                    tipoAgendamiento = "Preventivo",
                    tipoServicio = "Ajuste de presión",
                    fecha = "Septiembre 30",
                    hora = "2:00 PM",
                    descripcion = "Mantenimiento programado trimestral",
                    costo = "$80.000"
                )
            )
        }
    }
}