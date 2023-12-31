package com.heewoong.threebasicscreens.ui.photo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heewoong.threebasicscreens.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class photoViewModel : ViewModel() {
    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = _images

    fun loadImages(application: Application) {
        GlobalScope.launch(Dispatchers.IO) {
            val images = getAllImages(application)
            _images.postValue(images)
        }
    }

    private fun getAllImages(application: Application): List<Image> {
        val imagelist = mutableListOf<Image>()
        val imageUri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(android.provider.MediaStore.MediaColumns.DATA, android.provider.MediaStore.Images.Media.DISPLAY_NAME)

        val cursor = application.contentResolver.query(imageUri, projection, null, null, null)

        try {
            cursor?.moveToFirst()
            do {
                val currImage = Image()
                currImage.imageSrc =
                    cursor?.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.MediaColumns.DATA))
                currImage.imageName =
                    cursor?.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DISPLAY_NAME))
                imagelist.add(currImage)
            } while (cursor?.moveToNext() == true)
            cursor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return imagelist
    }
}