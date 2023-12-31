package com.heewoong.threebasicscreens.ui.contact


import android.app.Application
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heewoong.threebasicscreens.R
import com.heewoong.threebasicscreens.contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.AndroidViewModel


class contactViewModel : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    private val _sortText = MutableLiveData<String>()
    val sortText: LiveData<String> = _sortText

    init {
        // 초기화 시에 sortText를 "asc"로 설정
        _sortText.value = "asc"
    }

    private val _contactList = MutableLiveData<List<contact>>()
    val contactList: LiveData<List<contact>> = _contactList

    fun setSearchText(text: String) {
        _searchText.value = text
    }

//    fun setSortText(text: String) {
//        _sortText.value = text
//    }

    fun updateContactList(application: Application) {
        val searchedList = getContacts(application, _sortText.value, _searchText.value)
        _contactList.value = searchedList
    }

    private fun getContacts(application:Application, sort: String?, searchName: String?): List<contact> {
        val contacts = mutableListOf<contact>()
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        )
        var whenClause: String? = null
        var whereValues: Array<String>? = null
        // searchName에 값이 있을 때만 검색을 사용한다
        if (searchName?.isNotEmpty() == true) {
            whenClause = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} like ?"
            whereValues = arrayOf("%$searchName%")
        }
        val optionSort = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} $sort"
        val cursor = application.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projections,
            whenClause,
            whereValues,
            optionSort
        )
        while (cursor?.moveToNext() == true) {
            val name = cursor.getString(0)
            val number = cursor.getString(1)
            val image = cursor.getString(2)
            val imageUri = if (image != null) {
                Uri.parse(image)
            } else {
                Uri.parse("android.resource://" + application.packageName + "/" + R.drawable.c_1)
            }
            val contactItem = contact(name, number, imageUri)
            contacts.add(contactItem)
        }
        cursor?.close()
        return contacts
    }

}