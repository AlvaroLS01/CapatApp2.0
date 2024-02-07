package com.alvarols01.capatapp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class DetalleFragment : Fragment() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombreHermandad = arguments?.getString("nombreHermandad")
        val imageView: ImageView = view.findViewById(R.id.imageViewDetalle)
        val textViewNombre: TextView = view.findViewById(R.id.textViewTituloHermandad)

        // Busca en Firebase por el nombre de la hermandad
        firestore.collection("hermandades")
            .whereEqualTo("nombre", nombreHermandad)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Asumiendo que el nombre de la hermandad es único y solo devolverá un resultado
                    val hermandad = documents.documents[0].toObject(Hermandades::class.java)
                    textViewNombre.text = hermandad?.nombre
                    Glide.with(this).load(hermandad?.photo).into(imageView)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error adecuadamente
                println("Error al cargar los detalles de la hermandad: $exception")
            }
    }
}