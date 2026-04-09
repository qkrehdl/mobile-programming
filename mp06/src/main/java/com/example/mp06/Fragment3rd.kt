package com.example.mp06

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mp06.databinding.Fragment3rdBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3rd.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3rd : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val binding =  Fragment3rdBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Fragment3rd.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = Fragment3rd()
    }
}