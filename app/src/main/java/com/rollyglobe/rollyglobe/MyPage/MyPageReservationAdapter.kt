package com.rollyglobe.rollyglobe.MyPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rollyglobe.rollyglobe.Model.response_model.ReservationModel
import com.rollyglobe.rollyglobe.R
import timber.log.Timber


class MyPageReservationAdapter (val context: Context) : RecyclerView.Adapter<MyPageReservationAdapter.ViewHolder>(){

    var reservationList = ArrayList<ReservationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_page_reservation_item, parent, false))
    }

    override fun getItemCount() = reservationList.size

    override fun onBindViewHolder(holder: MyPageReservationAdapter.ViewHolder, position: Int) {
        holder.bind(reservationList[position])
        holder.itemView.tag = reservationList[position]
    }

    fun addItem(list : ArrayList<ReservationModel>){
        reservationList.clear()
        reservationList.addAll(list)
    }


    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val thumbnail = itemView.findViewById<ImageView>(R.id.reservation_thumbnail)
        val reservationName = itemView.findViewById<TextView>(R.id.reservation_name)
        val reservationIntro = itemView.findViewById<TextView>(R.id.reservation_intro)

        fun bind(reservation : ReservationModel){
            val imageURL = "https://rollyglobe.com/_product/_thumbnail/${reservation.reservationThumbnailNum}.${reservation.reservationThumbnailType}"
            Timber.d("reservation thumbnail path $imageURL")

            Glide.with(context).load(imageURL).into(thumbnail)
            reservationName.text = reservation.reservationName
            reservationIntro.text = reservation.reservationIntro
        }
    }
}