package com.heewoong.threebasicscreens

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.OnSwipe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery1)
        val paths = intent.getStringArrayExtra("paths") ?: emptyArray()
        val names = intent.getStringArrayExtra("names") ?: emptyArray()

        var position = intent.getIntExtra("position", 0)

        val path = intent.getStringExtra("path")
        val name = intent.getStringExtra("name")

        val colorImage = findViewById<ImageView>(R.id.imageArea)

        loadImage(path, name, colorImage)
//        val bm = BitmapFactory.decodeFile(path)
//        colorImage.setImageBitmap(bm)

//        if (getData != null && getData != 0) {
//            colorImage.setImageResource(getData)
//        }

        colorImage.setOnTouchListener(object: OnSwipeTouchListener(this@GalleryActivity) {
            override fun onSwipeRight() {
                super.onSwipeLeft()
                if (position > 0) {
                    position -= 1
                    loadImage(paths[position], names[position], colorImage)
                }
            }

            override fun onSwipeLeft() {
                super.onSwipeRight()
                if (position < 20) {
                    position += 1
                    loadImage(paths[position], names[position], colorImage)
                }
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
                finish()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                finish()
            }
        })

    }

    fun loadImage(path: String?, name: String?, colorImage: ImageView) {
        supportActionBar?.setTitle(name)
        val filePath = "file://" + path

        // Check if the file exists
        val file = File(path)
        if (file.exists()) {
            // File exists, proceed with loading
            Glide.with(this).load(file).error(R.drawable.c_1).into(colorImage)
            Log.e("ImageAdapter", "File found: $filePath" )
        } else {
            // Log an error if the file doesn't exist
            Log.e("ImageAdapter", "File not found: $filePath")
            // Optionally, you can load a placeholder image or handle this case differently
            Glide.with(this).load(R.drawable.c_10).into(colorImage)
        }
    }
}