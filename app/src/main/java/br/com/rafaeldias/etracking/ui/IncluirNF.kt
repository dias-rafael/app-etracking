package br.com.rafaeldias.etracking.ui;

import android.content.Context
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast
import br.com.rafaeldias.etracking.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_incluirnf.*


class IncluirNF : Fragment(){

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,
    savedInstanceState:Bundle?):View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incluirnf, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        btCadastrar.setOnClickListener(){
            cadastrarNF()
        }

    }

    private fun cadastrarNF(){
        if ((etCNPJRemetente.text.toString() != "") && (etNumeroNF.text.toString() != "") && (etNumeroNF.text.toString() != "")) {
            val BD = "Notas"
            val editor = this.activity!!.getSharedPreferences(BD, Context.MODE_PRIVATE).edit()
            editor.putString("cnpjremetente", etCNPJRemetente.text.toString())
            editor.putString("numeronf", etNumeroNF.text.toString())
            editor.putString("mercadoria", etMercadoria.text.toString())
            val Usuario = mAuth.currentUser
            Usuario?.let {
                val emailUsuario = Usuario.email
                editor.putString("usuarioinc", emailUsuario)
            }
            editor.commit()
            Toast.makeText(activity, "Nota gravada com sucesso", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                activity,
                getString(R.string.toast_campos_em_branco),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}