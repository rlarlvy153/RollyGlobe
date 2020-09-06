package com.rollyglobe.ui.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rollyglobe.R
import com.rollyglobe.network.model.SpotModel
import kotlinx.android.synthetic.main.recommendation_item.view.*
import timber.log.Timber

class RecommendationAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    var spotList = ArrayList<SpotModel>()
        set(spots) {
            field = spots

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recommendation_item, parent, false))
    }

    override fun getItemCount() = spotList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(spotList[position])
        holder.itemView.tag = spotList[position]
    }

    interface OnItemClickListener {
        fun onItemClick(spot: SpotModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {

                itemClickListener.onItemClick(spotList[adapterPosition])
            }
        }

        fun bind(spot: SpotModel) {
            itemView.spot_title.text = spot.spotTitleKor
            itemView.spot_intro.text = spot.spotIntro
//            spotPlace.text = "${spot.spotNationName} - ${spot.spotCityName}"

//            if(spot.spotThumbnailType !="null"){
//                val spotThumbnailPath = "$spotThumbnailHeader${spot.spotThumbnailNum}.${spot.spotThumbnailType}"

            Timber.d(spot.spotThumbnailPath)
            Glide.with(itemView.context).load("https://m.rollyglobe.com/post/pics/small/" + spot.spotThumbnailPath).into(itemView.spot_thumbnail)
//            }
        }
    }
}