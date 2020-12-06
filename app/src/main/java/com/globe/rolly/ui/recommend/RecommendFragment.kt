package com.globe.rolly.ui.recommend

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.globe.R
import com.globe.databinding.RecommendFragmentBinding
import com.globe.rolly.network.model.SpotModel
import com.globe.rolly.ui.MainViewModel
import com.globe.rolly.ui.recommend.inner_contents.InnerContentsActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecommendFragment : Fragment() {
    companion object {
        val instance = RecommendFragment()
    }

    private val mainViewModel: MainViewModel by sharedViewModel()

    private var _binding : RecommendFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = RecommendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecommendRecyclerView()

        observeEvents()

        mainViewModel.getSpotList()
    }

    private fun observeEvents() {
        mainViewModel.spotListLiveData.observe(viewLifecycleOwner, Observer {
            (binding.recommendSpotList.adapter as RecommendationAdapter).run {
                spotList = it
            }
        })
    }

    private fun initRecommendRecyclerView() {
        val listener = object : RecommendationAdapter.OnItemClickListener {
            override fun onItemClick(spot: SpotModel) {
                val intent = Intent(context, InnerContentsActivity::class.java)
                intent.putExtra("spotModel", spot)
                context?.startActivity(intent)
            }
        }

        binding.recommendSpotList.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = RecommendationAdapter(listener)
        }
    }
}
