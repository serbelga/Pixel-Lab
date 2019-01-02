package com.example.sergiobelda.photoeditor.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.sergiobelda.photoeditor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFigure extends Fragment {

    public TabFigure() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_figure, container, false);
    }
}
