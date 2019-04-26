package br.com.rafaeldias.etracking.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.rafaeldias.etracking.R
import br.com.rafaeldias.etracking.model.Notas

class NotasAdapter(var notas: List<Notas>) : RecyclerView.Adapter<NotasAdapter.NotasViewHolder>() {

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
        val notas = notas[i]
        holder.tvNota.text = notas.numeronf
        holder.tvMercadoria.text = notas.mercadoria
        holder.tvCNPJRemetente.text = notas.cnpjremetente
        holder.tvTelefone.text = notas.telefoneremetente
        holder.tvEndereco.text = notas.enderecoentrega
    }

    class NotasViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvNota: TextView = v.findViewById(R.id.tvNota)
        var tvMercadoria: TextView = v.findViewById(R.id.tvMercadoria)
        var tvCNPJRemetente: TextView = v.findViewById(R.id.tvCNPJRemetente)
        var tvTelefone: TextView = v.findViewById(R.id.tvTelefone)
        var tvEndereco: TextView = v.findViewById(R.id.tvEndereco)
    }
}