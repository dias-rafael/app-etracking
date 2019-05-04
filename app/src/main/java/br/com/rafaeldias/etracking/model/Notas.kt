package br.com.rafaeldias.etracking.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Notas {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var cnpjremetente: String? = null
    var numeronf: String? = null
    var mercadoria: String? = null
    var telefoneremetente: String? = null
    var enderecoentrega: String? = null
    var usuarioinc: String? = null
    var entregue: Boolean

    constructor(id: Long, cnpjremetente: String, numeronf: String, mercadoria: String, usuarioinc: String, telefoneremetente: String, enderecoentrega: String, entregue: Boolean) {
        this.id = id
        this.cnpjremetente = cnpjremetente
        this.numeronf = numeronf
        this.mercadoria = mercadoria
        this.telefoneremetente = telefoneremetente
        this.enderecoentrega = enderecoentrega
        this.usuarioinc = usuarioinc
        this.entregue = entregue
    }
}