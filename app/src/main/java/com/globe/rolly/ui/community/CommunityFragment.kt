package com.globe.rolly.ui.community


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.globe.R
import kotlinx.android.synthetic.main.community_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class CommunityFragment : Fragment() {

    private val communityViewModel: CommunityViewModel by viewModel()

    private val postListAdapter = PostListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.community_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postListRecyclerView.run{
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)

            adapter = postListAdapter
        }


        communityViewModel.getPostList()



        communityViewModel.postList.observe(viewLifecycleOwner, Observer {
            postListAdapter.items = it
        })

    }

    companion object {
        val instance = CommunityFragment()
    }


}

