package com.rollyglobe.ui.recommend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rollyglobe.R
import com.rollyglobe.network.model.SpotModel
import com.rollyglobe.ui.recommend.inner_contents.InnerContentsActivity
import timber.log.Timber

class RecommendationAdapter (val context: Context) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>(){

    var spotList = ArrayList<SpotModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recommendation_item, parent, false))
    }

    override fun getItemCount() = spotList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(spotList[position])
        holder.itemView.tag = spotList[position]
    }

    fun addItem(list : ArrayList<SpotModel>){
        spotList.clear()
        spotList.addAll(list)
    }


    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){

        val spotTitle = itemView.findViewById<TextView>(R.id.spot_title)
        val spotThumbnail = itemView.findViewById<ImageView>(R.id.spot_thumbnail)
        val spotIntro = itemView.findViewById<TextView>(R.id.spot_intro)
        val spotPlace = itemView.findViewById<TextView>(R.id.spot_place)

        init{
            itemView.setOnClickListener {
                val position = adapterPosition
                val intent = Intent(this@RecommendationAdapter.context, InnerContentsActivity::class.java)
                intent.putExtra("spotModel", spotList[position])
                this@RecommendationAdapter.context.startActivity(intent)
            }
        }

        fun bind(spot : SpotModel){
            spotTitle.text = spot.spotTitleKor
            spotIntro.text = spot.spotIntro
//            spotPlace.text = "${spot.spotNationName} - ${spot.spotCityName}"

//            if(spot.spotThumbnailType !="null"){
//                val spotThumbnailPath = "$spotThumbnailHeader${spot.spotThumbnailNum}.${spot.spotThumbnailType}"

                Timber.d(spot.spotThumbnailPath)
                Glide.with(context).load("https://m.rollyglobe.com/post/pics/original/" + spot.spotThumbnailPath).into(spotThumbnail)
//            }
        }
    }
}