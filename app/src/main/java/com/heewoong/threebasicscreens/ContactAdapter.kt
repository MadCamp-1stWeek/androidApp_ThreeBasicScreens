package com.heewoong.threebasicscreens

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val context: Context,
    private val contacts: List<Contact>
    ) : RecyclerView.Adapter<ContactAdapter.ImageViewHolder>() {

    class ImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val profile = itemView.findViewById<ImageView>(R.id.image)
        val name_info = itemView.findViewById<TextView>(R.id.name)
        val memo_info = itemView.findViewById<TextView>(R.id.memo)
        fun bindView(contact: Contact) {
            profile.setImageResource(contact.profile)
            name_info.setText(contact.name)
            memo_info.setText(contact.memo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_images, parent,false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(contacts[position])
    }
}