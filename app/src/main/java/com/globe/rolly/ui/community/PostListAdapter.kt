package com.globe.rolly.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.globe.R
import com.globe.rolly.network.model.response_model.PostInfo
import com.globe.rolly.support.baseclass.BaseSimpleAdapter
import kotlinx.android.synthetic.main.post_list_item.view.*

class PostListAdapter : BaseSimpleAdapter<PostInfo, PostListAdapter.PostListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        return PostListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class PostListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(post: PostInfo) {
            itemView.userNickname.text = post.userNickName

            bindPostRegDate(post.postRegdate)

            Glide.with(itemView.context).load("https://test.rollyglobe.com/post/pics/small/" + post.postPicInfo[0].pictureRegDate + "/" + post.postPicInfo[0].postPicName)
                .into(itemView.postPictures)

            itemView.postContent.text = post.postContent + "컨텐츠! 나중에 지울것!"

        }

        private fun bindPostRegDate(regDate: String) {
            val refined = regDate.substring(0, 4) + "." + regDate.substring(4, 6) + "." + regDate.substring(6, 8)

            itemView.postRegDate.text = refined
        }

    }
}