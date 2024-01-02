package com.heewoong.threebasicscreens

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_gallery1.imageName
import kotlinx.android.synthetic.main.activity_gallery1.imageNav
import java.io.File

class GalleryActivity : AppCompatActivity() {

    var isActionBarHidden = false
    private lateinit var dbHelper: DBHelper
    private lateinit var database: SQLiteDatabase

    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery1)

        dbHelper = DBHelper(this)
        database = dbHelper.writableDatabase

        supportActionBar?.hide()

        val contactFlag =intent.getBooleanExtra("contactFlag", false)

        val paths = intent.getStringArrayExtra("paths") ?: emptyArray()
        val names = intent.getStringArrayExtra("names") ?: emptyArray()

        var position = intent.getIntExtra("position", 0)

        var path = paths[position]
        val name = names[position]

        val colorImage = findViewById<ImageView>(R.id.imageArea)
        val imageName = findViewById<TextView>(R.id.imageName)
        val imageNav = findViewById<LinearLayout>(R.id.imageNav)

        val favoriteView = findViewById<LinearLayout>(R.id.imageFavorite)
        val favoriteViewImage = findViewById<ImageView>(R.id.imageFavoriteHeart)
        val edit = findViewById<LinearLayout>(R.id.imageEdit)
        val delete = findViewById<LinearLayout>(R.id.imageDelete)

        path = loadImage(path, name, colorImage, favoriteViewImage)

        colorImage.setOnTouchListener(object: OnSwipeTouchListener(this@GalleryActivity) {
            override fun onSwipeRight() {
                super.onSwipeLeft()
                if (position > 0) {
                    position -= 1
                    path = loadImage(paths[position], names[position], colorImage, favoriteViewImage)
                }
            }

            override fun onSwipeLeft() {
                super.onSwipeRight()
                if (position < paths.size) {
                    position += 1
                    path = loadImage(paths[position], names[position], colorImage, favoriteViewImage)
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
                if (contactFlag==true){
                    Toast.makeText(this@GalleryActivity, "사진이 선택되었습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent("imageSend")
                    intent.putExtra("imageUri", "file://$path")
                    intent.putExtra("fileInfo", path)
//                    intent.putExtra("Done", "Yes")
//                    setResult(Activity.RESULT_OK, intent)
                    sendBroadcast(intent)

                    val intent2 = Intent("statusSend")
                    intent2.putExtra("STATUS", "Selected")
                    sendBroadcast(intent2)


                    finish()
                }
                else{
                    if (isActionBarHidden) {
                        imageName.visibility = View.VISIBLE
                        imageNav.visibility = View.VISIBLE
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

                    } else {
                        imageName.visibility = View.GONE
                        imageNav.visibility = View.GONE
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

                    }
                    isActionBarHidden = !isActionBarHidden


                }
            }
        })

        delete.setOnClickListener {
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
                                database.delete(DBHelper.TABLE_NAME, "${DBHelper.COLUMN_PATH} = ?", arrayOf(paths[position]))
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

        edit.setOnClickListener {
            // Create an edit intent
            val editIntent = Intent(Intent.ACTION_EDIT)

            // Generate a content URI using FileProvider
            val contentUri = FileProvider.getUriForFile(
                this,
                "com.heewoong.threebasicscreens.fileprovider",
                File(path)
            )

            // Set the data and type for the intent (image/* MIME type)
            editIntent.setDataAndType(contentUri, "image/*")

            // Grant read URI permission
            editIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            // Start the intent with a chooser
            startActivity(Intent.createChooser(editIntent, "Open with"))
        }

        favoriteView.setOnClickListener {
            if (isPathInDatabase(path)) {
                //is favorite
                database.delete(DBHelper.TABLE_NAME, "${DBHelper.COLUMN_PATH} = ?", arrayOf(path))
                favoriteViewImage.setBackgroundResource(R.drawable.heart_empty)
            } else {
                //is not favorite
                addToFavorites(path)
                favoriteViewImage.setBackgroundResource(R.drawable.heart_full)
            }

        }

    }
    private fun isPathInDatabase(path: String): Boolean {
        val query = "SELECT * FROM ${DBHelper.TABLE_NAME} WHERE ${DBHelper.COLUMN_PATH} = ?"
        val cursor = database.rawQuery(query, arrayOf(path))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    private fun addToFavorites(path: String) {
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_PATH, path)
        }

        // Insert the new favorite into the database
        database.insert(DBHelper.TABLE_NAME, null, values)
    }

    fun loadImage(path: String?, name: String?, colorImage: ImageView, favoriteViewImage: ImageView) : (String?) {

        isActionBarHidden = false
        imageName.setText(name)
        imageName.visibility = View.VISIBLE
        imageNav.visibility = View.VISIBLE

        val filePath = "file://" + path

        // Check if the file exists
        val file = File(path)
        if (file.exists()) {
            // File exists, proceed with loading
            Glide.with(this).load(file).error(R.drawable.c_1).into(colorImage)
            if (isPathInDatabase(path!!)) {
                favoriteViewImage.setBackgroundResource(R.drawable.heart_full)
            }
            else {
                favoriteViewImage.setBackgroundResource(R.drawable.heart_empty)
            }
            Log.e("ImageAdapter", "File found: $filePath" )
        } else {
            // Log an error if the file doesn't exist
            Log.e("ImageAdapter", "File not found: $filePath")
            // Optionally, you can load a placeholder image or handle this case differently
            Glide.with(this).load(R.drawable.c_10).into(colorImage)
        }
        return path
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close() // Close the database connection when the activity is destroyed
    }
}