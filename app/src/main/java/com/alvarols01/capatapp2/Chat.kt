package com.alvarols01.capatapp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Chat : Fragment() {
    private lateinit var adapter: MensajeAdapter // Asegúrate de implementar este adaptador.
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val chatRecyclerView: RecyclerView = view.findViewById(R.id.chatRecyclerView)
        val messageEditText: EditText = view.findViewById(R.id.messageEditText)
        val sendButton: Button = view.findViewById(R.id.sendButton)

        adapter = MensajeAdapter(listOf()) // Inicializa tu adaptador aquí
        chatRecyclerView.layoutManager = LinearLayoutManager(context)
        chatRecyclerView.adapter = adapter

        sendButton.setOnClickListener {
            val contenido = messageEditText.text.toString().trim() // Elimina espacios al principio y al final
            if (contenido.isNotEmpty()) { // Comprueba si el mensaje no está vacío
                enviarMensaje(contenido)
                messageEditText.text.clear() // Limpia el EditText solo si el mensaje se ha enviado
            }
        }

        escucharMensajes()
        return view
    }

    private fun enviarMensaje(contenido: String) {
        val mensaje = Mensaje(
            contenido = contenido,
            remitente = FirebaseAuth.getInstance().currentUser?.email ?: "Anónimo",
            timestamp = System.currentTimeMillis()
        )

        db.collection("chat_global").add(mensaje)
            .addOnSuccessListener {
                // Mensaje enviado con éxito
                // Aquí podrías, por ejemplo, mostrar un Toast breve para confirmar el envío
            }
            .addOnFailureListener {
                // Error al enviar mensaje
                // Aquí podrías manejar el error, por ejemplo, mostrando un mensaje al usuario
            }
    }

    private fun escucharMensajes() {
        db.collection("chat_global")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // Manejar el error
                    return@addSnapshotListener
                }

                val mensajes = mutableListOf<Mensaje>()
                for (doc in value!!) {
                    val mensaje = doc.toObject(Mensaje::class.java)
                    mensajes.add(mensaje)
                }
                adapter.updateData(mensajes) // Actualiza tu lista de mensajes y refresca el adaptador
            }
    }
}
