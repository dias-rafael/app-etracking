package br.com.rafaeldias.etracking.ui;

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle;
import android.os.Handler
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast
import br.com.rafaeldias.etracking.R
import br.com.rafaeldias.etracking.database.AppDatabase
import br.com.rafaeldias.etracking.model.Notas
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_incluirnf.*


class IncluirNF : Fragment(){

    private lateinit var mAuth: FirebaseAuth
    private lateinit var builder: AlertDialog.Builder
    //private var appDatabase: AppDatabase? = null

    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,
    savedInstanceState:Bundle?):View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incluirnf, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        builder = AlertDialog.Builder(activity)

        btAlterar.setOnClickListener(){
            if ((etCNPJRemetente.text.toString() != "") && (etEmail.text.toString() != "") && (etSenha.text.toString() != "") && (etTelefoneContato.text.toString() != "") && (etEnderecoEntrega.text.toString() != "")) {
                val db = AppDatabase.getDatabase(activity!!.applicationContext)
                val Usuario = mAuth.currentUser
                Usuario?.let {
                    val emailUsuario = Usuario.email
                    val notasObj = Notas(0,etCNPJRemetente.text.toString(),etEmail.text.toString(),etSenha.text.toString(),emailUsuario.toString(),etTelefoneContato.text.toString(),etEnderecoEntrega.text.toString())
                    cadastrarNF(db!!).execute(notasObj)
                    Toast.makeText(context, "Nota Cadastrada", Toast.LENGTH_LONG).show()
                    Handler().postDelayed({
                        telaInicial()
                    }, 3000)
                }

            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.toast_campos_em_branco),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private inner class cadastrarNF internal constructor(appDatabase: AppDatabase) : AsyncTask<Notas, Void, String>() {
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