package com.heewoong.threebasicscreens

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.heewoong.threebasicscreens.ui.contact.contactViewModel
import com.heewoong.threebasicscreens.ui.photo.photoFragment

class contactEdit : AppCompatActivity() {
    private var imageUri: String? = null
    private var fileInfo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_edit)

        val contactViewModel: contactViewModel by viewModels()


        val oldName = intent.getStringExtra("oldName")
        val oldTel= intent.getStringExtra("oldPhone")
        var name = oldName.toString()
        var tel = oldTel.toString()

        val getOldImage_Uri = intent.getStringExtra("oldImage_Uri")
        Log.d("old", "$getOldImage_Uri")

        val newName = findViewById<TextView>(R.id.nameNew)
        val newTel = findViewById<TextView>(R.id.telNew)
        val editConfBtn = findViewById<Button>(R.id.editConfirm)
        val imageBtn = findViewById<ImageView>(R.id.imageEdit)
        newName.setText(name)
        newTel.setText(tel)

        val oldImage_Uri = Uri.parse(getOldImage_Uri)
        Log.d("old", "$oldImage_Uri")
        imageBtn.setImageURI(oldImage_Uri)

        fun hideKeyboard() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        val constraintLayout = findViewById<ConstraintLayout>(R.id.contact_Edit)
        constraintLayout.setOnTouchListener { _, _ ->
            hideKeyboard()
            false // 터치 이벤트가 소비되지 않았음을 나타냄
        }

        class ImageReceiver : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "imageSend") {
                    imageUri = intent.getStringExtra("imageUri")
                    fileInfo = intent.getStringExtra("fileInfo")
//                    image = intent.getStringExtra("imageUri")
                    // 여기서 이미지Uri를 사용하여 원하는 작업을 수행합니다.
                    Log.d("ImageReceiver", "Received imageUri: $imageUri")
                    imageBtn.setImageURI(Uri.parse(imageUri))
//                    imageSet=intent.getStringExtra("Done")
//                    Log.d("YESYES", "$imageSet")
                }
            }
        }
        val imageReceiver = ImageReceiver()
        val intentFilter = IntentFilter("imageSend")
        registerReceiver(imageReceiver, intentFilter)



        newName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        newTel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tel = s.toString()  // 사용자가 입력한 값을 tel 변수에 저장
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        imageBtn.setOnClickListener{
            val contactFlag = true
            val fragment = photoFragment(contactFlag)  // 호출할 프래그먼트의 인스턴스 생성
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragment_container,
                fragment
            )  // 프래그먼트를 container에 추가 또는 교체
            fragmentTransaction.addToBackStack(null)  // 백 스택에 추가하여 이전 상태로 돌아갈 수 있도록 함
            fragmentTransaction.commit()  // 프래그먼트 트랜잭션을 커밋하여 적용
        }

        editConfBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("STATUS", "Edited")
            setResult(Activity.RESULT_OK, intent)
            if (imageUri != null) {
                contactViewModel.contactDelete(application, oldName,oldTel)
                contactViewModel.contactAdd(application, name, tel, Uri.parse(imageUri))}
            else{
                Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_LONG).show()
                contactViewModel.contactDelete(application, oldName,oldTel)
                contactViewModel.contactAdd(application, name,tel)
            }
            Toast.makeText(this, "수정 되었습니다.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}