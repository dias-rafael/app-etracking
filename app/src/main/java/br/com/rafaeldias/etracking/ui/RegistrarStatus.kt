package br.com.rafaeldias.etracking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.rafaeldias.etracking.R;
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.content.Intent.getIntent
import android.widget.Toast


public class RegistrarStatus : Fragment(){

    private var idNota: String = "X"

    @Override
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,
            savedInstanceState:Bundle?):View? {
            // Inflate the layout for this fragment
            var bundle: Bundle = this.arguments!!
            idNota = bundle.getString("idNota")
            var view = inflater.inflate(R.layout.fragment_registrarstatus, container, false)
    return view
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Toast.makeText(context, idNota, Toast.LENGTH_LONG).show()
    }
}