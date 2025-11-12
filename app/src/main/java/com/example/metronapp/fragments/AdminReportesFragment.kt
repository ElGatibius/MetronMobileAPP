package com.example.metronapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.metronapp.R
import com.example.metronapp.databinding.FragmentAdminReportesBinding

class AdminReportesFragment : Fragment() {

    private var _binding: FragmentAdminReportesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminReportesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupClickListeners()
        generarReporteInicial()
    }

    private fun setupSpinners() {
        // Configurar spinner de tipo de reporte
        val tiposReporte = arrayOf("Reporte General", "Reporte de Citas", "Reporte de Ingresos", "Reporte de Clientes")
        val adapterTipo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tiposReporte)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipoReporte.adapter = adapterTipo

        // Configurar spinner de período
        val periodos = arrayOf("Última semana", "Último mes", "Último trimestre", "Último año", "Todo el tiempo")
        val adapterPeriodo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periodos)
        adapterPeriodo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPeriodo.adapter = adapterPeriodo

        // Listener para cambios en los spinners
        binding.spinnerTipoReporte.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                generarReporte()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerPeriodo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                generarReporte()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupClickListeners() {
        binding.btnExportarPdf.setOnClickListener {
            exportarPDF()
        }

        binding.btnCompartirReporte.setOnClickListener {
            compartirReporte()
        }
    }

    private fun generarReporteInicial() {
        generarReporte()
    }

    private fun generarReporte() {
        val tipoReporte = binding.spinnerTipoReporte.selectedItem.toString()
        val periodo = binding.spinnerPeriodo.selectedItem.toString()

        // Simular generación de reporte con datos diferentes según el tipo
        val reporte = when (tipoReporte) {
            "Reporte General" -> generarReporteGeneral(periodo)
            "Reporte de Citas" -> generarReporteCitas(periodo)
            "Reporte de Ingresos" -> generarReporteIngresos(periodo)
            "Reporte de Clientes" -> generarReporteClientes(periodo)
            else -> "Reporte no disponible"
        }

        binding.tvResultadoReporte.text = reporte
        binding.tvResultadoReporte.visibility = View.VISIBLE
    }

    private fun generarReporteGeneral(periodo: String): String {
        return """
            📊 REPORTE GENERAL - $periodo
            =============================
            
            📈 ESTADÍSTICAS PRINCIPALES:
            • Total de citas: 25
            • Citas completadas: 18
            • Citas pendientes: 5
            • Citas canceladas: 2
            
            💰 INGRESOS:
            • Ingresos totales: $3.250.000
            • Ingresos por emergencia: $2.100.000
            • Ingresos por preventivo: $1.150.000
            • Promedio por cita: $130.000
            
            👥 CLIENTES:
            • Clientes activos: 4
            • Nuevos clientes: 1
            • Citas por cliente: 6.25
            
            📅 DISTRIBUCIÓN:
            • Emergencia: 15 citas (60%)
            • Preventivo: 10 citas (40%)
            
            Generado: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date())}
        """.trimIndent()
    }

    private fun generarReporteCitas(periodo: String): String {
        return """
            📅 REPORTE DE CITAS - $periodo
            ==============================
            
            📋 RESUMEN DE CITAS:
            • Total programadas: 25
            • Completadas: 18 (72%)
            • Pendientes: 5 (20%)
            • Canceladas: 2 (8%)
            
            🚨 CITAS DE EMERGENCIA:
            • Total: 15 citas
            • Completadas: 12
            • Pendientes: 2
            • Canceladas: 1
            • Tiempo promedio: 2.5 horas
            
            🔧 CITAS PREVENTIVAS:
            • Total: 10 citas
            • Completadas: 6
            • Pendientes: 3
            • Canceladas: 1
            • Tiempo promedio: 1.5 horas
            
            📊 EFICIENCIA:
            • Tasa de completación: 72%
            • Tasa de cancelación: 8%
            • Satisfacción del cliente: 4.8/5.0
            
            Generado: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date())}
        """.trimIndent()
    }

    private fun generarReporteIngresos(periodo: String): String {
        return """
            💰 REPORTE DE INGRESOS - $periodo
            ================================
            
            💵 INGRESOS TOTALES: $3.250.000
            
            📈 POR TIPO DE SERVICIO:
            • Emergencia: $2.100.000 (64.6%)
            • Preventivo: $1.150.000 (35.4%)
            
            📊 ESTADÍSTICAS FINANCIERAS:
            • Ingreso promedio por cita: $130.000
            • Ingreso mensual promedio: $1.083.333
            • Crecimiento vs período anterior: +15%
            
            🏆 TOP CLIENTES POR INGRESOS:
            1. Gasolinera Central: $800.000
            2. Gasolinera Los Andes: $750.000
            3. Estación La Esperanza: $650.000
            4. ServiGas Norte: $400.000
            
            📅 PROYECCIONES:
            • Ingresos mensuales estimados: $1.200.000
            • Crecimiento anual proyectado: 18%
            
            Generado: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date())}
        """.trimIndent()
    }

    private fun generarReporteClientes(periodo: String): String {
        return """
            👥 REPORTE DE CLIENTES - $periodo
            ================================
            
            📊 RESUMEN DE CLIENTES:
            • Total de clientes: 4
            • Clientes activos: 4 (100%)
            • Nuevos clientes este período: 1
            • Tasa de retención: 95%
            
            🏆 CLIENTES DESTACADOS:
            • Mayor número de citas: Gasolinera Central (8 citas)
            • Cliente más reciente: ServiGas Norte
            • Mayor fidelidad: Gasolinera Los Andes (5 meses)
            
            📈 COMPORTAMIENTO:
            • Citas promedio por cliente: 6.25
            • Frecuencia promedio: 2.3 citas/mes
            • Satisfacción promedio: 4.8/5.0
            
            💼 INFORMACIÓN DE CONTACTO:
            • Email disponible: 100%
            • Teléfono disponible: 100%
            • Dirección disponible: 75%
            
            🎯 RECOMENDACIONES:
            • Programar seguimiento a Estación La Esperanza
            • Ofrecer mantenimiento preventivo a ServiGas Norte
            • Contactar Gasolinera Central para renovación de contrato
            
            Generado: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date())}
        """.trimIndent()
    }

    private fun exportarPDF() {
        // Simular exportación a PDF
        android.widget.Toast.makeText(
            requireContext(),
            "Exportando reporte a PDF...",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private fun compartirReporte() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Reporte Metron - ${binding.spinnerTipoReporte.selectedItem}")
            putExtra(Intent.EXTRA_TEXT, binding.tvResultadoReporte.text.toString())
        }
        startActivity(Intent.createChooser(shareIntent, "Compartir reporte"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}