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
    var usuarioinc: String? = null

    constructor() {}

    constructor(cnpjremetente: String, numeronf: String, mercadoria: String) {
        this.cnpjremetente = cnpjremetente
        this.numeronf = numeronf
        this.mercadoria = mercadoria
    }

    constructor(id: Long, cnpjremetente: String, numeronf: String, mercadoria: String, usuarioinc: String) {
        this.id = id
        this.cnpjremetente = cnpjremetente
        this.numeronf = numeronf
        this.mercadoria = mercadoria
        this.usuarioinc = usuarioinc
    }
}