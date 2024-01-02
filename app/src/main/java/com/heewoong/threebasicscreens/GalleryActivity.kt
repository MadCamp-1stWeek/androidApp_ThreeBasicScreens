package com.heewoong.threebasicscreens

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.OnSwipe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

class GalleryActivity : AppCompatActivity() {

    var isActionBarHidden = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery1)

        var contactFlag =intent.getBooleanExtra("contactFlag", false)

        val paths = intent.getStringArrayExtra("paths") ?: emptyArray()
        val names = intent.getStringArrayExtra("names") ?: emptyArray()

        var position = intent.getIntExtra("position", 0)

        val path = paths[position]
        val name = names[position]

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
                if (position < paths.size) {
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

            override fun onClick() {
                super.onClick()
                if (isActionBarHidden) {
                    supportActionBar?.show()
                } else {
                    supportActionBar?.hide()
                }
                isActionBarHidden = !isActionBarHidden

                if (contactFlag==true){
                    Toast.makeText(this@GalleryActivity, "사진이 선택되었습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent("imageSend")
                    intent.putExtra("imageUri", "file://$path")
                    intent.putExtra("fileInfo", "$path")
//                    intent.putExtra("Done", "Yes")
//                    setResult(Activity.RESULT_OK, intent)
                    sendBroadcast(intent)

                    val intent2 = Intent("statusSend")
                    intent2.putExtra("STATUS", "Selected")
                    sendBroadcast(intent2)


                    finish()
                }
                else{


                }
            }

            override fun onLongClick() {
                super.onLongClick()
                val builder = AlertDialog.Builder(this@GalleryActivity)
                builder.setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this image?")
                    .setPositiveButton("Delete") { _, _ ->
                        // User clicked the Delete button
                        if (position < paths.size) {
                            val filePath = "file://" + paths[position]

                            // Check if the file exists
                            val imageToDelete = File(paths[position])
                            if (imageToDelete.exists()) {
                                if (imageToDelete.delete()) {

                                    Toast.makeText(this@GalleryActivity, "이미지가 삭제 되었습니다.", Toast.LENGTH_SHORT).show()

                                    // Optionally, you can refresh the gallery or update the imagePaths list
                                    // and load the next image
                                    finish()
                                } else {
                                    Toast.makeText(this@GalleryActivity, "이미지를 삭제하는데 실패 했습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        })

    }

    fun loadImage(path: String?, name: String?, colorImage: ImageView) {
        supportActionBar?.setTitle(name)
        supportActionBar?.show()
        isActionBarHidden = false

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