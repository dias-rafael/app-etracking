package br.com.rafaeldias.etracking.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import br.com.rafaeldias.etracking.R
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val TEMPO_AGUARDO_SPLASHSCREEN = 3500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_splash)

        FirebaseMessaging.getInstance().subscribeToTopic("geral")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d("Msg tópico:", msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }

        //consultando os dados gravados localmente
        val BD = "User"
        val preferences = getSharedPreferences(BD, Context.MODE_PRIVATE)
        val isFirstOpen = preferences.getString("open_first", null)

        if (isFirstOpen != "1") {
            showSplash()
        } else {
            showLogin()
        }
    }

    private fun showLogin() {
        val nextScreen = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(nextScreen)
        finish()
    }

    private fun showSplash() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        anim.reset()
        ivLogoSobre.clearAnimation()
        ivLogoSobre.startAnimation(anim)
        tvAppSobre.clearAnimation()
        tvAppSobre.startAnimation(anim)

        Handler().postDelayed({
            showLogin()
        }, TEMPO_AGUARDO_SPLASHSCREEN)
    }
}
