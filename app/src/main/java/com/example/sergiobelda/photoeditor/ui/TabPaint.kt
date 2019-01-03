package com.example.sergiobelda.photoeditor.ui


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.RadioGroup
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.widget.CompoundButtonCompat
import androidx.fragment.app.Fragment
import com.example.sergiobelda.photoeditor.R

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
        colorsRadioGroup = view.findViewById(R.id.colorsRadioGroup)
        initializeColorsRadioGroup()
    }

    private fun initializeColorsRadioGroup() {
        initializeColors(colorsRadioGroup)
        colorsRadioGroup!!.setOnCheckedChangeListener { group, checkedColor ->
            currentColor = checkedColor
            tabPaintListener!!.onColorSelected(currentColor)
        }
    }

    private fun initializeColors(group: RadioGroup?) {
        val colorsArray = resources.getIntArray(R.array.palette)
        for (i in colorsArray.indices) {
            val button = AppCompatRadioButton(context!!)
            CompoundButtonCompat.setButtonTintList(
                button, ColorStateList.valueOf(convertToDisplay(colorsArray[i]))
            )
            button.id = colorsArray[i]
            group!!.addView(button)
        }
    }

    /**
     *
     * @param color
     * @return
     */
    @ColorInt
    private fun convertToDisplay(@ColorInt color: Int): Int {
        return if (color == Color.WHITE) Color.BLACK else color
    }

    interface TabPaintListener {
        fun onColorSelected(currentColor: Int)
    }
}