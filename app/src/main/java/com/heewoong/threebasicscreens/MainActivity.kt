package com.heewoong.threebasicscreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contacts = listOf(
            Contact(R.drawable.c_1, "Patrick Star", "010-1212-1212", "Best Friend"),
            Contact(R.drawable.c_1_2, "Squidward", "010-2323-2323", "Mean"),
            Contact(R.drawable.c_2, "Mr.Krabs", "010-3434-3434", "Boss"),
            Contact(R.drawable.c_3, "Plankton", "010-4545-4545", "Villain"),
            Contact(R.drawable.c_4, "Sandy Cheeks", "010-5656-5656", "Friend"),
            Contact(R.drawable.c_5, "Gary", "010-6767-6767", "My everything")
            )

        val recyclerView = findViewById<RecyclerView>(R.id.contact_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ContactAdapter(this, contacts)
    }
}