package com.heewoong.threebasicscreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.OnSwipe

class GalleryActivity : AppCompatActivity() {
//    private fun setImage(getData: String, colorImage: ImageView) {
//        when (getData) {
//            "1" -> {
//                colorImage.setImageResource(R.drawable.c_1)
//            }
//            "2" -> {
//                colorImage.setImageResource(R.drawable.c_1_2)
//            }
//            "3" -> {
//                colorImage.setImageResource(R.drawable.c_2)
//            }
//            "4" -> {
//                colorImage.setImageResource(R.drawable.c_3)
//            }
//            "5" -> {
//                colorImage.setImageResource(R.drawable.c_4)
//            }
//            "6" -> {
//                colorImage.setImageResource(R.drawable.c_5)
//            }
//            "7" -> {
//                colorImage.setImageResource(R.drawable.c_5_2)
//            }
//            "8" -> {
//                colorImage.setImageResource(R.drawable.c_6)
//            }
//            "9" -> {
//                colorImage.setImageResource(R.drawable.c_7)
//            }
//            "10" -> {
//                colorImage.setImageResource(R.drawable.c_7_1)
//            }
//            "11" -> {
//                colorImage.setImageResource(R.drawable.c_7_2)
//            }
//            "12" -> {
//                colorImage.setImageResource(R.drawable.c_7_3)
//            }
//            "13" -> {
//                colorImage.setImageResource(R.drawable.c_7_4)
//            }
//            "14" -> {
//                colorImage.setImageResource(R.drawable.c_8)
//            }
//            "15" -> {
//                colorImage.setImageResource(R.drawable.c_9)
//            }
//            "16" -> {
//                colorImage.setImageResource(R.drawable.c_10)
//            }
//            "17" -> {
//                colorImage.setImageResource(R.drawable.c_11)
//            }
//            "18" -> {
//                colorImage.setImageResource(R.drawable.c_12)
//            }
//            "19" -> {
//                colorImage.setImageResource(R.drawable.c_13)
//            }
//            "20" -> {
//                colorImage.setImageResource(R.drawable.c_14)
//            }
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery1)

        var getData = intent.getIntExtra("data", 0)
        val colorImage = findViewById<ImageView>(R.id.imageArea)

        if (getData != null && getData != 0) {
            colorImage.setImageResource(getData)
        }

//        colorImage.setOnTouchListener(object: OnSwipeTouchListener(this@GalleryActivity) {
//            override fun onSwipeRight() {
//                super.onSwipeLeft()
//                if (getData != "1" && getData != null) {
//                    getData = (getData!!.toInt() - 1).toString()
//                }
//                Toast.makeText(this@GalleryActivity, "right", Toast.LENGTH_LONG).show()
//                getData?.let { setImage(it, colorImage) }
//            }
//
//            override fun onSwipeLeft() {
//                super.onSwipeRight()
//                if (getData != "20" && getData != null) {
//                    getData = (getData!!.toInt() + 1).toString()
//                }
//                getData?.let { setImage(it, colorImage) }
//            }
//
//            override fun onSwipeUp() {
//                super.onSwipeUp()
//                finish()
//            }
//
//            override fun onSwipeDown() {
//                super.onSwipeDown()
//                finish()
//            }
//        })





    }
}