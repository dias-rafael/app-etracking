package br.com.rafaeldias.etracking.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import br.com.rafaeldias.etracking.R
import android.widget.TextView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var emailUsuario: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, br.com.rafaeldias.etracking.R.string.navigation_drawer_open, br.com.rafaeldias.etracking.R.string.navigation_drawer_close
        )
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nvMenu) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        // Obtém a referência da view de cabeçalho
        val headerView = navigationView.getHeaderView(0)

        emailUsuario = intent!!.getStringExtra("emailUsuario")

        // Obtém a referência do nome do usuário e altera seu nome
        val txtEmailUsuarioLogado = headerView.findViewById(R.id.tvSlogan) as TextView
        txtEmailUsuarioLogado.text = emailUsuario

    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.getItemId()) {
            R.id.nav_incluirnf -> changeFragment(IncluirNF())
            R.id.nav_listarnf -> changeFragment(ListarNF())
            R.id.nav_sobre -> changeFragment(Sobre())
            R.id.nav_sair -> finish()
            R.id.nav_desconectar -> Sair()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun changeFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_main, fragment)
        ft.commit()
    }

    private fun Sair(){
        val BD = "User"
        val editor = getSharedPreferences(BD, Context.MODE_PRIVATE).edit()

        editor.putString("open_first", "0")
        editor.commit()

        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}