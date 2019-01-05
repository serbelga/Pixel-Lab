package com.example.sergiobelda.photoeditor.uifragments


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.sergiobelda.photoeditor.R
import kotlinx.android.synthetic.main.fragment_start.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Start Fragment.
 */
class StartFragment : Fragment() {
    private val CAMERA_REQUEST_CODE = 0
    private val GALLERY_REQUEST_CODE = 1
    val PERMISSIONS_REQUEST_CAMERA = 99
    lateinit var photoUri : Uri
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraButton.setOnClickListener {
            checkCameraPermission()
        }
        galleryButton.setOnClickListener {
            var pickPhoto = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, GALLERY_REQUEST_CODE)
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            /*
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    Manifest.permission.CAMERA)) {
                Log.d("Atencion", "Debes aceptar los permisos de la camara")
            } else {
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSIONS_REQUEST_CAMERA)
            }*/
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                PERMISSIONS_REQUEST_CAMERA)
        } else {
            startCameraActivity()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> if (grantResults.isNotEmpty() && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                startCameraActivity()
            }
        }
    }

    private fun startCameraActivity() {
        var takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicture.resolveActivity(activity!!.packageManager) != null) {
            try {
                val photoFile = createPhotoFile()
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(context!!, "com.example.sergiobelda.photoeditor", photoFile)
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePicture, CAMERA_REQUEST_CODE)
                }
            } catch (e : IOException) {
                Log.e("IOException", e.message)
            }
        }
    }

    private fun createPhotoFile(): File? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val photoFileName = "IMG_" + timestamp + "_"
        val storageDir = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(photoFileName, ".jpg", storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, image: Intent?) {
        super.onActivityResult(requestCode, resultCode, image)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                val bundle = Bundle()
                bundle.putString("image", photoUri.toString())
                findNavController(this).navigate(R.id.editorAction, bundle)
            }
            GALLERY_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                val selectedImage = image!!.data
                val bundle = Bundle()
                bundle.putString("image", selectedImage.toString())
                findNavController(this).navigate(R.id.editorAction, bundle)
            }
        }
    }
}
