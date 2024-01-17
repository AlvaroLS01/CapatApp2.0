package com.alvarols01.capatapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.adapter.HermandadesAdapter

class Dia : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dia, container, false)

        // Llamar a la función initRecyclerView después de inflar la vista
        initRecyclerView(view)

        return view
    }

    private fun initRecyclerView(view: View) {
        val args: DiaArgs by navArgs()
        val dia = args.name

        val hermandades = when (dia) {
            "hermandadesDomingoRamos" -> HermandadesProvider.hermandadesDomingoRamos
            "hermandadesLunesSanto" -> HermandadesProvider.hermandadesLunesSanto
            "hermandadesMartesSanto" -> HermandadesProvider.hermandadesMartesSanto
            "hermandadesMiercolesSanto" -> HermandadesProvider.hermandadesMiercolesSanto
            "hermandadesJuevesSanto" -> HermandadesProvider.hermandadesJuevesSanto
            "hermandadesMadruga" -> HermandadesProvider.hermandadesMadruga
            "hermandadesViernesSanto" -> HermandadesProvider.hermandadesViernesSanto
            "hermandadesSabadoSanto" -> HermandadesProvider.hermandadesSabadoSanto
            "hermandadesDomingoResurreccion" -> HermandadesProvider.hermandadesDomingoResurreccion
            else -> HermandadesProvider.hermandadesList
        }

        val manager = LinearLayoutManager(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerHermandades)

        // Configurar el LinearLayoutManager y asignarlo al RecyclerView
        recyclerView.layoutManager = manager

        // Agregar una línea divisoria entre los elementos del RecyclerView
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)
        recyclerView.addItemDecoration(decoration)

        // Asignar el adaptador al RecyclerView
        recyclerView.adapter = HermandadesAdapter(hermandades)
    }
}


