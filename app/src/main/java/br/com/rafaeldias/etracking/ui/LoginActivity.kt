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

        if(mAuth.currentUser != null) {
            vaiParaTelaPrincipal()
        } else {
            ConsultaBaseLocal()
        }

        btLogar.setOnClickListener {
            if (!ValidaEmail.isEmailValid(etNumeroNF.text.toString()))
                Toast.makeText(getApplicationContext(),getString(R.string.toast_usuario_incorreto),Toast.LENGTH_SHORT).show()
            else {
                if(etMercadoria.text.toString() != "") {
                    mAuth.signInWithEmailAndPassword(
                        etNumeroNF.text.toString(),
                        etMercadoria.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            GravaBaseLocal()
                            vaiParaTelaPrincipal()
                        } else {
                            Toast.makeText(this@LoginActivity, it.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else{
                    Toast.makeText(getApplicationContext(),getString(R.string.toast_senha_invalida),Toast.LENGTH_SHORT).show()
                }
            }
        }

        btNovaConta.setOnClickListener {
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

        editor.putString("email", etNumeroNF.text.toString())
        editor.commit()
    }

    private fun ConsultaBaseLocal(){
        val BD = "User"
        val prefs = getSharedPreferences(BD, Context.MODE_PRIVATE)
        val user = prefs.getString("email", null)
        if (user != null) {
            etNumeroNF.setText(user)
        }
    }
}