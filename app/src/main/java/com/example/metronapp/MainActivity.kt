package com.example.metronapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.metronapp.fragments.AgendarCitaFragment
import com.example.metronapp.fragments.EditarPerfilFragment
import com.example.metronapp.fragments.HomeFragment
import com.example.metronapp.fragments.RevisarCitasFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        popup.menuInflater.inflate(R.menu.hamburguer_menu_options, popup.menu)

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
        // Aquí va la lógica real de logout
        // Por ahora solo cerramos esta activity
        finish()
    }

    private fun loadInitialFragment() {
        // Fragment inicial que se muestra al abrir la app
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            replaceFragment(HomeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}