package com.alvarols01.capatapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class MenuDias : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menudias, container, false)

        val btnDomingoRamos = root.findViewById<Button>(R.id.btnDomingoRamos)
        btnDomingoRamos.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Domingo de Ramos")
            findNavController().navigate(action)
        }

        val btnLunesSanto = root.findViewById<Button>(R.id.btnLunesSanto)
        btnLunesSanto.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Lunes Santo")
            findNavController().navigate(action)
        }

        val btnMartesSanto = root.findViewById<Button>(R.id.btnMartesSanto)
        btnMartesSanto.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Martes Santo")
            findNavController().navigate(action)
        }

        val btnMiercolesSanto = root.findViewById<Button>(R.id.btnMiercolesSanto)
        btnMiercolesSanto.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Miércoles Santo")
            findNavController().navigate(action)
        }

        val btnJuevesSanto = root.findViewById<Button>(R.id.btnJuevesSanto)
        btnJuevesSanto.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Jueves Santo")
            findNavController().navigate(action)
        }

        val btnMadruga = root.findViewById<Button>(R.id.btnMadruga)
        btnMadruga.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Madrugá")
            findNavController().navigate(action)
        }

        val btnViernesSanto = root.findViewById<Button>(R.id.btnViernesSanto)
        btnViernesSanto.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Viernes Santo")
            findNavController().navigate(action)
        }

        val btnSabadoSanto = root.findViewById<Button>(R.id.btnSabadoSanto)
        btnSabadoSanto.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Sábado Santo")
            findNavController().navigate(action)
        }

        val btnDomingoResurreccion = root.findViewById<Button>(R.id.btnDomingoResurreccion)
        btnDomingoResurreccion.setOnClickListener {
            val action = MenuDiasDirections.actionMenuDiasToDia(dia = "Domingo de Resurrección")
            findNavController().navigate(action)
        }


        return root
    }
}

