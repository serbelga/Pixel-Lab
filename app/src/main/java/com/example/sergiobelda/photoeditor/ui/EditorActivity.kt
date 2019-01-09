package com.example.sergiobelda.photoeditor.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.example.sergiobelda.photoeditor.R
import com.example.sergiobelda.photoeditor.editableimageview.EditableImageView
import com.example.sergiobelda.photoeditor.editableimageview.EditorTool.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_editor.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class EditorActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 99
    lateinit var editableImageView : EditableImageView
    lateinit var toolsBottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        this.title = ""
        editableImageView = myImageView
        setImage()
        initializePalette()
        initializeSave()
        initializeTabLayout()
        initializeViewPager()
        initializeBottomSheetBehavior()
        initializeBottomNavigationView()
    }

    private fun initializeSave() {
        saveFab.setOnClickListener {
            saveImage()
        }
    }

    private fun initializePalette() {
        palettePaint.setSelectedColor(Color.WHITE)
        paletteFab.setOnClickListener {
            paletteFab.isExpanded = !paletteFab.isExpanded
        }
        scrim.setOnClickListener {
            paletteFab.isExpanded = false
        }
        palettePaint.setOnColorSelectedListener { color ->
            editableImageView.setCurrentColor(color)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.undo_redo_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        if (paletteFab.isExpanded) {
            paletteFab.isExpanded = false
        }
        if (toolsBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            toolsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        } else if (saveMode) {
            exitSaveMode()
        } else {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this, R.style.AppAlertDialog)
            materialAlertDialogBuilder.setMessage(R.string.lose_work)
            materialAlertDialogBuilder.setPositiveButton(R.string.yes) { _, _ ->
                super.onBackPressed()
            }
            materialAlertDialogBuilder.setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            val materialAlertDialog = materialAlertDialogBuilder.create()
            materialAlertDialog.show()
        }
    }

    private fun initializeBottomNavigationView() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.tools -> {
                    toolsBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    myImageView.setEditMode(PAINT)
                }
                R.id.export -> {
                    enterSaveMode()
                }
            }
            true
        }
    }

    private var saveMode = false

    private fun enterSaveMode() {
        bottom_navigation.visibility = View.GONE
        paletteFab.visibility = View.GONE
        saveFab.visibility = View.VISIBLE
        toolbar.visibility = View.GONE
        nameTextInputLayout.visibility = View.VISIBLE
        toolsBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        editableImageView.setEditMode(-1)
        motionLayout.transitionToEnd()
        saveMode = true
    }

    private fun exitSaveMode() {
        bottom_navigation.visibility = View.VISIBLE
        paletteFab.visibility = View.VISIBLE
        saveFab.visibility = View.GONE
        toolbar.visibility = View.VISIBLE
        nameTextInputLayout.visibility = View.GONE
        motionLayout.transitionToStart()
        saveMode = false
    }

    private fun checkWriteExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            saveImage()
        }
    }

    private fun saveImage() {
        editableImageView.isDrawingCacheEnabled = true
        editableImageView.buildDrawingCache(true)
        val bitmap = editableImageView.drawingCache
        val root = Environment.getExternalStorageDirectory()
        val rnd = Random()
        val i = rnd.nextInt(1000)
        val cachePath : File
        cachePath = if (!saveTextInputEdit.text!!.equals("")) {
            File(root.absolutePath + "/DCIM/Camera/" + saveTextInputEdit.text + ".jpg")
        } else {
            File(root.absolutePath + "/DCIM/Camera/image_" + i.toString() + ".jpg")
        }
        try {
            cachePath.createNewFile()
            val ostream = FileOutputStream(cachePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream)
            ostream.flush()
            ostream.close()
            showSnackbar("Se ha guardado correctamente")
        } catch (e: Exception) {
            showSnackbar(e.message!!)
            e.printStackTrace()
        }
        editableImageView.isDrawingCacheEnabled = false
    }

    private fun showSnackbar(message : String){
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                exitSaveMode()
            }
        }).show()
    }

    private fun initializeBottomSheetBehavior() {
        toolsBottomSheetBehavior = BottomSheetBehavior.from(toolsBottomSheet)
        toolsBottomSheetBehavior.setBottomSheetCallback(createBottomSheetCallback())
    }

    private fun createBottomSheetCallback(): BottomSheetBehavior.BottomSheetCallback? {
        return object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        var tabPosition = toolsTabLayout.selectedTabPosition
                        myImageView.setEditMode(tabPosition)
                    }
                }
            }

        }
    }

    private fun initializeViewPager() {
        val pagerAdapter = ToolsViewPagerAdapter(supportFragmentManager, toolsTabLayout.tabCount, editableImageView)
        toolsViewPager.adapter = pagerAdapter
        toolsViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(toolsTabLayout))
    }

    /**
     * Sets the Edit Mode of the EditableImageView in function of the tab selected
     */
    private fun initializeTabLayout() {
        toolsTabLayout.addOnTabSelectedListener(object : TabLayout.ViewPagerOnTabSelectedListener(toolsViewPager) {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                super.onTabReselected(tab)
                myImageView.setEditMode(tab!!.position)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                super.onTabSelected(tab)
                toolsViewPager.currentItem = tab!!.position
                myImageView.setEditMode(tab.position)
            }
        })
    }

    /**
     * Sets the image to be edited
     */
    private fun setImage() {
        var uri = Uri.parse(intent.getStringExtra("image"))
        Glide.with(this).load(uri).into(myImageView)
    }

    private class ToolsViewPagerAdapter(fm: FragmentManager, var tabsNum: Int, var editableImageView: EditableImageView) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            if (position == PAINT) {
                fragment = TabPaint()
                fragment.tabPaintListener = object : TabPaint.TabPaintListener {
                    override fun onStrokeChanged(currentStroke: Float) {
                        editableImageView.setCurrentStroke(currentStroke)
                    }
                }
            } else if (position == FIGURE) {
                fragment = TabFigure()
                fragment.tabFigureListener = object : TabFigure.TabFigureListener {
                    override fun onFigureSelected(currentFigure: Int) {
                        editableImageView.setFigureMode(currentFigure)
                    }
                }
            } else if (position == STICKER) {
                fragment = TabSticker()
                fragment.tabStickerListener = object : TabSticker.TabStickerListener {
                    override fun onStickerSelected() {

                    }
                }
            }
            return fragment!!
        }

        override fun getCount(): Int {
            return tabsNum
        }

    }
}
