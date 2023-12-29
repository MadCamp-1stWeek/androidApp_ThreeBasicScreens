package com.heewoong.threebasicscreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = listOf(
            Image(R.drawable.c_1),
            Image(R.drawable.c_1_2),
            Image(R.drawable.c_2),
            Image(R.drawable.c_3),
            Image(R.drawable.c_4),
            Image(R.drawable.c_5),
            Image(R.drawable.c_5_2),
            Image(R.drawable.c_6),
            Image(R.drawable.c_7),
            Image(R.drawable.c_7_1),
            Image(R.drawable.c_7_2),
            Image(R.drawable.c_7_3),
            Image(R.drawable.c_7_4),
            Image(R.drawable.c_8),
            Image(R.drawable.c_9),
            Image(R.drawable.c_10),
            Image(R.drawable.c_11),
            Image(R.drawable.c_12),
            Image(R.drawable.c_13),
            Image(R.drawable.c_14)
            )

        val recyclerView = findViewById<RecyclerView>(R.id.image_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ImageAdapter(this, images)
    }
}