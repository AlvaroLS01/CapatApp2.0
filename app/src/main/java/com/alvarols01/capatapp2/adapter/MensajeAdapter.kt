package com.alvarols01.capatapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class MensajeAdapter(private var mensajes: List<Mensaje>) :
    RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    fun updateData(mensajes: List<Mensaje>) {
        this.mensajes = mensajes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = mensajes[position]
        holder.bind(mensaje)
    }

    override fun getItemCount() = mensajes.size

    class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvRemitente: TextView = view.findViewById(R.id.tvRemitente)
        private val tvContenido: TextView = view.findViewById(R.id.tvContenido)
        private val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)

        fun bind(mensaje: Mensaje) {
            tvRemitente.text = mensaje.remitente
            tvContenido.text = mensaje.contenido
            tvTimestamp.text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(mensaje.timestamp)
        }
    }
}
