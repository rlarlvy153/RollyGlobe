package com.rollyglobe.ui.recommend

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rollyglobe.R
import com.rollyglobe.network.model.SpotModel
import com.rollyglobe.ui.MainViewModel
import com.rollyglobe.ui.recommend.inner_contents.InnerContentsActivity
import com.rollyglobe.ui.signin.SignInActivity
import kotlinx.android.synthetic.main.recommend_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecommendFragment : Fragment() {
    companion object {
        val instance = RecommendFragment()
    }

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recommend_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecommendRecyclerView()

        observeEvents()

        mainViewModel.getSpotList()
    }

    private fun observeEvents() {
        mainViewModel.spotListLiveData.observe(viewLifecycleOwner, Observer {
            (recommendSpotList.adapter as RecommendationAdapter).run {
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

        recommendSpotList.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = RecommendationAdapter(listener)
        }
    }
}
