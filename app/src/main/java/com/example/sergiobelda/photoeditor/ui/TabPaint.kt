package com.example.sergiobelda.photoeditor.ui


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initializeColorPalette()
        initializeStrokeListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeStrokeListener() {
        strokeSeekBar.max = 32
        strokeSeekBar.min = 8
        strokeText.text = strokeSeekBar.progress.toString()
        strokeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val stroke = Math.max(8, Math.min(32, progress))
                strokeText.text = stroke.toString()
                tabPaintListener.onStrokeChanged(stroke.toFloat())
            }
        })
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
        fun onStrokeChanged(currentStroke: Float)
    }
}