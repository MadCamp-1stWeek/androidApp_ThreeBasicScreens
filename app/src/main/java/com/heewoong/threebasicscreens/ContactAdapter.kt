package com.heewoong.threebasicscreens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext

class ContactAdapter(
    private val context: Context,
    private val contacts: List<contact>

) : RecyclerView.Adapter<ContactAdapter.ImageViewHolder>() {

     class ImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val profile = itemView.findViewById<ImageView>(R.id.image)
        val name_info = itemView.findViewById<TextView>(R.id.name)
        val memo_info = itemView.findViewById<TextView>(R.id.tel)



        fun bindView(contact: contact, context: Context) {
            val outContact = itemView.findViewById<LinearLayout>(R.id.outContact)
            outContact.setOnClickListener{
//                Toast.makeText(context, "aaa", Toast.LENGTH_LONG).show();
                val intent = Intent(context, contactDescription::class.java)
                intent.putExtra("image", contact.image.toString())
                intent.putExtra("name", contact.name)
                intent.putExtra("phone", contact.phone)
                startActivity(context,intent,null)
            }
            profile.setImageURI(contact.image)
            name_info.setText(contact.name)
            memo_info.setText(contact.phone)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contacts, parent,false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(contacts[position], context)
    }
}