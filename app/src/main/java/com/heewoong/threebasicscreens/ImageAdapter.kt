package com.heewoong.threebasicscreens

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File

class ImageAdapter(
    private var context: Context,
    private var images: List<Image>, contactFlag:Boolean
    ) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var contactFlag = contactFlag
    private var dbHelper: DBHelper = DBHelper(context)
    private var database: SQLiteDatabase = dbHelper.writableDatabase


    class ImageViewHolder(View: View) : RecyclerView.ViewHolder(View) {

        var img: ImageView? = null

        init {
            img = View.findViewById<ImageView>(R.id.image)
        }

//        fun bindView(context: Context, image: Image) {
//            img.setImageResource(image.imageSrc)
//            img.setOnClickListener{
//                val intent= Intent(context, GalleryActivity::class.java)
//                intent.putExtra("data", image.imageSrc)
//                startActivity(context, intent, null)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
        )

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val image = images[position]
        val filePath = "file://" + image.imageSrc

        // Check if the file exists
        val file = File(image.imageSrc)

            if (file.exists()) {
                // File exists, proceed with loading
                Glide.with(context).load(file).error(R.drawable.c_1).into(holder.img!!)
                Log.e("ImageAdapter", "File found: $filePath")
            } else {
                // Log an error if the file doesn't exist
                Log.e("ImageAdapter", "File not found: $filePath")
                // Optionally, you can load a placeholder image or handle this case differently
                Glide.with(context).load(R.drawable.c_10).into(holder.img!!)
            }
            holder.img?.setOnClickListener {
                val intent = Intent(context, GalleryActivity::class.java)
//            if (context is Activity) {
//                (context as Activity).startActivityForResult(intent, 300)
//            }
                intent.putExtra("contactFlag", contactFlag)
                intent.putExtra("position", position)
                intent.putExtra("paths", images.map { it.imageSrc }.toTypedArray())
                intent.putExtra("names", images.map { it.imageName }.toTypedArray())
                context.startActivity(intent)

        }

    }

    private fun isPathInDatabase(path: String): Boolean {
        val query = "SELECT * FROM ${DBHelper.TABLE_NAME} WHERE ${DBHelper.COLUMN_PATH} = ?"
        val cursor = database.rawQuery(query, arrayOf(path))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}