package br.com.rafaeldias.etracking.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.content.DialogInterface
import android.widget.Toast
import br.com.rafaeldias.etracking.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import android.widget.TextView




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.com.rafaeldias.etracking.R.layout.activity_main)
        val toolbar = findViewById(br.com.rafaeldias.etracking.R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
/*
        val fab = findViewById(br.com.rafaeldias.etracking.R.id.fab) as FloatingActionButton
        fab.setOnClickListener(object : View.OnClickListener() {
            override fun onClick(view: View) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        })
        */

        val drawer = findViewById(br.com.rafaeldias.etracking.R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, br.com.rafaeldias.etracking.R.string.navigation_drawer_open, br.com.rafaeldias.etracking.R.string.navigation_drawer_close
        )
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(br.com.rafaeldias.etracking.R.id.nvMenu) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        // Obtém a referência da view de cabeçalho
        val headerView = navigationView.getHeaderView(0)

        var emailUsuario = intent!!.getStringExtra("emailUsuario")

        // Obtém a referência do nome do usuário e altera seu nome
        val txtEmailUsuarioLogado = headerView.findViewById(R.id.tvEmailUsuarioLogado) as TextView
        txtEmailUsuarioLogado.text = emailUsuario

    }

    override fun onBackPressed() {
        val drawer = findViewById(br.com.rafaeldias.etracking.R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(br.com.rafaeldias.etracking.R.menu.main, menu)
        return true
    }
*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()


        return if (id == br.com.rafaeldias.etracking.R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.getItemId()) {
            br.com.rafaeldias.etracking.R.id.nav_incluirnf -> changeFragment(IncluirNF())
            br.com.rafaeldias.etracking.R.id.nav_listarnf -> changeFragment(ListarNF())
            br.com.rafaeldias.etracking.R.id.nav_registrarstatus -> changeFragment(RegistrarStatus())
            br.com.rafaeldias.etracking.R.id.nav_sobre -> changeFragment(Sobre())
            br.com.rafaeldias.etracking.R.id.nav_sair -> finish()
        }

        val drawer = findViewById(br.com.rafaeldias.etracking.R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(br.com.rafaeldias.etracking.R.id.content_main, fragment)
        ft.commit()
    }
}