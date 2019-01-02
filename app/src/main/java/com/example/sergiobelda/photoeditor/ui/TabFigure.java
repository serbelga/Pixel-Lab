package com.example.sergiobelda.photoeditor.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sergiobelda.photoeditor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFigure extends Fragment {
    private TabFigureListener tabFigureListener;
    private Button circlesButton, squaresButton;

    public TabFigure() {
        this.tabFigureListener = null;
    }

    public void setTabFigureListener(TabFigureListener tabFigureListener) {
        this.tabFigureListener = tabFigureListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_figure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circlesButton = view.findViewById(R.id.circlesButton);
        squaresButton = view.findViewById(R.id.squaresButton);
        circlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabFigureListener.onFigureSelected(1);
            }
        });
        squaresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabFigureListener.onFigureSelected(0);
            }
        });
    }

    public interface TabFigureListener {
        void onFigureSelected(int currentFigure);
    }
}
