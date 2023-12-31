package com.heewoong.threebasicscreens.ui.photo

import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class photoFragment :Fragment() {
    private var imageRecycler: RecyclerView? = null
    private lateinit var galleryViewModel: photoViewModel

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

        // Retrieve images only if the list is empty
        if (galleryViewModel.images.value.isNullOrEmpty()) {
            galleryViewModel.loadImages(requireContext().applicationContext as Application)
        }

        return view
    }
}
