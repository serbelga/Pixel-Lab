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
import com.google.android.material.tabs.TabLayout
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
        tabFigureListener.onFigureSelected(0)
        //circlesButton!!.setOnClickListener { tabFigureListener.onFigureSelected(CIRCLE) }
        //squaresButton!!.setOnClickListener { tabFigureListener.onFigureSelected(SQUARE) }
        //linesButton!!.setOnClickListener { tabFigureListener.onFigureSelected(LINE) }
        initializeFigureTabLayout()
    }

    private fun initializeFigureTabLayout() {
        figureTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabFigureListener.onFigureSelected(tab!!.position)
            }

        })
    }

    interface TabFigureListener {
        fun onFigureSelected(currentFigure: Int)
    }
}