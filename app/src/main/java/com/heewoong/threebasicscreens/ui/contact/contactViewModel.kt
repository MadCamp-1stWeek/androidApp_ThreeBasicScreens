package com.heewoong.threebasicscreens.ui.contact


import android.app.Application
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.RemoteException
import android.provider.ContactsContract
import android.util.Log
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
import java.io.InputStream


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
        val myContact = searchedList.filter{ it.phone=="010-1234-0184"}
        val otherContacts = searchedList.filter{it.phone!="010-1234-0184"}

        val searchedMyContacts = mutableListOf<contact>()
        searchedMyContacts.addAll(myContact)
        searchedMyContacts.addAll(otherContacts)

        _contactList.value = searchedMyContacts
    }

    private fun getContacts(application:Application, sort: String?, searchName: String?): List<contact> {
//        Log.d("SHOW","${ContactsContract.CommonDataKinds.Phone.PHOTO_URI}")
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
                Uri.parse("android.resource://" + application.packageName + "/" + R.drawable.profile)
            }
            val contactItem = contact(name, number, imageUri)
            contacts.add(contactItem)
        }
        cursor?.close()
        return contacts
    }
    fun contactAdd(application:Application, name:String, tel:String, image:Uri? =  null) {
//        Thread{
            val list = ArrayList<ContentProviderOperation>()
            try {
                list.add(
                    ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build()
                )
                list.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "$name") // 이름
                        .build()
                )
                list.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "$tel") // 전화번호
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) // 번호타입(Type_Mobile : 모바일)
                        .build()
                )
//                list.add(
//                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, "hong_gildong@naver.com") // 이메일
//                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK) // 이메일타입(Type_Work : 직장)
//                        .build()
//                )
                // 이미지 추가
                image?.let { uri ->
                    val inputStream: InputStream? = application.contentResolver.openInputStream(uri)
                    inputStream?.use { stream ->
                        val imageBytes: ByteArray = stream.readBytes()

                        val photoRow = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, imageBytes)
                            .build()
                        list.add(photoRow)
                    }
                }
                application.contentResolver.applyBatch(ContactsContract.AUTHORITY, list) // 주소록 추가
                list.clear() // 리스트 초기화
            } catch (e: RemoteException) {
                e.printStackTrace()
            } catch (e: OperationApplicationException) {
                e.printStackTrace()
            }
//        }.start()
    }
//
fun contactDelete(application: Application, name: String?, tel: String?) {
//    Thread {
        val cursor = application.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} = ? AND ${ContactsContract.CommonDataKinds.Phone.NUMBER} = ?",
            arrayOf(name, tel),
            null
        )
        cursor?.apply {
            val displayNameIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val contactIdIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val rawContactIdIndex = getColumnIndex(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID)

            while (moveToNext()) {
                if (displayNameIndex != -1 && numberIndex != -1 && contactIdIndex != -1 && rawContactIdIndex != -1) {
                    val contactId = getString(contactIdIndex)
                    val rawContactId = getString(rawContactIdIndex)

                    val deleteUri = Uri.withAppendedPath(
                        ContactsContract.RawContacts.CONTENT_URI,
                        rawContactId
                    )
                    application.contentResolver.delete(deleteUri, null, null)
                } else {
                    Log.e("ContactDelete", "Some columns do not exist")
                }
            }
            close()
        }
//    }.start()
}
}