package com.example.metronapp
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encontrar la Toolbar dentro del layout incluido
        val toolbar: Toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        // Quitar el título por defecto para usar nuestro TextView centrado
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    // Función para mostrar menús desplegables (Popup)
    private fun showPopupMenu(anchor: View, menuRes: Int) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                // Aquí manejas la navegación para cada opción
                R.id.nav_agendar_cita -> {
                    // navController.navigate(R.id.tu_fragment_de_agendar_cita)
                    true
                }
                R.id.nav_revisar_citas -> {
                    // navController.navigate(R.id.tu_fragment_de_revisar_citas)
                    true
                }
                R.id.nav_edit_profile -> {
                    // navController.navigate(R.id.tu_fragment_de_editar_perfil)
                    true
                }
                R.id.nav_logout -> {
                    // Lógica para cerrar sesión
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
