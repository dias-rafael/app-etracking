package br.com.rafaeldias.etracking.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.rafaeldias.etracking.R
import br.com.rafaeldias.etracking.model.User
import br.com.rafaeldias.etracking.utils.ValidaEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        btAlterar.setOnClickListener {
            if (!ValidaEmail.isEmailValid(etEmail.text.toString()))
                Toast.makeText(getApplicationContext(),getString(R.string.toast_usuario_incorreto),Toast.LENGTH_SHORT).show()
            else {
                if ((etSenha.text.toString() != "") && (etCNPJRemetente.text.toString() != "") && (etTelefone.text.toString() != "")) {
                    mAuth.createUserWithEmailAndPassword(
                        etEmail.text.toString(),
                        etSenha.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            salvarNoRealtimeDatabase()
                        } else {
                            Toast.makeText(this@SignUpActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.toast_campos_em_branco),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun salvarNoRealtimeDatabase() {
        val user = User(etCNPJRemetente.text.toString(), etEmail.text.toString(), etTelefone.text.toString())
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao criar o usuário", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
