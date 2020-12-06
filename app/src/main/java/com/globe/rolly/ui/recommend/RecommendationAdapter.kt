package com.globe.rolly.ui.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.globe.databinding.RecommendationItemBinding
import com.globe.rolly.network.model.SpotModel
import timber.log.Timber

class RecommendationAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    var spotList = ArrayList<SpotModel>()
        set(spots) {
            field = spots

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecommendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = spotList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(spotList[position])
        holder.itemView.tag = spotList[position]
    }

    interface OnItemClickListener {
        fun onItemClick(spot: SpotModel)
    }

    inner class ViewHolder(private val itemBinding: RecommendationItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemView.setOnClickListener {

                itemClickListener.onItemClick(spotList[adapterPosition])
            }
        }

        fun bind(spot: SpotModel) {
            itemBinding.spotTitle.text = spot.spotTitleKor
            itemBinding.spotIntro.text = spot.spotIntro
//            spotPlace.text = "${spot.spotNationName} - ${spot.spotCityName}"

//            if(spot.spotThumbnailType !="null"){
//                val spotThumbnailPath = "$spotThumbnailHeader${spot.spotThumbnailNum}.${spot.spotThumbnailType}"

            Timber.d(spot.spotThumbnailPath)
            Glide.with(itemView.context).load("https://m.rollyglobe.com/post/pics/small/" + spot.spotThumbnailPath).into(itemBinding.spotThumbnail)
//            }
        }
    }
}