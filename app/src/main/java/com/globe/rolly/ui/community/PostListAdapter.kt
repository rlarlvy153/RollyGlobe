package com.globe.rolly.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.globe.databinding.PostListItemBinding
import com.globe.rolly.network.model.response_model.Post
import com.globe.rolly.support.baseclass.BaseSimpleAdapter
import timber.log.Timber

class PostListAdapter : BaseSimpleAdapter<Post, PostListAdapter.PostListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class PostListViewHolder(private val itemBinding: PostListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(post: Post) {
            itemBinding.userNickname.text = post.userInfo.userNickname

            bindPostRegDate(post.postInfo.postRegdate)

            Glide.with(itemView.context)
                .load("https://test.rollyglobe.com/post/pics/small/" + post.postPicInfo[0].pictureRegDate + "/" + post.postPicInfo[0].postPicName)
                .into(itemBinding.postPictures)

            itemBinding.postContent.text = post.postInfo.postContent + "임시 콘텐츠"

            itemBinding.spotPosition.text = "${post.continent} - ${post.nation} - ${post.city}"

            itemBinding.spotName.text = post.spotTitleKor

            itemBinding.spotIntro.text = post.spotIntro

            itemBinding.likeCnt.text = "좋아요 77개"
            itemBinding.commentCnt.text = "댓글 32개"
        }

        private fun bindPostRegDate(regDate: String?) {
            if(regDate == null || regDate.isEmpty()){

                return
            }

            val refined = regDate.substring(0, 4) + "." + regDate.substring(4, 6) + "." + regDate.substring(6, 8)

            itemBinding.postRegDate.text = refined
        }

    }
}