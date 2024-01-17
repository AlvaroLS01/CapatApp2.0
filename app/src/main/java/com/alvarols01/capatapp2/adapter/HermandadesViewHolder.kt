package com.alvarols01.capatapp2.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.Hermandades
import com.alvarols01.capatapp2.R
import com.bumptech.glide.Glide

class HermandadesViewHolder(view: View): RecyclerView.ViewHolder(view) {


    val hermandad = view.findViewById<TextView>(R.id.tvNombreHermandad)
    val iglesia = view.findViewById<TextView>(R.id.tvNombreIglesia)
    val dia = view.findViewById<TextView>(R.id.tvDia)
    val photo = view.findViewById<ImageView>(R.id.ivHermandad)


    fun render(hermandadesModel: Hermandades) {
        hermandad.text = hermandadesModel.hermandad
        iglesia.text = hermandadesModel.iglesia
        dia.text = hermandadesModel.dia
        Glide.with(itemView).load(hermandadesModel.photo).into(photo)

        itemView.setOnClickListener {
            // Llama al nuevo método para abrir la URL
            openUrl(hermandadesModel.url)
        }
    }

    private fun openUrl(url: String) {
        // Crea un Intent implícito para abrir la URL en un navegador
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        // Comprueba si hay una actividad que pueda manejar el Intent
        if (intent.resolveActivity(itemView.context.packageManager) != null) {
            itemView.context.startActivity(intent)
        } else {
            // Muestra un mensaje si no hay actividad para manejar el Intent
            Toast.makeText(itemView.context, "No se puede abrir la URL", Toast.LENGTH_SHORT).show()
        }
    }
    }
