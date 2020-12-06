package com.globe.rolly.ui.community


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.globe.databinding.CommunityFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class CommunityFragment : Fragment() {

    private val communityViewModel: CommunityViewModel by viewModel()

    private val postListAdapter = PostListAdapter()

    private var _binding: CommunityFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = CommunityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postListRecyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)

            adapter = postListAdapter
        }


        communityViewModel.getPostList()



        communityViewModel.postList.observe(viewLifecycleOwner, {
            postListAdapter.items = it
        })

    }

    companion object {
        val instance = CommunityFragment()
    }


}

