package com.heewoong.threebasicscreens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream
import java.security.AccessController.getContext

class ContactAdapter(
    private val context: Context,
    private val contacts: List<contact>

) : RecyclerView.Adapter<ContactAdapter.ImageViewHolder>() {

     class ImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val profile = itemView.findViewById<ImageView>(R.id.image)
        val name_info = itemView.findViewById<TextView>(R.id.name)
        val memo_info = itemView.findViewById<TextView>(R.id.tel)
         val score = itemView.findViewById<TextView>(R.id.score)


        fun bindView(contact: contact, context: Context) {

            val sharedPreferences = context.getSharedPreferences("best",Context.MODE_PRIVATE)
            //Log.d("ffffff", "${sharedPreferences.getInt("best_score",0)}")


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
            if (contact.phone=="010-1234-0184"){
                score.setText(sharedPreferences.getInt("best_score", 0).toString())
            }
            else{
            }
            score.setOnClickListener {
                val sharedPreferences = context.getSharedPreferences("best_Uri", Context.MODE_PRIVATE)
                val uriString = sharedPreferences.getString("best_Uri", null)
                Log.d("HAHA", "$uriString")

                if (uriString != null) {
                    val uri = Uri.parse(uriString)
                    val filePath = uri.path // 파일의 절대 경로를 가져옵니다.

                    if (filePath != null) {
                        val bitmap = BitmapFactory.decodeFile(filePath)
                        val bytes = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                        // 비트맵을 이미지 파일로 저장하여 그 경로를 얻습니다.
                        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
                        val imageUri = Uri.parse(path)

                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "image/*"
                            putExtra(Intent.EXTRA_STREAM, imageUri)
                            putExtra("sms_body", "내용을 입력하세요") // SMS 앱이 이 메시지를 지원하는 경우에만 작동합니다.
                            data = Uri.parse("smsto:${contact.phone}")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }

                        if (sendIntent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(sendIntent)
                        } else {
                            Toast.makeText(context, "MMS 앱을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // 파일 경로가 유효하지 않을 때 처리할 내용을 여기에 작성합니다.
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contacts, parent,false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(contacts[position], context)


    }


}