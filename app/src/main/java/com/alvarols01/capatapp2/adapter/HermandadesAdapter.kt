package com.alvarols01.capatapp2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.Hermandades
import com.alvarols01.capatapp2.R

class HermandadesAdapter(private val hermandadesList: List<Hermandades>) : RecyclerView.Adapter<HermandadesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HermandadesViewHolder {
        val layautInflater = LayoutInflater.from(parent.context)
        return HermandadesViewHolder(layautInflater.inflate(R.layout.item_hermandades, parent, false))
    }

    override fun onBindViewHolder(holder: HermandadesViewHolder, position: Int) {
        val item = hermandadesList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = hermandadesList.size
}