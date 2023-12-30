package com.heewoong.threebasicscreens

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var requestLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val name: TextView = findViewById(R.id.name)


        val status = ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS")
        if (status == PackageManager.PERMISSION_GRANTED) {
            Log.d("test", "permission granted")
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>("android.permission.READ_CONTACTS"),
                100
            )
            Log.d("test", "permission denied")
        }

        fun getContacts(): MutableList<contact> {
        val contacts = mutableListOf<contact>()
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projections,
            null,
            null,
            null
        )
        while (cursor?.moveToNext() ?: false) {
            val id = cursor?.getString(0)
            val name = cursor?.getString(1)
            val number = cursor?.getString(2)
            val contactItem = contact(id, name, number)
            contacts.add(contactItem)
        }
        return contacts
    }
//        requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            if (it.resultCode == RESULT_OK){
//                val cursor = contentResolver.query(
//                    it.data!!.data!!,
//                    arrayOf<String>(
//                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER,
//                    ),
//                    null,
//                    null,
//                    null
//                )
//                Log.d("test","cursor size : ${cursor?.count}")
//                }
//            }
//        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
//        requestLauncher.launch(intent)



//        val json =assets.open("contacts_info.json").reader().readText()
//        val contacts = JSONObject(json).getJSONArray("contacts")

//        val contacts = listOf(
//            Contact(R.drawable.c_1, "Patrick Star", "010-1212-1212", "Best Friend"),
//            Contact(R.drawable.c_1_2, "Squidward", "010-2323-2323", "Mean"),
//            Contact(R.drawable.c_2, "Mr.Krabs", "010-3434-3434", "Boss"),
//            Contact(R.drawable.c_3, "Plankton", "010-4545-4545", "Villain"),
//            Contact(R.drawable.c_4, "Sandy Cheeks", "010-5656-5656", "Friend"),
//            Contact(R.drawable.c_5, "Gary", "010-6767-6767", "My everything")
//            )

        val recyclerView = findViewById<RecyclerView>(R.id.contact_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ContactAdapter(this, getContacts())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d("test",  "permission granted")
        }
        else{
            Log.d("test", "permission denied")
        }
    }
}