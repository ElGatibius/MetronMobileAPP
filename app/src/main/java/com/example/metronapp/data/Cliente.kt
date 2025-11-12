package com.example.metronapp.data

import java.io.Serializable

data class Cliente(
    val id: String,
    val razonSocial: String,
    val nit: String,
    val email: String,
    val repName: String,
    val telefono: String = "No proporcionado",
    val direccion: String = "No proporcionada",
    val fechaRegistro: String,
    val totalCitas: Int = 0
) : Serializable { // 4. Se cambia Parcelable por Serializable
    companion object {
        fun getClientesEjemplo(): List<Cliente> {
            return listOf(
                Cliente(
                    id = "1",
                    razonSocial = "Gasolinera Los Andes",
                    nit = "123456789-0",
                    email = "andes@gasolineras.com",
                    repName = "Carlos Andrade",
                    telefono = "3001234567",
                    direccion = "Calle 123 #45-67",
                    fechaRegistro = "15 Agosto 2024",
                    totalCitas = 5
                ),
                Cliente(
                    id = "2",
                    razonSocial = "Estación La Esperanza",
                    nit = "987654321-0",
                    email = "esperanza@estaciones.com",
                    repName = "María González",
                    telefono = "3109876543",
                    direccion = "Avenida Siempre Viva 742",
                    fechaRegistro = "20 Julio 2024",
                    totalCitas = 3
                ),
                Cliente(
                    id = "3",
                    razonSocial = "ServiGas Norte",
                    nit = "456789123-0",
                    email = "norte@servigas.com",
                    repName = "Jorge Pérez",
                    telefono = "3204567890",
                    direccion = "Carrera 80 #25-30",
                    fechaRegistro = "10 Septiembre 2024",
                    totalCitas = 2
                ),
                Cliente(
                    id = "4",
                    razonSocial = "Gasolinera Central",
                    nit = "789123456-0",
                    email = "central@gasolineras.com",
                    repName = "Ana López",
                    telefono = "3157891234",
                    direccion = "Diagonal 60 #10-20",
                    fechaRegistro = "5 Junio 2024",
                    totalCitas = 8
                )
            )
        }
    }
}
