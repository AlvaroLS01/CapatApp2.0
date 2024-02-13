package com.alvarols01.capatapp2.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.Hermandades
import com.alvarols01.capatapp2.R
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HermandadesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val hermandadTextView: TextView = view.findViewById(R.id.tvNombreHermandad)
    private val iglesiaTextView: TextView = view.findViewById(R.id.tvNombreIglesia)
    private val diaTextView: TextView = view.findViewById(R.id.tvDia)
    private val photoImageView: ImageView = view.findViewById(R.id.ivHermandad)
    private val btnAnadirFavoritos: ImageView = view.findViewById(R.id.btnAnadirFavoritos)

    fun bind(hermandadesModel: Hermandades, onHermandadClicked: (String) -> Unit, onFavoritosClicked: (Hermandades) -> Unit) {
        hermandadTextView.text = hermandadesModel.nombre
        iglesiaTextView.text = hermandadesModel.iglesia
        diaTextView.text = hermandadesModel.dia
        Glide.with(itemView.context).load(hermandadesModel.photo).into(photoImageView)

        itemView.setOnClickListener {
            onHermandadClicked(hermandadesModel.nombre)
        }

        btnAnadirFavoritos.setOnClickListener {
            onFavoritosClicked(hermandadesModel)
            añadirAFavoritos(hermandadesModel)
        }
    }

    private fun añadirAFavoritos(hermandadesModel: Hermandades) {
        val usuarioEmail = FirebaseAuth.getInstance().currentUser?.email ?: return
        FirebaseFirestore.getInstance().collection("usuarios")
            .document(usuarioEmail)
            .collection("favoritos")
            .document(hermandadesModel.nombre)
            .set(hermandadesModel)
            .addOnSuccessListener {
                Toast.makeText(itemView.context, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(itemView.context, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show()
            }
    }
}
