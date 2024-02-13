package com.alvarols01.capatapp2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.adapter.HermandadesAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Favoritos : Fragment(R.layout.fragment_favoritos) {

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

                    for (document in querySnapshot.documents) {
                        val hermandad = document.toObject(Hermandades::class.java)
                        if (hermandad != null) {
                            hermandadesList.add(hermandad)
                        }
                    }

                    val adapter = HermandadesAdapter(hermandadesList) { nombreHermandad ->
                        // Correctly handling the click on a list item, passing the brotherhood name
                        val action = DiaDirections.actionDiaToDetalleFragment(nombreHermandad = nombreHermandad)
                        findNavController().navigate(action)
                    }
                    recyclerView.adapter = adapter
                }
            }
            .addOnFailureListener { exception ->
                // Handle the error appropriately
                println("Error cargando las hermandades: $exception")
            }
    }
}
