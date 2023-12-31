package com.heewoong.threebasicscreens

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Gallery_MainActivity : AppCompatActivity() {

    private var imageRecycler: RecyclerView?=null
    private var images: ArrayList<Image>?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_photo)

//        val images = listOf(
//            Image(R.drawable.c_1),
//            Image(R.drawable.c_1_2),
//            Image(R.drawable.c_2),
//            Image(R.drawable.c_3),
//            Image(R.drawable.c_4),
//            Image(R.drawable.c_5),
//            Image(R.drawable.c_5_2),
//            Image(R.drawable.c_6),
//            Image(R.drawable.c_7),
//            Image(R.drawable.c_7_1),
//            Image(R.drawable.c_7_2),
//            Image(R.drawable.c_7_3),
//            Image(R.drawable.c_7_4),
//            Image(R.drawable.c_8),
//            Image(R.drawable.c_9),
//            Image(R.drawable.c_10),
//            Image(R.drawable.c_11),
//            Image(R.drawable.c_12),
//            Image(R.drawable.c_13),
//            Image(R.drawable.c_14)
//        )

        imageRecycler = findViewById<RecyclerView>(R.id.image_recycler)

        //Storage Permission
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),101)
        }

        images = ArrayList()

        if(images!!.isEmpty()) {
            images = getAllImages()
            imageRecycler?.layoutManager = GridLayoutManager(this, 3)
            imageRecycler?.adapter = ImageAdapter(this, images!!)
        }

    }

    private fun getAllImages(): ArrayList<Image> {
        val imagelist = ArrayList<Image>()
        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor = this@Gallery_MainActivity.contentResolver.query(imageUri, projection, null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val currImage = Image()
                currImage.imageSrc = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
                currImage.imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                imagelist.add(currImage)
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return imagelist
    }
}