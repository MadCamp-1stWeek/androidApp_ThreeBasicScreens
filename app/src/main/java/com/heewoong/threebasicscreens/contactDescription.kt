package com.heewoong.threebasicscreens

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class contactDescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_contact_description)

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
    }
}