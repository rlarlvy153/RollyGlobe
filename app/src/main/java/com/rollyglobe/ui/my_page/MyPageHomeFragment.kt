package com.rollyglobe.ui.my_page

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rollyglobe.ui.MainViewModel

import com.rollyglobe.R
import kotlinx.android.synthetic.main.my_page_home_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MyPageHomeFragment : Fragment() {

    //    val mainViewModel: MainViewModel by sharedViewModel()
    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView

    companion object {
        val instance = MyPageHomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("life MyPageHomeFragment onCreateView")
        val root = inflater.inflate(R.layout.my_page_home_fragment, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)

        addClickListener()

        initRecyclerView()

        observeEvents()


    }

    private fun addClickListener() {
        logoutBtn.setOnClickListener {
            mainViewModel.logout()
        }

        mypage_profile_edit_button.setOnClickListener {
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
        mypageReservationList.run {
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
        mainViewModel.userName.observe(viewLifecycleOwner, Observer {
            nickname.text = it
        })
        mainViewModel.reservations.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as MyPageReservationAdapter).run {
                addItem(it)
                notifyDataSetChanged()
            }
        })
        mainViewModel.following.observe(viewLifecycleOwner, Observer {
            val followingString = String.format(resources.getString(R.string.following), it)
            following.text = followingString
        })
        mainViewModel.follower.observe(viewLifecycleOwner, Observer {
            val followerString = String.format(resources.getString(R.string.follower), it)
            follower.text = followerString
        })
    }
}
