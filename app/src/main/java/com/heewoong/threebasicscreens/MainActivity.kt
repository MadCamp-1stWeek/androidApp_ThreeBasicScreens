package com.heewoong.threebasicscreens

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Photo.PHOTO
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.net.MailTo.parse
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpCookie.parse
import java.net.URI
import java.util.logging.Level.parse
import kotlin.time.Duration.Companion.parse

class MainActivity : AppCompatActivity() {

    var searchText= ""
    var sortText= "asc"
//    var image_uri : Uri = Uri.parse("drawable://" + R.drawable.c_1)
    var image_uri : Uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.c_1);

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
        fun getContacts(sort:String, searchName:String?): MutableList<contact> {
            val contacts = mutableListOf<contact>()
            val projections = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            )
            var whenClause:String? = null
            var whereValues:Array<String>? = null
            // searchName에 값이 있을 때만 검색을 사용한다
            if(searchName?.isNotEmpty() ?: false) {
                whenClause = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} like ?"
                whereValues = arrayOf("%$searchName%")
            }
            val optionSort = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} $sort"
            val cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projections,
                whenClause,
                whereValues,
                optionSort
            )
            while (cursor?.moveToNext() ?: false) {
                val name = cursor?.getString(0)
                val number = cursor?.getString(1)
                val image = cursor?.getString(2)
                Log.d("image", "$image")
                image_uri = if (image!=null){
                    Uri.parse(image)
                } else {
//                    Uri.parse("drawable://" + R.drawable.c_1)
                    Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.c_1);
                }

//                val inputStream: InputStream? = contentResolver.openInputStream(Uri.parse(image))
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//                val contactItem = contact(id, name, number, bitmap)
//                val contactItem = contact(id, name, number, image_uri)
                val contactItem = contact(name, number, image_uri)
                contacts.add(contactItem)
            }
            return contacts
        }
        fun changeList() {
            val searchedList = getContacts(sortText, searchText)
            val recyclerView = findViewById<RecyclerView>(R.id.contact_recycler)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = ContactAdapter(this, searchedList)
        }
        fun setSearchListener() {
            val editSearch = findViewById<EditText>(R.id.editSearch)
            editSearch.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchText = s.toString()
                    changeList()
                }
            })
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
        recyclerView.adapter = ContactAdapter(this, getContacts(sortText, searchText))
        setSearchListener()
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