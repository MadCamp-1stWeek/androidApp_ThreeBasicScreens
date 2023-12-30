//package com.heewoong.threebasicscreens
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import org.json.JSONArray
//import java.lang.Integer.parseInt
//
//class ContactAdapter(
//    private val context: Context,
//    private val contacts: JSONArray
//
//    ) : RecyclerView.Adapter<ContactAdapter.ImageViewHolder>() {
//
//    class ImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
//
//        val profile = itemView.findViewById<ImageView>(R.id.image)
//        val name = itemView.findViewById<TextView>(R.id.name)
//        val memo = itemView.findViewById<TextView>(R.id.memo)
//        fun bindView(contacts: JSONArray) {
//            val obj = contacts.getJSONObject(layoutPosition)
////            val image_name= obj.getString("image_info")
//            val image_name = getImageResourceId(obj.getString("image_info"), itemView.context)
//            val name_info= obj.getString("name_info")
//            val tel_info= obj.getString("tel_info")
//            val memo_info= obj.getString("memo_info")
//
//            profile.setImageResource(image_name)
//            name.setText(name_info)
//            memo.setText(memo_info)
//
//        }
//        private fun getImageResourceId(imageName: String, context: Context): Int {
//            val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
//            return resId
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
//        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_images, parent,false))
//
//    override fun getItemCount(): Int = contacts.length()
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        holder.bindView(contacts)
//    }
//}
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
    private val contacts: List<contact>
) : RecyclerView.Adapter<ContactAdapter.ImageViewHolder>() {

    class ImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val profile = itemView.findViewById<ImageView>(R.id.image)
        val name_info = itemView.findViewById<TextView>(R.id.name)
        val memo_info = itemView.findViewById<TextView>(R.id.memo)
        fun bindView(contact: contact) {
//            profile.setImageResource(contact.profile)
            name_info.setText(contact.name)
            memo_info.setText(contact.phone)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_images, parent,false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(contacts[position])
    }
}