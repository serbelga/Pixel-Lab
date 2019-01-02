package com.example.sergiobelda.photoeditor.ui;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioGroup;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.widget.CompoundButtonCompat;
import androidx.fragment.app.Fragment;
import com.example.sergiobelda.photoeditor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPaint extends Fragment {
    private RadioGroup colorsRadioGroup;
    private int currentColor;

    private TabPaintListener tabPaintListener;

    public TabPaint() {
        this.tabPaintListener = null;
    }

    public void setTabPaintListener(TabPaintListener tabPaintListener) {
        this.tabPaintListener = tabPaintListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_paint, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        colorsRadioGroup = view.findViewById(R.id.colorsRadioGroup);
        initializeColorsRadioGroup();
    }

    private void initializeColorsRadioGroup() {
        initializeColors(colorsRadioGroup);
        colorsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedColor) {
                currentColor = checkedColor;
                tabPaintListener.onColorSelected(currentColor);
            }
        });
    }

    private void initializeColors(RadioGroup group) {
        int[] colorsArray = getResources().getIntArray(R.array.palette);
        for (int i = 0; i < colorsArray.length; i++) {
            AppCompatRadioButton button = new AppCompatRadioButton(getContext());
            CompoundButtonCompat.setButtonTintList(
                    button, ColorStateList.valueOf(convertToDisplay(colorsArray[i])));
            button.setId(colorsArray[i]);
            group.addView(button);
        }
    }

    /**
     *
     * @param color
     * @return
     */
    @ColorInt
    private int convertToDisplay(@ColorInt int color) {
        return color == Color.WHITE ? Color.BLACK : color;
    }

    public interface TabPaintListener {
        void onColorSelected(int currentColor);
    }
}
