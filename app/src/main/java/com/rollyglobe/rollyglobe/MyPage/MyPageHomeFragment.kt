package com.rollyglobe.rollyglobe.MyPage

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
import com.rollyglobe.rollyglobe.MainViewModel

import com.rollyglobe.rollyglobe.R
import kotlinx.android.synthetic.main.my_page_home_fragment.*
import timber.log.Timber

class MyPageHomeFragment : Fragment() {

    lateinit var viewModel : MainViewModel
    lateinit var recyclerView : RecyclerView
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
        val logoutButton = root.findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener { v ->
            //viewModel.logout()
        }
        val profileEditButton = root.findViewById<Button>(R.id.mypage_profile_edit_button)
        profileEditButton.setOnClickListener { v->
            val intent = Intent(activity!!,ProfileEditActivity::class.java)
            intent.putExtra(ProfileEditActivity.EDIT_NAME, viewModel.userName.value)
            intent.putExtra(ProfileEditActivity.EDIT_PHONENUMBER, viewModel.userPhoneNumber.value)
            intent.putExtra(ProfileEditActivity.EDIT_EMAIL,viewModel.userEmail.value)
            intent.putExtra(ProfileEditActivity.EDIT_BIRTHDAY, viewModel.userBirtday.value)
            intent.putExtra(ProfileEditActivity.EDIT_GENDER, viewModel.userSex.value)

            startActivity(intent)
         }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)
        recyclerView.run{
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
            adapter = MyPageReservationAdapter(context)
        }

        viewModel.userName.observe(activity!!, Observer {
            nickname.text = it
        })
        viewModel.reservations.observe(activity!!, Observer {
            (recyclerView.adapter as MyPageReservationAdapter).run {
                addItem(it)
                notifyDataSetChanged()
            }
        })
    }

    override fun onResume() {
        Timber.d("life MyPageHomeFragment onResume")
        viewModel.getMyPageHome()
        super.onResume()
    }

    override fun onPause() {
        Timber.d("life MyPageHomeFragment onPause")

        super.onPause()
    }

}
