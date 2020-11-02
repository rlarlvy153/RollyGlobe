package com.globe.rolly.ui.community


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globe.R

/**
 * A simple [Fragment] subclass.
 */
class CommunityFragment : Fragment() {
    companion object{
        val instance = CommunityFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.community_fragment, container, false)
    }


}
