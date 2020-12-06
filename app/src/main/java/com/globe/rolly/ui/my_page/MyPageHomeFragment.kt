package com.globe.rolly.ui.my_page

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globe.R
import com.globe.databinding.MyPageHomeFragmentBinding
import com.globe.rolly.ui.MainViewModel

class MyPageHomeFragment : Fragment() {

    //    val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val instance = MyPageHomeFragment()
    }

    private var _binding: MyPageHomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MyPageHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)

        addClickListener()

        initRecyclerView()

        observeEvents()
    }

    private fun addClickListener() {
        binding.logoutBtn.setOnClickListener {
            mainViewModel.logout()
        }
        binding.mypageProfileEditButton.setOnClickListener {
            val intent = Intent(activity!!, ProfileEditActivity::class.java)
            intent.putExtra(ProfileEditActivity.EDIT_NAME, mainViewModel.userName.value)
            intent.putExtra(ProfileEditActivity.EDIT_NATIONCODE, mainViewModel.userNationCode)
            intent.putExtra(ProfileEditActivity.EDIT_PHONENUMBER, mainViewModel.userPhoneNumber.value)
            intent.putExtra(ProfileEditActivity.EDIT_EMAIL, mainViewModel.userEmail.value)
            intent.putExtra(ProfileEditActivity.EDIT_BIRTHDAY, mainViewModel.userBirtday.value)
            intent.putExtra(ProfileEditActivity.EDIT_GENDER, mainViewModel.userSex.value)

            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        binding.mypageReservationList.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyPageReservationAdapter(context)
        }
    }

    override fun onResume() {
        mainViewModel.getMyPageHome()
        super.onResume()
    }

    private fun observeEvents() {
        mainViewModel.userName.observe(viewLifecycleOwner, {
            binding.nickname.text = it
        })
        mainViewModel.reservations.observe(viewLifecycleOwner, {
            (binding.mypageReservationList.adapter as MyPageReservationAdapter).run {
                addItem(it)
                notifyDataSetChanged()
            }
        })
        mainViewModel.following.observe(viewLifecycleOwner, {
            val followingString = String.format(resources.getString(R.string.following), it)
            binding.following.text = followingString
        })
        mainViewModel.follower.observe(viewLifecycleOwner, {
            val followerString = String.format(resources.getString(R.string.follower), it)
            binding.follower.text = followerString
        })
    }
}
