package com.heewoong.threebasicscreens.ui.contact

import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
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
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heewoong.threebasicscreens.ContactAdapter
import com.heewoong.threebasicscreens.R
import kotlinx.coroutines.launch


class contactFragment : Fragment() {

    private lateinit var viewModel: contactViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var editSearch: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        checkAndRequestPermissions()
        val root = inflater.inflate(R.layout.fragment_contact, container, false)

        viewModel = ViewModelProvider(this).get(contactViewModel::class.java)

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

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.READ_CONTACTS),
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
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("test", "Permission granted")
                    // 권한이 허용된 경우에 할 일을 처리하세요
                    // 예를 들어, 연락처 가져오기 등의 작업을 진행할 수 있습니다.
                } else {
                    Log.d("test", "Permission denied")
                    // 권한이 거부된 경우 사용자에게 메시지를 표시하거나 다른 처리를 수행하세요
                }
            }
        }
    }
}
