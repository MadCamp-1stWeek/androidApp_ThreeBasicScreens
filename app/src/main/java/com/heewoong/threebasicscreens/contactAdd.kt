package com.heewoong.threebasicscreens

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.heewoong.threebasicscreens.ui.contact.contactViewModel
import com.heewoong.threebasicscreens.ui.photo.photoFragment
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream


class contactAdd : AppCompatActivity() {
    private var imageUri: String? = null
    private var fileInfo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_add)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val contactViewModel: contactViewModel by viewModels()



//        data class con(
//            val image_info: String?,
//            val name_info: String?,
//            val tel_info: String?,
//            val memo_info: String?
//        )
//
//        val cons = mutableListOf<con>()

        var name: String = ""
        var tel: String = ""


        val nameAdd = findViewById<TextView>(R.id.nameAdd)
        val telAdd = findViewById<TextView>(R.id.telAdd)
        val addBtn = findViewById<TextView>(R.id.add)
        val imageBtn = findViewById<ImageView>(R.id.imageAdd)

        fun hideKeyboard() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        val constraintLayout = findViewById<ConstraintLayout>(R.id.contact_add)
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


        addBtn.isEnabled = false

        fun checkFieldsForEmptyValues() {
            if (name.isNotEmpty() && tel.isNotEmpty()) {
                addBtn.isEnabled = true
            } else {
                addBtn.isEnabled = false
            }
        }

        nameAdd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = nameAdd.text.toString()
                checkFieldsForEmptyValues()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        telAdd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tel = telAdd.text.toString()
                checkFieldsForEmptyValues()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


        imageBtn.setOnClickListener {
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
//            imageBtn.post {
//                if (imageSet == "Yes") {
//                    fragmentManager.beginTransaction().remove(fragment).commit()
//                }
//            }
        }
//        fun uriToFile(context: Context, uri: Uri): File {
//            val inputStream = context.contentResolver.openInputStream(uri)
//            val file = File(context.cacheDir, "temp_image.jpg")
//            val outputStream = FileOutputStream(file)
//
//            inputStream?.use { input ->
//                outputStream.use { output ->
//                    input.copyTo(output)
//                }
//            }
//
//            return file
//        }


        addBtn.setOnClickListener {

//            val resultIntent = Intent()
//            resultIntent.putExtra("result_key", 500) // 결과로 전달할 데이터 추가


            if (imageUri != null) {
                // 이미지 URI가 있을 경우에만 contactViewModel.contactAdd 함수 호출
//                Log.d("showME", "$fileInfo")
//                val imageFile = File(fileInfo)
//                Log.d("showME", "$fileInfo")
                Log.d("showME", "$imageUri")
                contactViewModel.contactAdd(application, name, tel, Uri.parse(imageUri))
//                setResult(Activity.RESULT_OK, resultIntent)
//                val imageFile = uriToFile(application, Uri.parse(imageUri))
//                fun saveImageToContact(application: Application, name: String, tel: String, imageUri: Uri) {
//
//                    Log.d("showME", "$imageUri")
//                    contactViewModel.contactAdd(application, name, tel, imageUri)
//                }
//                saveImageToContact(application, name, tel, Uri.fromFile(imageFile))
            } else {
                // 이미지 URI가 없는 경우의 처리 (예: 경고 메시지 출력)
                Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                contactViewModel.contactAdd(application, name, tel)
//                setResult(Activity.RESULT_OK, resultIntent)
            }
            Toast.makeText(this, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
            finish()


//            val json = assets.open("contacts_info.json").reader().readText()
//            val contacts = JSONObject(json).getJSONArray("contacts")
//
//            // 기존 데이터 로드
//
//            for (i in 0 until contacts.length()) {
//                val conObject = contacts.getJSONObject(i)
//                val c = con(
//                    conObject.getString("image_info"),
//                    conObject.getString("name_info"),
//                    conObject.getString("tel_info"),
//                    conObject.getString("memo_info")
//                )
//                cons.add(c)
//            }
//            Log.d("list", "$cons")
//
//            // 새 데이터 추가
//            val cc = con("d", "D", "D", "d")
//            cons.add(cc)
//
//            // 업데이트된 전체 데이터를 JSON Array로 만듦
//            val updatedArray = JSONArray()
//            cons.forEach {
//                val jsonObject = JSONObject()
//                jsonObject.put("image_info", it.image_info)
//                jsonObject.put("name_info", it.name_info)
//                jsonObject.put("tel_info", it.tel_info)
//                jsonObject.put("memo_info", it.memo_info)
//                updatedArray.put(jsonObject)
//            }
//
//            try {
//                val file = File(filesDir, "contacts_info.json")
//                val fileOutputStream = FileOutputStream(file)
//                fileOutputStream.write(updatedArray.toString().toByteArray())
//                fileOutputStream.close()
//
//                // 파일 업데이트 후 로그로 확인
//                val updatedJson = file.readText()
//                Log.d("UpdatedJson", updatedJson)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            finish()
        }

    }

}


