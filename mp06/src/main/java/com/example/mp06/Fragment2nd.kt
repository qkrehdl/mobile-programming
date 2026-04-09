package com.example.mp06

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mp06.databinding.Fragment2ndBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2nd.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2nd : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val binding =  Fragment2ndBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment2nd.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = Fragment2nd()
    }
}