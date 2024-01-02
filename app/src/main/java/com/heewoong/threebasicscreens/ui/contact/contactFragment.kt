package com.heewoong.threebasicscreens.ui.contact

import android.app.Activity
import android.app.Application
import android.content.ContentProviderOperation
import android.content.Context
import android.content.Intent
import android.content.OperationApplicationException
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.os.Bundle
import android.os.RemoteException
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.heewoong.threebasicscreens.databinding.FragmentContactBinding
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.heewoong.threebasicscreens.ContactAdapter
import com.heewoong.threebasicscreens.R
import com.heewoong.threebasicscreens.contactAdd
import com.heewoong.threebasicscreens.contactDescription
import kotlinx.coroutines.launch


class contactFragment : Fragment() {

    private lateinit var viewModel: contactViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var editSearch: EditText

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("RequestCoode", "$requestCode")
//        if (requestCode == 500 && resultCode == Activity.RESULT_OK) {
//            // 데이터를 추출하고 변경된 연락처 목록을 다시 로드
//            viewModel.updateContactList(requireContext().applicationContext as Application)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        checkAndRequestPermissions()
        val root = inflater.inflate(R.layout.fragment_contact, container, false)
        val fab: FloatingActionButton? = root.findViewById(R.id.fab)


        viewModel = ViewModelProvider(this).get(contactViewModel::class.java)

         fun hideKeyboard() {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }

        val constraintLayout = root.findViewById<ConstraintLayout>(R.id.contact_fragment)
        constraintLayout.setOnTouchListener { _, _ ->
            hideKeyboard()
            false // 터치 이벤트가 소비되지 않았음을 나타냄
        }



        fab?.setOnClickListener {
            val intent = Intent(requireContext(), contactAdd::class.java)
//            requireActivity().startActivityForResult(intent, 500)
            requireActivity().startActivity(intent)
        }

        recyclerView = root.findViewById(R.id.contact_recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        editSearch = root.findViewById(R.id.editSearch)
        editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchText(s.toString())
                viewModel.updateContactList(requireContext().applicationContext as Application)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        viewModel.contactList.observe(viewLifecycleOwner) { contacts ->
            recyclerView.adapter = ContactAdapter(requireContext(), contacts)
        }

        viewModel.updateContactList(requireContext().applicationContext as Application)

        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateContactList(requireContext().applicationContext as Application)
    }



    private fun checkAndRequestPermissions() {
        val readContactsPermission = android.Manifest.permission.READ_CONTACTS
        val writeContactsPermission = android.Manifest.permission.WRITE_CONTACTS

        val readPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            readContactsPermission
        ) == PackageManager.PERMISSION_GRANTED

        val writePermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            writeContactsPermission
        ) == PackageManager.PERMISSION_GRANTED

        val permissionsToRequest = mutableListOf<String>()
        if (!readPermissionGranted) {
            permissionsToRequest.add(readContactsPermission)
        }
        if (!writePermissionGranted) {
            permissionsToRequest.add(writeContactsPermission)
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissions(
                permissionsToRequest.toTypedArray(),
                100
            )
        } else {
            // 이미 권한이 허용된 경우의 처리
            Log.d("test", "Permission already granted")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                val grantedPermissions = mutableListOf<String>()
                val deniedPermissions = mutableListOf<String>()
                grantResults.forEachIndexed { index, result ->
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        grantedPermissions.add(permissions[index])
                    } else {
                        deniedPermissions.add(permissions[index])
                    }
                }
                if (grantedPermissions.isNotEmpty()) {
                    // 필요한 작업을 처리하세요 (예: 연락처 추가 등)
                }
                if (deniedPermissions.isNotEmpty()) {
                    // 권한이 거부된 경우 사용자에게 메시지를 표시하거나 다른 처리를 수행하세요
                }
            }
        }
    }

//    private fun checkAndRequestPermissions() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.READ_CONTACTS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(
//                arrayOf(android.Manifest.permission.READ_CONTACTS),
//                100
//            )
//        } else {
//            // 이미 권한이 허용된 경우의 처리
//            Log.d("test", "Permission already granted")
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            100 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("test", "Permission granted")
//                    // 권한이 허용된 경우에 할 일을 처리하세요
//                    // 예를 들어, 연락처 가져오기 등의 작업을 진행할 수 있습니다.
//                } else {
//                    Log.d("test", "Permission denied")
//                    // 권한이 거부된 경우 사용자에게 메시지를 표시하거나 다른 처리를 수행하세요
//                }
//            }
//        }
//    }

}
