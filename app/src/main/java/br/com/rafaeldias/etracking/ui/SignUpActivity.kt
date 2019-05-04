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

        btCriarConta.setOnClickListener {
            btCriarConta.setText(getString(R.string.aguarde))
            btCriarConta.isEnabled=false
            if (!ValidaEmail.isEmailValid(etEmail.text.toString())) {
                Toast.makeText(getApplicationContext(),getString(R.string.toast_usuario_incorreto),Toast.LENGTH_SHORT).show()
                btCriarConta.setText(getString(R.string.criar_conta))
                btCriarConta.isEnabled = true
            }
            else {
                if ((etSenha.text.toString() != "") && (etNome.text.toString() != "") && (etTelefone.text.toString() != "")) {
                    mAuth.createUserWithEmailAndPassword(
                        etEmail.text.toString(),
                        etSenha.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            salvarNoRealtimeDatabase()
                        } else {
                            Toast.makeText(this@SignUpActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                            btCriarConta.setText(getString(R.string.criar_conta))
                            btCriarConta.isEnabled = true
                        }
                    }
                } else {
                    Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.toast_campos_em_branco),
                        Toast.LENGTH_SHORT
                    ).show()
                    btCriarConta.setText(getString(R.string.criar_conta))
                    btCriarConta.isEnabled = true
                }
            }
        }
    }

    private fun salvarNoRealtimeDatabase() {
        val user = User(etNome.text.toString(), etEmail.text.toString(), etTelefone.text.toString())
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, getString(R.string.usuario_criado), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.erro_criar_usuario), Toast.LENGTH_SHORT).show()
                }
            }
    }
}
