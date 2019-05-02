package br.com.rafaeldias.etracking.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rafaeldias.etracking.R

class Sobre : Fragment(){

    @Override
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,
                              savedInstanceState:Bundle?):View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sobre, container, false)
        return view
    }
}