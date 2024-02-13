package com.alvarols01.capatapp2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC,
    GOOGLE
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Esto lo hago para que se vea la pantalla de carga
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)


        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")



        //Guardado de datos
       val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
       prefs.putString("email", email)
       prefs.putString("provider", provider)
       prefs.apply()



    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"
        

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId = R.id.action_home

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.HomeFragment)
                    true
                }

                R.id.action_hermandades -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.menuDias)
                    true
                }


                R.id.action_profile -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.ProfileFragment)
                    true
                }

                R.id.action_map -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.mapFragment)
                    true
                }

                R.id.action_chat -> {
                    findNavController(R.id.navHostFragment).navigate(R.id.chat)
                    true
                }


                // Agrega más casos según sea necesario
                else -> false
            }
        }

        }

    fun loadFragment(fragment: Fragment) {
        // Cargar fragmento
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navHostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



}

