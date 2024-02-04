// ProfileFragment.kt

package com.alvarols01.capatapp2

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alvarols01.capatapp2.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        val btnGuardar: Button = view.findViewById(R.id.btnGuardar)
        val btnRecuperar: Button = view.findViewById(R.id.btnRecuperar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)

        val emailTextView : TextView = view.findViewById(R.id.emailTextView)
        val providerTextView : TextView = view.findViewById(R.id.providerTextView)
        val editNombreUsuario : TextView = view.findViewById(R.id.editNombreUsuario)
        val editTelefono : TextView = view.findViewById(R.id.editTelefono)
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()

        val usuario = FirebaseAuth.getInstance().currentUser

        if (usuario != null) {
            val email = usuario.email
            val provider = usuario.providerData[1].providerId

            if (provider != "google.com") {
                emailTextView.text = email
                providerTextView.text = "Basico"
            } else {
                emailTextView.text = email
                providerTextView.text = provider
            }


        }

        btnLogout.setOnClickListener {
            logout()
        }

        btnGuardar.setOnClickListener {
            val nombre = editNombreUsuario.text.toString()
            val telefono = editTelefono.text.toString()

            if (nombre.isNotEmpty() && telefono.isNotEmpty()) {
                val userData = hashMapOf(
                    "nombre" to nombre,
                    "telefono" to telefono
                )

                val currentUser = FirebaseAuth.getInstance().currentUser
                val email = currentUser?.email

                if (email != null) {
                    // Usar el correo electrónico como identificador único en la base de datos
                    db.collection("usuarios").document(email).set(userData)
                        .addOnSuccessListener {
                            Log.d("ProfileFragment", "Datos guardados correctamente")
                            // Mostrar mensaje al usuario, si es necesario
                        }
                        .addOnFailureListener { e ->
                            Log.w("ProfileFragment", "Error al guardar datos", e)
                            // Manejar el error, mostrar mensaje al usuario
                        }
                }
            } else {
                // Manejar el caso en que los campos están vacíos
                Log.d("ProfileFragment", "Nombre o teléfono están vacíos")
            }

        }

        btnRecuperar.setOnClickListener {
            db.collection("usuarios").document(email).get().addOnSuccessListener {
                editTelefono.setText(it.get("telefono") as String?)
                editNombreUsuario.setText(it.get("nombre") as String?)

                }

        }

        btnEliminar.setOnClickListener {
            db.collection("usuarios").document(email).delete().addOnSuccessListener {
                Log.d("ProfileFragment", "Datos eliminados correctamente")
                // Mostrar mensaje al usuario, si es necesario
            }
                .addOnFailureListener { e ->
                    Log.w("ProfileFragment", "Error al eliminar datos", e)
                    // Manejar el error, mostrar mensaje al usuario
                }
        }

        return view
    }

    private fun logout() {
        val preferences = requireActivity().getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        preferences.clear()
        preferences.apply()

        FirebaseAuth.getInstance().signOut()

        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
        // Finalizar la actividad actual
        requireActivity().finish()
    }
}
