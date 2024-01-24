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
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesDomingoRamos"))
        }

        val btnLunesSanto = root.findViewById<Button>(R.id.btnLunesSanto)
        btnLunesSanto.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesLunesSanto"))
        }

        val btnMartesSanto = root.findViewById<Button>(R.id.btnMartesSanto)
        btnMartesSanto.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesMartesSanto"))
        }

        val btnMiercolesSanto = root.findViewById<Button>(R.id.btnMiercolesSanto)
        btnMiercolesSanto.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesMiercolesSanto"))
        }

        val btnJuevesSanto = root.findViewById<Button>(R.id.btnJuevesSanto)
        btnJuevesSanto.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesJuevesSanto"))
        }

        val btnMadruga = root.findViewById<Button>(R.id.btnMadruga)
        btnMadruga.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesMadruga"))
        }

        val btnViernesSanto = root.findViewById<Button>(R.id.btnViernesSanto)
        btnViernesSanto.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesViernesSanto"))
        }

        val btnSabadoSanto = root.findViewById<Button>(R.id.btnSabadoSanto)
        btnSabadoSanto.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesSabadoSanto"))
        }

        val btnDomingoResurreccion = root.findViewById<Button>(R.id.btnDomingoResurreccion)
        btnDomingoResurreccion.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesDomingoResurreccion"))
        }

        val btnTodasLasHermandades = root.findViewById<Button>(R.id.btnTodasLasHermandades)
        btnTodasLasHermandades.setOnClickListener {
            findNavController().navigate(MenuDiasDirections.actionMenuDiasToDia(name = "hermandadesList"))
        }


        return root

    }
}