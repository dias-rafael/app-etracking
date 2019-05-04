package br.com.rafaeldias.etracking.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rafaeldias.etracking.R
import br.com.rafaeldias.etracking.model.Notas
import kotlinx.android.synthetic.main.card_nota.view.*
import kotlinx.android.synthetic.main.card_nota.view.tvEmail
import kotlinx.android.synthetic.main.card_nota.view.tvEndereco
import kotlinx.android.synthetic.main.card_nota.view.tvNome
import kotlinx.android.synthetic.main.card_nota.view.tvSenha
import kotlinx.android.synthetic.main.card_nota.view.tvTelefone
import kotlinx.android.synthetic.main.card_nota.view.cbEntregue

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
    }

    class NotasViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(part: Notas, clickListener: (Notas) -> Unit) {
            itemView.tvEmail.text = part.numeronf
            itemView.tvSenha.text = part.mercadoria
            itemView.tvNome.text = part.cnpjremetente
            itemView.tvEndereco.text = part.enderecoentrega
            itemView.tvTelefone.text = part.telefoneremetente
            itemView.cbEntregue.isChecked = part.entregue
            itemView.setOnClickListener { clickListener(part) }
        }
    }
}