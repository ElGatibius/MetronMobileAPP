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
    val ubicacion: String = "EDS Suba",
    val clienteNombre: String,
    val clienteEmail: String,
    val estado: String = "Pendiente"
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
                costo = "$150.000",
                clienteNombre = "Gasolinera Los Andes",
                clienteEmail = "andes@gasolineras.com",
                estado = "Confirmada"
            ),
                Cita(
                    id = "2",
                    tipoAgendamiento = "Preventivo",
                    tipoServicio = "Ajuste de presión",
                    fecha = "Septiembre 30",
                    hora = "2:00 PM",
                    descripcion = "Mantenimiento programado trimestral",
                    costo = "$80.000",
                    clienteNombre = "Estación La Esperanza",
                    clienteEmail = "esperanza@estaciones.com",
                    estado = "Pendiente"
                ),
                Cita(
                    id = "3",
                    tipoAgendamiento = "Emergencia",
                    tipoServicio = "Calibración de surtidores",
                    fecha = "Septiembre 15",
                    hora = "9:00 AM",
                    descripcion = "Fuga en sistema de medición",
                    costo = "$200.000",
                    clienteNombre = "ServiGas Norte",
                    clienteEmail = "norte@servigas.com",
                    estado = "Completada"
                ),
                Cita(
                    id = "4",
                    tipoAgendamiento = "Preventivo",
                    tipoServicio = "Ajuste de presión",
                    fecha = "Octubre 5",
                    hora = "11:00 AM",
                    descripcion = "Revisión general del sistema",
                    costo = "$75.000",
                    clienteNombre = "Gasolinera Central",
                    clienteEmail = "central@gasolineras.com",
                    estado = "Pendiente"
            ))
        }
    }
}

