package br.com.rafaeldias.etracking.ui;

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle;
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText
import br.com.rafaeldias.etracking.R;
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_editarnf.*

public class EditarNF : Fragment(){

    private var idNota: String = ""

    @Override
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,
            savedInstanceState:Bundle?):View? {
            // Inflate the layout for this fragment
            var bundle: Bundle = this.arguments!!
            if (bundle != null) {
                idNota = bundle.getString("idNota")
            }
            var view = inflater.inflate(R.layout.fragment_editarnf, container, false)
    return view
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tel: EditText = view.findViewById(R.id.etTelefoneContato)
        var end: EditText = view.findViewById(R.id.etEnderecoEntrega)

        ivCall.setOnClickListener({
            var uri = Uri.parse(tel.text.toString())
            //Toast.makeText(context, tel.text.toString(), Toast.LENGTH_LONG).show()
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + uri))
            if (ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {
                Log.e("DB", "CALL PERMISSION GRANTED")
                startActivity(intent)
            }
        })

        ivMaps.setOnClickListener({
            var endereco = end.text.toString()
            val intent = Intent(activity, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("endereco",endereco)
            startActivity(intent)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Toast.makeText(context, idNota, Toast.LENGTH_LONG).show()

    }
}