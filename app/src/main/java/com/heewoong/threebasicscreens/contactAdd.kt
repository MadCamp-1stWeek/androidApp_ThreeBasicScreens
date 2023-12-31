package com.heewoong.threebasicscreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.heewoong.threebasicscreens.ui.contact.contactViewModel

class contactAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_add)

        val contactViewModel: contactViewModel by viewModels()

        var name: String=""
        var tel: String=""

        val nameAdd = findViewById<TextView>(R.id.nameAdd)
        val telAdd = findViewById<TextView>(R.id.telAdd)
        val addBtn = findViewById<Button>(R.id.add)

        addBtn.isEnabled= false

        fun checkFieldsForEmptyValues() {
            if (name.isNotEmpty() && tel.isNotEmpty()) {
                addBtn.isEnabled = true
            } else {
                addBtn.isEnabled = false
            }
        }

        nameAdd.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name=nameAdd.text.toString()
                checkFieldsForEmptyValues()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        telAdd.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tel=telAdd.text.toString()
                checkFieldsForEmptyValues()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })




        addBtn.setOnClickListener{
            Toast.makeText(this,"저장되었습니다.", Toast.LENGTH_LONG).show()
            contactViewModel.contactAdd(application, name, tel)
        }
    }
}
