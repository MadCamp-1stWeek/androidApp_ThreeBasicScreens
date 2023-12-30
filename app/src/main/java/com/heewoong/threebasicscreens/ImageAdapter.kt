package com.heewoong.threebasicscreens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(
    private var context: Context,
    private var images: ArrayList<Image>
    ) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(View : View) : RecyclerView.ViewHolder(View) {

        var img : ImageView?=null

        init {
            img = View?.findViewById<ImageView>(R.id.image)
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
        ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent,false))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        image.imageSrc?.let { Log.d("test", it) }
        Glide.with(context).load(image.imageSrc).error(R.drawable.c_1).into(holder.img!!)
        holder.img?.setOnClickListener {
            val intent= Intent(context, GalleryActivity::class.java)
            intent.putExtra("path", image.imageSrc)
            intent.putExtra("name", image.imageName)
            context.startActivity(intent)
        }

    }
}