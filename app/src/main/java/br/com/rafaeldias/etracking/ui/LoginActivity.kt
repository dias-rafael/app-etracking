package br.com.rafaeldias.etracking.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.rafaeldias.etracking.R
import br.com.rafaeldias.etracking.utils.ValidaEmail
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        ConsultaBaseLocal()

        btLogar.setOnClickListener {
            btLogar.setText(getString(R.string.aguarde))
            btLogar.isEnabled=false
            if (!ValidaEmail.isEmailValid(etEmail.text.toString())) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_usuario_incorreto), Toast.LENGTH_SHORT)
                    .show()
                btLogar.setText(getString(R.string.logar))
                btLogar.isEnabled = true
            }

            else {
                if(etSenha.text.toString() != "") {
                    mAuth.signInWithEmailAndPassword(
                        etEmail.text.toString(),
                        etSenha.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            GravaBaseLocal()
                            vaiParaTelaPrincipal()
                        } else {
                            Toast.makeText(this@LoginActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                            btLogar.setText(getString(R.string.logar))
                            btLogar.isEnabled = true
                        }
                    }
                } else{
                    Toast.makeText(getApplicationContext(),getString(R.string.toast_senha_invalida),Toast.LENGTH_SHORT).show()
                    btLogar.setText(getString(R.string.logar))
                    btLogar.isEnabled = true
                }
            }
        }

        btCriarConta.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun vaiParaTelaPrincipal(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val Usuario = mAuth.currentUser
        Usuario?.let {
            val emailUsuario = Usuario.email
            intent.putExtra("emailUsuario", emailUsuario)
        }
        startActivity(intent)
        finish()
    }

    private fun GravaBaseLocal(){
        val BD = "User"
        val editor = getSharedPreferences(BD, Context.MODE_PRIVATE).edit()

        editor.putString("email", etEmail.text.toString())
        if (swManterConectado.isChecked){
            editor.putString("open_first", "1")
        } else {
            editor.putString("open_first", "0")
        }
        editor.commit()
    }

    private fun ConsultaBaseLocal(){
        val BD = "User"
        val prefs = getSharedPreferences(BD, Context.MODE_PRIVATE)
        val user = prefs.getString("email", null)
        val open_first = prefs.getString("open_first", null)
        if (user != null) {
            etEmail.setText(user)
        }
        if ((mAuth.currentUser != null) && (open_first == "1")){
                vaiParaTelaPrincipal()
        }
    }
}