package com.rollyglobe.rollyglobe


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment() {

    lateinit var viewModel : MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_my_page, container, false)
        viewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)
        viewModel.getMyPageHome()
        return root
    }


}
