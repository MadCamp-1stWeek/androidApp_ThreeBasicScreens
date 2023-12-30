package com.heewoong.threebasicscreens

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(
    private val context: Context,
    private val images: List<Image>
    ) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(View : View) : RecyclerView.ViewHolder(View) {

        val img = itemView.findViewById<ImageView>(R.id.image)
        fun bindView(context: Context, image: Image) {
            img.setImageResource(image.imageSrc)
            img.setOnClickListener{
                val intent= Intent(context, GalleryActivity::class.java)
                intent.putExtra("data", image.imageSrc)
                startActivity(context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_images, parent,false))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(context, images[position])
    }
}