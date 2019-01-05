package com.example.sergiobelda.photoeditor.ui


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.sergiobelda.photoeditor.R
import kotlinx.android.synthetic.main.fragment_tab_paint.*

/**
 * Tab Paint Fragment.
 */
class TabPaint : Fragment() {
    private var colorsRadioGroup: RadioGroup? = null
    private var currentColor: Int = 0
    lateinit var tabPaintListener: TabPaintListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_paint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initializeColorPalette()
    }

    /*
    private fun initializeColorPalette() {
        palettePaint.setFixedColumnCount(resources.getIntArray(R.array.palette).size)
        //Select color
        if (currentColor == 0) {
            palettePaint.setSelectedColor(Color.BLACK)
        } else {
            palettePaint.setSelectedColor(currentColor)
        }
        //On color click
        palettePaint.setOnColorSelectedListener { color ->
            currentColor = color
            tabPaintListener.onColorSelected(currentColor)
        }
    }*/

    interface TabPaintListener {
        fun onColorSelected(currentColor: Int)
    }
}