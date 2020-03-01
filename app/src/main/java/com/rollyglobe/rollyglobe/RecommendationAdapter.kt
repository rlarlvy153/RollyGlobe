package com.rollyglobe.rollyglobe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendationAdapter (val context: Context) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>(){

    var spotList = ArrayList<SpotModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recommendation_item, parent, false))
    }

    override fun getItemCount() = spotList.size

    override fun onBindViewHolder(holder: RecommendationAdapter.ViewHolder, position: Int) {
        holder.bind(spotList[position])
        holder.itemView.tag = spotList[position]
    }

    fun addItem(list : ArrayList<SpotModel>){
        spotList.clear()
        spotList.addAll(list)
    }


    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val spotTitle = itemView.findViewById<TextView>(R.id.spot_title)

        fun bind(spot : SpotModel){
            spotTitle.text = spot.spotTitleKor
        }
    }
}