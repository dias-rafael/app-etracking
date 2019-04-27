package br.com.rafaeldias.etracking.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.rafaeldias.etracking.R
import br.com.rafaeldias.etracking.model.Notas
import kotlinx.android.synthetic.main.card_nota.view.*

class NotasAdapter(var notas: List<Notas>, val clickListener: (Notas) -> Unit) : RecyclerView.Adapter<NotasAdapter.NotasViewHolder>() {

    override fun getItemCount(): Int {
        return notas.size
    }

    fun setList(notas: List<Notas>) {
        this.notas = notas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_nota, parent, false)
        return NotasViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, i: Int) {
        (holder as NotasViewHolder).bind(notas[i], clickListener)
        val notas = notas[i]
        /*
        holder.tvtvNota.text = notas.numeronf
        holder.tvMercadoria.text = notas.mercadoria
        holder.tvCNPJRemetente.text = notas.cnpjremetente
        holder.tvTelefone.text = notas.telefoneremetente
        holder.tvEndereco.text = notas.enderecoentrega
        */


    }

    class NotasViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(part: Notas, clickListener: (Notas) -> Unit) {
            itemView.tvNota.text = part.numeronf
            itemView.tvMercadoria.text = part.mercadoria
            itemView.tvCNPJRemetente.text = part.cnpjremetente
            itemView.tvEndereco.text = part.enderecoentrega
            itemView.tvTelefone.text = part.telefoneremetente
            /*
            var tvMercadoria: TextView = itemView.findViewById(R.id.tvMercadoria)
            var tvCNPJRemetente: TextView = itemView.findViewById(R.id.tvCNPJRemetente)
            var tvTelefone: TextView = itemView.findViewById(R.id.tvTelefone)
            var tvEndereco: TextView = itemView.findViewById(R.id.tvEndereco)
            */
            itemView.setOnClickListener { clickListener(part)}
        }
    }
}