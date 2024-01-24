// ProfileFragment.kt

package com.alvarols01.capatapp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Obtener datos del argumento
        val email = arguments?.getString("email")
        val provider = arguments?.getString("provider")
        val name = arguments?.getString("name")
        val lastName = arguments?.getString("lastName")
        val phone = arguments?.getString("phone")

        // Mostrar datos en la interfaz de usuario
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)
        val providerTextView: TextView = view.findViewById(R.id.providerTextView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val lastNameTextView: TextView = view.findViewById(R.id.lastNameTextView)
        val phoneTextView: TextView = view.findViewById(R.id.phoneTextView)

        emailTextView.text = "Email: $email"
        providerTextView.text = "Provider: $provider"
        nameTextView.text = "Name: $name"
        lastNameTextView.text = "Last Name: $lastName"
        phoneTextView.text = "Phone: $phone"

        return view
    }
}
