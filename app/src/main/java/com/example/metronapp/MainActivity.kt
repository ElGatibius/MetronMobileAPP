package com.example.metronapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.metronapp.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("UserName", Context.MODE_PRIVATE)
        isAdmin = sharedPreferences.getBoolean("is_admin", false)

        setupToolbar()
        loadInitialFragment()
    }

    private fun setupToolbar() {
        val btnMenu = findViewById<ImageButton>(R.id.btn_menu)
        val btnProfile = findViewById<ImageButton>(R.id.btn_profile)
        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)

        // Establecer título
        toolbarTitle.text = "Metron"

        // Menú hamburguesa
        btnMenu.setOnClickListener { view ->
            showHamburgerMenu(view)
        }

        // Menú de perfil
        btnProfile.setOnClickListener { view ->
            showProfileMenu(view)
        }
    }

    private fun showHamburgerMenu(view: android.view.View) {
        val popup = PopupMenu(this, view)

        // Inflar menú diferente según el rol
        val menuRes = if (isAdmin) {
            R.menu.hamburguer_menu_admin
        } else {
            R.menu.hamburguer_menu_options
        }

        popup.menuInflater.inflate(menuRes, popup.menu)

        // Forzar a mostrar íconos
        try {
            val field = popup.javaClass.getDeclaredField("mPopup")
            field.isAccessible = true
            val menuPopupHelper = field.get(popup)
            menuPopupHelper.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menuPopupHelper, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.nav_agendar_cita -> {
                    replaceFragment(AgendarCitaFragment())
                    true
                }
                R.id.nav_revisar_citas -> {
                    replaceFragment(RevisarCitasFragment())
                    true
                }
                // Opciones del admin
                R.id.nav_admin_dashboard -> {
                    replaceFragment(AdminDashboardFragment())
                    true
                }
                R.id.nav_gestion_citas -> {
                    replaceFragment(AdminGestionCitasFragment())
                    true
                }
                R.id.nav_gestion_clientes -> {
                    replaceFragment(AdminGestionClientesFragment())
                    true
                }
                R.id.nav_reportes -> {
                    replaceFragment(AdminReportesFragment())
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun showProfileMenu(view: android.view.View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.profile_menu_options, popup.menu)

        // Forzar a mostrar íconos
        try {
            val field = popup.javaClass.getDeclaredField("mPopup")
            field.isAccessible = true
            val menuPopupHelper = field.get(popup)
            menuPopupHelper.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menuPopupHelper, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.nav_edit_profile -> {
                    replaceFragment(EditarPerfilFragment())
                    true
                }
                R.id.nav_logout -> {
                    showLogoutConfirmation()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun loadInitialFragment() {
        // Fragment inicial según el rol
        val fragment = if (isAdmin) {
            AdminDashboardFragment()
        } else {
            HomeFragment()
        }

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showLogoutConfirmation() {
        android.app.AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, which ->
                performLogout()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun performLogout() {
        // Limpiar solo los datos de sesión, mantener los datos del usuario
        val editor = sharedPreferences.edit()
        editor.remove("is_admin")
        editor.remove("user_role")
        editor.apply()

        finish()
    }
}