package br.com.rafaeldias.etracking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import br.com.rafaeldias.etracking.R;
import br.com.rafaeldias.etracking.adapter.NotasAdapter
import br.com.rafaeldias.etracking.model.Notas
import kotlinx.android.synthetic.main.content_nf.*
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.widget.Toast


public class ListarNF : Fragment(){

    private var  adapter: NotasAdapter? = null

    private var  notas: List<Notas> = listOf()

    @Override
    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?,
                              savedInstanceState:Bundle?):View? = inflater.inflate(R.layout.fragment_listarnf, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotas.layoutManager = LinearLayoutManager(this.activity)
        adapter = NotasAdapter(notas!!)
        rvNotas.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listarNotas()
    }

    private fun listarNotas() {
        //of() — indica a activity ou Fragment em que o ViewModel será utilizado
        //get() — indica o ViewModel que será utilizado.
        ViewModelProviders.of(this)
            .get(ListaNotasViewModel::class.java)
            .notas
            .observe(this, Observer<List<Notas>> { notas ->
                adapter?.setList(notas!!)
                rvNotas.adapter!!.notifyDataSetChanged()
            })
    }

}
