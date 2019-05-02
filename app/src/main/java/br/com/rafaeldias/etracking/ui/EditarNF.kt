package br.com.rafaeldias.etracking.ui;

import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.fragment_editarnf.btCriarConta
import kotlinx.android.synthetic.main.fragment_editarnf.etCNPJ
import kotlinx.android.synthetic.main.fragment_editarnf.etEnderecoEntrega
import kotlinx.android.synthetic.main.fragment_editarnf.etSenha
import kotlinx.android.synthetic.main.fragment_editarnf.etEmail
import kotlinx.android.synthetic.main.fragment_editarnf.etTelefoneContato

class EditarNF : Fragment(){

    private lateinit var mAuth: FirebaseAuth
    private lateinit var builder: AlertDialog.Builder
    private lateinit var notasObj: Notas

    private var idNota: String = ""
    private var cnpjRemetente: String = ""
    private var numeroNF: String = ""
    private var mercadoria: String = ""
    private var enderecoEntrega: String = ""
    private var telefoneContato: String = ""

    private lateinit var cnpj: EditText
    private lateinit var nf: EditText
    private lateinit var merc: EditText
    private lateinit var tel: EditText
    private lateinit var end: EditText
    private  var emailUsuario: String? = ""


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

        mAuth = FirebaseAuth.getInstance()
        builder = AlertDialog.Builder(activity)

        val Usuario = mAuth.currentUser
        emailUsuario = Usuario!!.email

        etCNPJ.setText(cnpjRemetente)
        etEmail.setText(numeroNF)
        etSenha.setText(mercadoria)
        etTelefoneContato.setText(telefoneContato)
        etEnderecoEntrega.setText(enderecoEntrega)
        cnpj = view.findViewById(R.id.etCNPJ)
        nf = view.findViewById(R.id.etEmail)
        merc = view.findViewById(R.id.etSenha)
        tel = view.findViewById(R.id.etTelefoneContato)
        end = view.findViewById(R.id.etEnderecoEntrega)

        ivCall.setOnClickListener(){
            var uri = Uri.parse(tel.text.toString())
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + uri))
            if (ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {
                startActivity(intent)
            }
        }

        ivMaps.setOnClickListener(){
            var endereco = end.text.toString()
            val intent = Intent(activity, MapsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("endereco",endereco)
            startActivity(intent)
        }

        ivShare.setOnClickListener(){
            var shareContent: String
            shareContent = "Número NF: " + nf.text.toString()
            shareContent += " / Mercadoria: " + merc.text.toString()
            shareContent += " / Endereço Entrega: " + end.text.toString()
            shareContent += " / Telefone Contato: " + tel.text.toString()
            shareContent += " / CNPJ Remetente: " + cnpj.text.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = ("text/html")
            intent.putExtra(Intent.EXTRA_TEXT, (shareContent))
            val intentChooser = Intent.createChooser( intent, getString(R.string.chooser) )
            startActivity(intentChooser)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val db = AppDatabase.getDatabase(activity!!.applicationContext)
        notasObj = Notas(idNota.toLong(),"","","","","","")

        btExcluir.setOnClickListener(){
            if (idNota != "") {
                excluirNF(db!!).execute(notasObj)
                Toast.makeText(context, getString(R.string.nota_excluida), Toast.LENGTH_LONG).show()
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

        btCriarConta.setOnClickListener() {
            if ((cnpj.text.toString() != "") && (etEmail.text.toString() != "") && (etSenha.text.toString() != "") && (etTelefoneContato.text.toString() != "") && (etEnderecoEntrega.text.toString() != "")) {
                alterarNF(db!!).execute(notasObj)
                Toast.makeText(context, getString(R.string.nota_alterada), Toast.LENGTH_LONG).show()
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
        val cnpjnovo = cnpj.text.toString()
        val nfnovo = nf.text.toString()
        val mercnovo = merc.text.toString()
        val telnovo = tel.text.toString()
        val endnovo = end.text.toString()
        override fun doInBackground(vararg params: Notas): String {
            db.NotasDao().atualizaNF(params[0].id,cnpjnovo,nfnovo,mercnovo,emailUsuario,telnovo,endnovo)
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