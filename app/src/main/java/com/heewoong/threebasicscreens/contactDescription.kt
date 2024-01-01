package com.heewoong.threebasicscreens

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.heewoong.threebasicscreens.ui.contact.contactViewModel

class contactDescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_contact_description)

        val contactViewModel: contactViewModel by viewModels()


        val imageIn = findViewById<ImageView>(R.id.imageIn)
        val getImage =intent.getStringExtra("image")
        val imageIn_uri = Uri.parse(getImage)
        imageIn.setImageURI(imageIn_uri)

        val nameIn = findViewById<TextView>(R.id.nameIn)
        val getName = intent.getStringExtra("name")
        nameIn.setText(getName)

        val telIn = findViewById<TextView>(R.id.telIn)
        val getTel = intent.getStringExtra("phone")
        telIn.setText(getTel)

        val delBtn = findViewById<Button>(R.id.del)
        val editBtn = findViewById<Button>(R.id.edit)

        delBtn.setOnClickListener{
            Toast.makeText(this, "삭제 되었습니다.", Toast.LENGTH_LONG).show()
            contactViewModel.contactDelete(application,getName,getTel)
            finish()
        }

        editBtn.setOnClickListener{
            val intent = Intent(this, contactEdit::class.java)
            intent.putExtra("oldName", getName)
            intent.putExtra("oldPhone", getTel)
            ContextCompat.startActivity(this, intent, null)
            finish()
        }
    }
}