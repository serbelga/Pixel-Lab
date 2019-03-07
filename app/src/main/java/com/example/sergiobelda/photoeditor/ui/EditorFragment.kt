package com.example.sergiobelda.photoeditor.ui


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController

import com.example.sergiobelda.photoeditor.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.fragment_editor.*

/**
 * A simple [Fragment] subclass.
 *
 */
class EditorFragment : Fragment() {
    private lateinit var toolsBottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.undo_redo_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView.setImageResource(R.color.gray)


        initializeBottomSheetBehavior()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.export -> {
                val extras = FragmentNavigatorExtras(
                    imageView to "imageView"
                )
                findNavController().navigate(R.id.saveAction,
                    null, // Bundle of args
                    null, // NavOptions
                    extras)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeBottomSheetBehavior() {
        toolsBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        toolsBottomSheetBehavior.setBottomSheetCallback(createBottomSheetCallback())
        toolsBottomSheetBehavior.state =  BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun createBottomSheetCallback(): BottomSheetBehavior.BottomSheetCallback? {
        return object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        //var tabPosition = toolsTabLayout.selectedTabPosition
                        //myImageView.setEditMode(tabPosition)
                    }
                }
            }

        }
    }
}
