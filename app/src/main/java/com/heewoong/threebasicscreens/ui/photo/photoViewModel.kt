package com.heewoong.threebasicscreens.ui.photo

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heewoong.threebasicscreens.DBHelper
import com.heewoong.threebasicscreens.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class photoViewModel : ViewModel() {
    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = _images

    fun loadImages(application: Application, filter: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            val dbHelper = DBHelper(application.applicationContext)
            val database = dbHelper.writableDatabase
            val images = getAllImages(application, filter, database)
            _images.postValue(images)
        }
    }
    fun isPathInDatabase(path: String, database: SQLiteDatabase): Boolean {
        val query = "SELECT * FROM ${DBHelper.TABLE_NAME} WHERE ${DBHelper.COLUMN_PATH} = ?"
        val cursor = database.rawQuery(query, arrayOf(path))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    private fun getAllImages(application: Application, filter: Boolean, database: SQLiteDatabase): List<Image> {
        if (!filter) {
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
        else {
            val imagelist = mutableListOf<Image>()
            val imageUri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection =
                arrayOf(android.provider.MediaStore.MediaColumns.DATA, android.provider.MediaStore.Images.Media.DISPLAY_NAME)

            val cursor = application.contentResolver.query(imageUri, projection, null, null, null)
            Log.d("test", "filtering")
            try {
                cursor?.moveToFirst()
                do {
                    val currImage = Image()
                    currImage.imageSrc =
                        cursor?.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.MediaColumns.DATA))
                    if (isPathInDatabase(currImage.imageSrc!!, database)) {
                        // File exists, proceed with loading
                        Log.d("test", "in")
                        currImage.imageName =
                            cursor?.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DISPLAY_NAME))
                        imagelist.add(currImage)
                    } else {
                        Log.d("test", "not in")
                        continue
                    }

                } while (cursor?.moveToNext() == true)
                cursor?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return imagelist

        }



    }

}