package br.com.rafaeldias.etracking.ui;

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle;
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText
import br.com.rafaeldias.etracking.R;
import android.widget.Toast
import br.com.rafaeldias.etracking.database.AppDatabase
import br.com.rafaeldias.etracking.model.Notas
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_editarnf.*
import kotlinx.android.synthetic.main.fragment_editarnf.btAlterar
import kotlinx.android.synthetic.main.fragment_editarnf.etCNPJRemetente
import kotlinx.android.synthetic.main.fragment_editarnf.etEnderecoEntrega
import kotlinx.android.synthetic.main.fragment_editarnf.etMercadoria
import kotlinx.android.synthetic.main.fragment_editarnf.etNumeroNF
import kotlinx.android.synthetic.main.fragment_editarnf.etTelefoneContato
import kotlinx.android.synthetic.main.fragment_incluirnf.*

public class EditarNF : Fragment(){

    private lateinit var mAuth: FirebaseAuth

    private var idNota: String = ""
    private var cnpjRemetente: String = ""
    private var numeroNF: String = ""
    private var mercadoria: String = ""
    private var enderecoEntrega: String = ""
    private var telefoneContato: String = ""

    @Override
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,
            savedInstanceState:Bundle?):View? {
            // Inflate the layout for this fragment
            var bundle: Bundle = this.arguments!!
            if (bundle != null) {
                idNota = bundle.getString("idNota")
                cnpjRemetente = bundle.getString("cnpjRemetente")
                numeroNF = bundle.getString("numeroNF")
                mercadoria = bundle.getString("mercadoria")
                enderecoEntrega = bundle.getString("enderecoEntrega")
                telefoneContato = bundle.getString("telefoneContato")
            }
            var view = inflater.inflate(R.layout.fragment_editarnf, container, false)
    return view
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etCNPJRemetente.setText(cnpjRemetente)
        etNumeroNF.setText(numeroNF)
        etMercadoria.setText(mercadoria)
        etTelefoneContato.setText(telefoneContato)
        etEnderecoEntrega.setText(enderecoEntrega)
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
        mAuth = FirebaseAuth.getInstance()
        super.onActivityCreated(savedInstanceState)
        //Toast.makeText(context, idNota, Toast.LENGTH_LONG).show()
        val Usuario = mAuth.currentUser
        val emailUsuario = Usuario!!.email
        val db = AppDatabase.getDatabase(activity!!.applicationContext)

        val notasObj = Notas(idNota.toLong(),etCNPJRemetente.text.toString(),etNumeroNF.text.toString(),etMercadoria.text.toString(),emailUsuario.toString(),etTelefoneContato.text.toString(),etEnderecoEntrega.text.toString())

        btExcluir.setOnClickListener(){
            if (idNota != "") {
                excluirNF(db!!).execute(notasObj)
                Toast.makeText(context, "Nota Excluída", Toast.LENGTH_LONG).show()
                Handler().postDelayed({
                    telaInicial()
                }, 3000)
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.toast_nf_nao_encontrada),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        btAlterar.setOnClickListener() {
            if ((etCNPJRemetente.text.toString() != "") && (etNumeroNF.text.toString() != "") && (etMercadoria.text.toString() != "") && (etTelefoneContato.text.toString() != "") && (etEnderecoEntrega.text.toString() != "")) {
                alterarNF(db!!).execute(notasObj)
                Toast.makeText(context, "Nota Alterada", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.toast_campos_em_branco),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private inner class excluirNF internal constructor(appDatabase: AppDatabase) : AsyncTask<Notas, Void, String>() {
        private val db: AppDatabase = appDatabase

        override fun doInBackground(vararg params: Notas): String {
            db.NotasDao().del(params[0].id)
            return ""
        }

    }

    private inner class alterarNF internal constructor(appDatabase: AppDatabase) : AsyncTask<Notas, Void, String>() {
        private val db: AppDatabase = appDatabase

        override fun doInBackground(vararg params: Notas): String {
            db.NotasDao().add(params[0])
            return ""
        }

    }

    private fun telaInicial() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val Usuario = mAuth.currentUser
        Usuario?.let {
            val emailUsuario = Usuario.email
            intent.putExtra("emailUsuario", emailUsuario)
        }
        startActivity(intent)
    }
}