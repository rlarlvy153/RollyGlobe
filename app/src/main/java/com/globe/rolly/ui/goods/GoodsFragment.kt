package com.globe.rolly.ui.goods


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.R

/**
 * A simple [Fragment] subclass.
 */
class GoodsFragment : Fragment() {
    companion object{
        val instance = GoodsFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.goods_fragment, container, false)
    }


}
