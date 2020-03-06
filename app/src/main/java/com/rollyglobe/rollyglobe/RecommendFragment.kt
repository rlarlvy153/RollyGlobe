package com.rollyglobe.rollyglobe


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 */
class RecommendFragment : Fragment() {

    lateinit var viewModel : MainViewModel
    lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_recommend, container, false)
        recyclerView = root.findViewById(R.id.spot_list)
        recyclerView.run{
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = RecommendationAdapter(context)
        }
        viewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)

        viewModel.spotListLiveData.observe(activity!!, Observer{
            (recyclerView.adapter as RecommendationAdapter).run{
                addItem(it)
                notifyDataSetChanged()
            }
        })

        viewModel.getSpotList()

        return root
    }


}
