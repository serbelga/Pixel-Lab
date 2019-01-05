package com.example.sergiobelda.photoeditor.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.sergiobelda.photoeditor.R
import com.example.sergiobelda.photoeditor.editableimageview.figures.Figure.*
import kotlinx.android.synthetic.main.fragment_tab_figure.*
import kotlinx.android.synthetic.main.fragment_tab_paint.*

/**
 * Tab Figure Fragment.
 */
class TabFigure : Fragment() {
    lateinit var tabFigureListener: TabFigureListener
    private var currentColor: Int = 0
    lateinit var tabPaintListener: TabPaint.TabPaintListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_figure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        circlesButton!!.setOnClickListener { tabFigureListener.onFigureSelected(CIRCLE) }
        squaresButton!!.setOnClickListener { tabFigureListener.onFigureSelected(SQUARE) }
        linesButton!!.setOnClickListener { tabFigureListener.onFigureSelected(LINE) }
        //initializeColorPalette()
    }

    /*
    private fun initializeColorPalette() {
        paletteFigure.setFixedColumnCount(resources.getIntArray(R.array.palette).size)
        //Select color
        if (currentColor == 0) {
            paletteFigure.setSelectedColor(Color.BLACK)
        } else {
            paletteFigure.setSelectedColor(currentColor)
        }
        //On color click
        paletteFigure.setOnColorSelectedListener { color ->
            currentColor = color
            tabPaintListener.onColorSelected(currentColor)
        }
    }*/

    interface TabFigureListener {
        fun onFigureSelected(currentFigure: Int)
    }
}