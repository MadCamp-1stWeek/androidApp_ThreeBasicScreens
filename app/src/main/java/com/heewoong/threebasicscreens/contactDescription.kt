package com.heewoong.threebasicscreens

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.heewoong.threebasicscreens.ui.contact.contactViewModel

class contactDescription : AppCompatActivity() {
    var status: String? = "Not Edited"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_contact_description)



        val contactViewModel: contactViewModel by viewModels()


        val imageIn = findViewById<ImageView>(R.id.imageIn)
        val getImage =intent.getStringExtra("image")
        val imageIn_uri = Uri.parse(getImage)
        Log.d("OlD", "$imageIn_uri")
        imageIn.setImageURI(imageIn_uri)

        val nameIn = findViewById<TextView>(R.id.nameIn)
        val getName = intent.getStringExtra("name")
        nameIn.setText(getName)

        val telIn = findViewById<TextView>(R.id.telIn)
        val getTel = intent.getStringExtra("phone")
        telIn.setText(getTel)

        val delBtn = findViewById<LinearLayout>(R.id.del)
        val editBtn = findViewById<LinearLayout>(R.id.edit)
        val callBtn = findViewById<LinearLayout>(R.id.call)
        val textBtn = findViewById<LinearLayout>(R.id.text)

        delBtn.setOnClickListener{
            Toast.makeText(this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            contactViewModel.contactDelete(application,getName,getTel)
            finish()
        }

        editBtn.setOnClickListener{
            val intent = Intent(this, contactEdit::class.java)
            intent.putExtra("oldName", getName)
            intent.putExtra("oldPhone", getTel)
            Log.d("OlD", "$imageIn_uri")
            intent.putExtra("oldImage_Uri", getImage)
            startActivityForResult(intent,99)
//            if (status == "Edited") {
//                // 뒤로가기 버튼을 누른 것과 같은 행동 수행
//                onBackPressed()
//                finish()
//            }

        }

        callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            Log.d("tel", "tel$telIn")
            intent.data = Uri.parse("tel:$getTel")
            Log.d("tel", "${intent.data}")
            startActivity(intent)
        }

        textBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply{data = Uri.parse("smsto:$getTel")}
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            val status = data?.getStringExtra("STATUS")
            if (status == "Edited") {
                // 뒤로가기 버튼을 누른 것과 같은 행동 수행
                finish() // 작업이 완료되면 현재 액티비티를 종료합니다.
            }
        }
    }
}