package br.com.rafaeldias.etracking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import br.com.rafaeldias.etracking.R;


public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}