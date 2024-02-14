package com.alvarols01.capatapp2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.Hermandades
import com.alvarols01.capatapp2.databinding.ItemHermandadesBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HermandadesAdapter(
    private val hermandadesList: List<Hermandades>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<HermandadesAdapter.HermandadesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HermandadesViewHolder {
        val binding = ItemHermandadesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HermandadesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HermandadesViewHolder, position: Int) {
        val hermandad = hermandadesList[position]
        holder.bind(hermandad, onClick)
    }

    override fun getItemCount(): Int = hermandadesList.size

    class HermandadesViewHolder(
        private val binding: ItemHermandadesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hermandad: Hermandades, onClick: (String) -> Unit) {
            with(binding) {
                tvNombreHermandad.text = hermandad.nombre
                tvNombreIglesia.text = hermandad.iglesia
                tvDia.text = hermandad.dia
                Glide.with(ivHermandad.context)
                    .load(hermandad.photo)
                    .into(ivHermandad)

                btnAnadirFavoritos.setOnClickListener {
                    añadirAFavoritos(hermandad)
                }

                root.setOnClickListener {
                    onClick(hermandad.nombre)
                }
            }
        }

        private fun añadirAFavoritos(hermandad: Hermandades) {
            val emailUsuario = FirebaseAuth.getInstance().currentUser?.email ?: return
            val db = FirebaseFirestore.getInstance()
            val favoritosRef = db.collection("usuarios").document(emailUsuario).collection("favoritos")
            val docRef = favoritosRef.document(hermandad.nombre)

            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        // La hermandad ya está en favoritos, así que la eliminamos
                        docRef.delete().addOnSuccessListener {
                            Toast.makeText(itemView.context, "Hermandad eliminada de favoritos", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { e ->
                            Toast.makeText(itemView.context, "Error al eliminar hermandad de favoritos", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // La hermandad no está en favoritos, así que la añadimos
                        val favorito = hashMapOf(
                            "nombre" to hermandad.nombre,
                            "iglesia" to hermandad.iglesia,
                            "dia" to hermandad.dia,
                            "photo" to hermandad.photo,
                            "url" to hermandad.url
                        )

                        docRef.set(favorito).addOnSuccessListener {
                            Toast.makeText(itemView.context, "Hermandad añadida a favoritos", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { e ->
                            Toast.makeText(itemView.context, "Error al añadir hermandad a favoritos", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(itemView.context, "Error al comprobar el estado de favoritos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
