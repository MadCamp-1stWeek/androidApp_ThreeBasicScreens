package com.heewoong.threebasicscreens

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private var imageRecycler: RecyclerView?=null
    private var images: ArrayList<Image>?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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




//        image1.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "1")
//            startActivity(intent)
//        }
//
//        image2.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "2")
//            startActivity(intent)
//        }
//
//        image3.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "3")
//            startActivity(intent)
//        }
//
//        image4.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "4")
//            startActivity(intent)
//        }
//
//        image5.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "5")
//            startActivity(intent)
//        }
//
//        image6.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "6")
//            startActivity(intent)
//        }
//
//        image7.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "7")
//            startActivity(intent)
//        }
//
//        image8.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "8")
//            startActivity(intent)
//        }
//
//        image9.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "9")
//            startActivity(intent)
//        }
//
//        image10.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "10")
//            startActivity(intent)
//        }
//
//        image11.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "11")
//            startActivity(intent)
//        }
//
//        image12.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "12")
//            startActivity(intent)
//        }
//
//        image13.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "13")
//            startActivity(intent)
//        }
//
//        image14.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "14")
//            startActivity(intent)
//        }
//
//        image15.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "15")
//            startActivity(intent)
//        }
//
//        image16.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "16")
//            startActivity(intent)
//        }
//
//        image17.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "17")
//            startActivity(intent)
//        }
//
//        image18.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "18")
//            startActivity(intent)
//        }
//
//        image19.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "19")
//            startActivity(intent)
//        }
//
//        image20.setOnClickListener{
//            val intent= Intent(this, GalleryActivity::class.java)
//            intent.putExtra("data", "20")
//            startActivity(intent)
//        }
    }

    private fun getAllImages(): ArrayList<Image> {
        val imagelist = ArrayList<Image>()
        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor = this@MainActivity.contentResolver.query(imageUri, projection, null, null, null)

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