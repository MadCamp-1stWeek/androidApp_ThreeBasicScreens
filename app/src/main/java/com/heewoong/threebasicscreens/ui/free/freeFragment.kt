package com.heewoong.threebasicscreens.ui.free

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.heewoong.threebasicscreens.databinding.FragmentFreeBinding


class freeFragment :Fragment()  {
    private var _binding: FragmentFreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val freeViewModel =
            ViewModelProvider(this).get(freeViewModel::class.java)

        _binding = FragmentFreeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFree
        freeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
