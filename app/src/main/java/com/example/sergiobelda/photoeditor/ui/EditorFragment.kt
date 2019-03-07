package com.example.sergiobelda.photoeditor.ui


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras

import com.example.sergiobelda.photoeditor.R
import kotlinx.android.synthetic.main.fragment_editor.*

/**
 * A simple [Fragment] subclass.
 *
 */
class EditorFragment : Fragment() {

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
        button.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                imageView to "imageView")
            view.findNavController().navigate(R.id.saveAction,
                null, // Bundle of args
                null, // NavOptions
                extras)
        }

    }
}
