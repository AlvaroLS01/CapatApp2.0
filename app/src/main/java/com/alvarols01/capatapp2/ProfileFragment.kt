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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        val btnEditar: Button = view.findViewById(R.id.btnEditar)

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
        db.collection("usuarios").document(email).get().addOnSuccessListener {
            editTelefono.setText(it.get("telefono") as String?)
            editNombreUsuario.setText(it.get("nombre") as String?)
        }

        btnLogout.setOnClickListener {
            logout()
        }

        btnEditar.setOnClickListener {
            // Obtén el NavController desde el fragmento actual
            val navController = findNavController()

            // Navega al fragmento "Editar"
            navController.navigate(R.id.editar) // Reemplaza "editarFragment" con el ID real de tu fragmento de edición
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
