package br.com.rafaeldias.etracking.ui;

import android.app.AlertDialog
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

        btCriarConta.setOnClickListener(){
            if ((etNome.text.toString() != "") && (etEmail.text.toString() != "") && (etSenha.text.toString() != "") && (etTelefoneContato.text.toString() != "") && (etEnderecoEntrega.text.toString() != "")) {
                val db = AppDatabase.getDatabase(activity!!.applicationContext)
                val Usuario = mAuth.currentUser
                Usuario?.let {
                    val emailUsuario = Usuario.email
                    val notasObj = Notas(0,etNome.text.toString(),etEmail.text.toString(),etSenha.text.toString(),emailUsuario.toString(),etTelefoneContato.text.toString(),etEnderecoEntrega.text.toString(),false)
                    cadastrarNF(db!!).execute(notasObj)
                    Toast.makeText(context, getString(R.string.nota_cadastrada), Toast.LENGTH_LONG).show()
                    Handler().postDelayed({
                        //telaInicial()
                        etNome.setText("")
                        etEmail.setText("")
                        etSenha.setText("")
                        etTelefoneContato.setText("")
                        etEnderecoEntrega.setText("")
                    }, 2500)
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

}