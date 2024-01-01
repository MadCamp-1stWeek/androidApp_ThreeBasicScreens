package com.heewoong.threebasicscreens

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.heewoong.threebasicscreens.ui.contact.contactViewModel

class contactEdit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_edit)

        val contactViewModel: contactViewModel by viewModels()

        var name: String = ""
        var tel: String = ""
        val oldName = intent.getStringExtra("oldName")
        val oldTel= intent.getStringExtra("oldPhone")

        val newName = findViewById<TextView>(R.id.nameNew)
        val newTel = findViewById<TextView>(R.id.telNew)
        val editConfBtn = findViewById<Button>(R.id.editConfirm)

        editConfBtn.isEnabled = false

        fun checkFieldsForEmptyValues() {
            if (name.isNotEmpty() && tel.isNotEmpty()) {
                editConfBtn.isEnabled = true
            } else {
                editConfBtn.isEnabled = false
            }
        }

        newName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()  // 사용자가 입력한 값을 name 변수에 저장
                checkFieldsForEmptyValues()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        newTel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tel = s.toString()  // 사용자가 입력한 값을 tel 변수에 저장
                checkFieldsForEmptyValues()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })


        editConfBtn.setOnClickListener {

            contactViewModel.contactDelete(application, oldName,oldTel)
            contactViewModel.contactAdd(application, name,tel)
            Toast.makeText(this, "수정 되었습니다.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}