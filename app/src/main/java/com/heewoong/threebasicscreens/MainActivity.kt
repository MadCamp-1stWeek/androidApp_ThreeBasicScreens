package com.heewoong.threebasicscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image1 = findViewById<ImageView>(R.id.c_image_1)
        val image2 = findViewById<ImageView>(R.id.c_image_1_2)
        val image3 = findViewById<ImageView>(R.id.c_image_2)
        val image4 = findViewById<ImageView>(R.id.c_image_3)
        val image5 = findViewById<ImageView>(R.id.c_image_4)
        val image6 = findViewById<ImageView>(R.id.c_image_5)
        val image7 = findViewById<ImageView>(R.id.c_image_5_2)
        val image8 = findViewById<ImageView>(R.id.c_image_6)
        val image9 = findViewById<ImageView>(R.id.c_image_7)
        val image10 = findViewById<ImageView>(R.id.c_image_7_1)
        val image11 = findViewById<ImageView>(R.id.c_image_7_2)
        val image12 = findViewById<ImageView>(R.id.c_image_7_3)
        val image13 = findViewById<ImageView>(R.id.c_image_7_4)
        val image14 = findViewById<ImageView>(R.id.c_image_8)
        val image15 = findViewById<ImageView>(R.id.c_image_9)
        val image16 = findViewById<ImageView>(R.id.c_image_10)
        val image17 = findViewById<ImageView>(R.id.c_image_11)
        val image18 = findViewById<ImageView>(R.id.c_image_12)
        val image19 = findViewById<ImageView>(R.id.c_image_13)
        val image20 = findViewById<ImageView>(R.id.c_image_14)

        image1.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "1")
            startActivity(intent)
        }

        image2.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "2")
            startActivity(intent)
        }

        image3.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "3")
            startActivity(intent)
        }

        image4.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "4")
            startActivity(intent)
        }

        image5.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "5")
            startActivity(intent)
        }

        image6.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "6")
            startActivity(intent)
        }

        image7.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "7")
            startActivity(intent)
        }

        image8.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "8")
            startActivity(intent)
        }

        image9.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "9")
            startActivity(intent)
        }

        image10.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "10")
            startActivity(intent)
        }

        image11.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "11")
            startActivity(intent)
        }

        image12.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "12")
            startActivity(intent)
        }

        image13.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "13")
            startActivity(intent)
        }

        image14.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "14")
            startActivity(intent)
        }

        image15.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "15")
            startActivity(intent)
        }

        image16.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "16")
            startActivity(intent)
        }

        image17.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "17")
            startActivity(intent)
        }

        image18.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "18")
            startActivity(intent)
        }

        image19.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "19")
            startActivity(intent)
        }

        image20.setOnClickListener{
            val intent= Intent(this, GalleryActivity::class.java)
            intent.putExtra("data", "20")
            startActivity(intent)
        }
    }
}