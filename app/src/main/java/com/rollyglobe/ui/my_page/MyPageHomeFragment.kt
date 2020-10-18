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
        recyclerView = root.findViewById<RecyclerView>(R.id.mypage_reservation_recyclerview)

        val profileEditButton = root.findViewById<Button>(R.id.mypage_profile_edit_button)
        profileEditButton.setOnClickListener { v ->
            val intent = Intent(activity!!, ProfileEditActivity::class.java)
            intent.putExtra(ProfileEditActivity.EDIT_NAME, mainViewModel.userName.value)
            intent.putExtra(ProfileEditActivity.EDIT_NATIONCODE, mainViewModel.userNationCode)
            intent.putExtra(ProfileEditActivity.EDIT_PHONENUMBER, mainViewModel.userPhoneNumber.value)
            intent.putExtra(ProfileEditActivity.EDIT_EMAIL, mainViewModel.userEmail.value)
            intent.putExtra(ProfileEditActivity.EDIT_BIRTHDAY, mainViewModel.userBirtday.value)
            intent.putExtra(ProfileEditActivity.EDIT_GENDER, mainViewModel.userSex.value)

            startActivity(intent)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)

        recyclerView.run {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyPageReservationAdapter(context)
        }

        mainViewModel.userName.observe(activity!!, Observer {
            nickname.text = it
        })
        mainViewModel.reservations.observe(activity!!, Observer {
            (recyclerView.adapter as MyPageReservationAdapter).run {
                addItem(it)
                notifyDataSetChanged()
            }
        })
        mainViewModel.following.observe(activity!!, Observer {
            val followingString = String.format(resources.getString(R.string.following), it)
            following.setText(followingString)
        })
        mainViewModel.follower.observe(activity!!, Observer {
            val followerString = String.format(resources.getString(R.string.follower), it)
            follower.setText(followerString)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutBtn.setOnClickListener {
            Timber.d("frag viewmodel id " +mainViewModel.hashCode())

            mainViewModel.logout()
        }

    }

    override fun onResume() {
        Timber.d("life MyPageHomeFragment onResume")
        mainViewModel.getMyPageHome()
        super.onResume()
    }

}
