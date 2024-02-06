package com.alvarols01.capatapp2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvarols01.capatapp2.Hermandades
import com.alvarols01.capatapp2.databinding.ItemHermandadesBinding
import com.bumptech.glide.Glide

class HermandadesAdapter(
    private val hermandadesList: List<Hermandades>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<HermandadesAdapter.HermandadesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HermandadesViewHolder {
        val binding = ItemHermandadesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HermandadesViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: HermandadesViewHolder, position: Int) {
        val hermandad = hermandadesList[position]
        holder.bind(hermandad)
    }

    override fun getItemCount(): Int = hermandadesList.size

    class HermandadesViewHolder(
        private val binding: ItemHermandadesBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hermandad: Hermandades) {
            with(binding) {
                tvNombreHermandad.text = hermandad.nombre
                tvNombreIglesia.text = hermandad.iglesia
                // Asume que tienes un campo 'dia' en tu objeto Hermandades si quieres mostrarlo
                tvDia.text = hermandad.dia
                Glide.with(ivHermandad.context)
                    .load(hermandad.photo)
                    .into(ivHermandad)

                // Configura el clic listener para cada ítem
                root.setOnClickListener {
                    onClick(hermandad.nombre)
                }
            }
        }
    }
}
