package com.example.sergiobelda.photoeditor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.sergiobelda.photoeditor.R
import com.example.sergiobelda.photoeditor.editableimageview.figures.Figure.CIRCLE
import com.example.sergiobelda.photoeditor.editableimageview.figures.Figure.SQUARE

/**
 * Tab Figure Fragment.
 */
class TabFigure : Fragment() {
    lateinit var tabFigureListener: TabFigureListener
    private var circlesButton: Button? = null
    private var squaresButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_figure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        circlesButton = view.findViewById(R.id.circlesButton)
        squaresButton = view.findViewById(R.id.squaresButton)
        circlesButton!!.setOnClickListener { tabFigureListener.onFigureSelected(CIRCLE) }
        squaresButton!!.setOnClickListener { tabFigureListener.onFigureSelected(SQUARE) }
    }

    interface TabFigureListener {
        fun onFigureSelected(currentFigure: Int)
    }
}