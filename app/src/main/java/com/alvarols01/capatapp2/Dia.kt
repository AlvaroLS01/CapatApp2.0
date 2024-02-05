package com.alvarols01.capatapp2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.adapter.HermandadesAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Dia : Fragment(R.layout.fragment_dia) {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val args: DiaArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerHermandades)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        firestore.collection("hermandades")
            .whereEqualTo("dia", args.dia)
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                if (querySnapshot != null) {
                    val hermandadesList = mutableListOf<Hermandades>()

                    for (document in querySnapshot) {
                        val hermandad = document.toObject(Hermandades::class.java)
                        hermandadesList.add(hermandad)
                    }

                    val adapter = HermandadesAdapter(hermandadesList)
                    recyclerView.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                // Maneja el error adecuadamente
                println("Error al cargar las hermandades: $exception")
            }
    }
}
