package com.example.sergiobelda.photoeditor.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.sergiobelda.photoeditor.R

/**
 * TabSticker Fragment
 */
class TabSticker : Fragment() {
    lateinit var tabStickerListener: TabStickerListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_sticker, container, false)
    }

    public interface TabStickerListener {
        fun onStickerSelected()
    }
}// Required empty public constructor
