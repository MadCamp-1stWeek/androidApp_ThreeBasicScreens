package com.heewoong.threebasicscreens.ui.photo

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heewoong.threebasicscreens.ImageAdapter
import com.heewoong.threebasicscreens.R
import com.heewoong.threebasicscreens.databinding.FragmentPhotoBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class photoFragment :Fragment() {
    private var imageRecycler: RecyclerView? = null
    private lateinit var galleryViewModel: photoViewModel
    private lateinit var cameraButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        imageRecycler = view.findViewById(R.id.image_recycler)

        // Storage Permission
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                101
            )
        }

        // Initialize ViewModel
        galleryViewModel = ViewModelProvider(this).get(photoViewModel::class.java)

        // Observe changes in the image list
        galleryViewModel.images.observe(viewLifecycleOwner, Observer { images ->
            imageRecycler?.layoutManager = GridLayoutManager(requireContext(), 3)
            imageRecycler?.adapter = ImageAdapter(requireContext(), images)
        })

        galleryViewModel.loadImages(requireContext().applicationContext as Application)

        cameraButton = view.findViewById(R.id.Camera)

        cameraButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA),102)
            }
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),103)
            }
            val cameraInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraInt, 102)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 102 && resultCode == RESULT_OK) {
            saveImageToStorage(data)
        }
    }

    fun saveImageToStorage(data: Intent?) {
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState == Environment.MEDIA_MOUNTED) {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            // Generate a unique file name using a timestamp
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "my_image_$timeStamp.jpg"

            val file = File(storageDirectory + "/DCIM/Camera", fileName)
            try {
                val stream: OutputStream = FileOutputStream(file)

                // Get the camera image bitmap from the intent
                val bitmap = data?.extras?.get("data") as Bitmap

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()

                // Notify the system that a new file has been created
                notifyMediaScanner(file)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun notifyMediaScanner(file: File) {
        MediaScannerConnection.scanFile(
            requireContext(),
            arrayOf(file.absolutePath),
            arrayOf("image/jpeg"),
            object : MediaScannerConnection.OnScanCompletedListener {
                override fun onScanCompleted(path: String?, uri: Uri?) {
                    Log.d("MediaScanner", "Scanned $path:")
                    Log.d("MediaScanner", "-> uri=$uri")

                    galleryViewModel.loadImages(requireContext().applicationContext as Application)
                }
            }
        )
    }

}
