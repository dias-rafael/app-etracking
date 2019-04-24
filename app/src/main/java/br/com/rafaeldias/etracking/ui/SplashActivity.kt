package br.com.rafaeldias.etracking.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import br.com.rafaeldias.etracking.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val TEMPO_AGUARDO_SPLASHSCREEN = 4500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_splash)

        //consultando os dados gravados localmente
        val preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val isFirstOpen = preferences.getBoolean("open_first", true)

        if (isFirstOpen) {
            showSplash()
        } else {
            //showLogin()
        }
    }

    private fun showLogin() {
        //val nextScreen = Intent(this@SplashActivity, LoginActivity::class.java)
        //startActivity(nextScreen)
        //finish()
    }

    private fun showSplash() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        anim.reset()
        ivSplash.clearAnimation()
        ivSplash.startAnimation(anim)
        tvSplash.clearAnimation()
        tvSplash.startAnimation(anim)

        Handler().postDelayed({
            showLogin()
        }, TEMPO_AGUARDO_SPLASHSCREEN)
    }
}
