package com.globe.rolly.ui.community.writepost

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.globe.R
import com.globe.rolly.support.baseclass.BaseSimpleAdapter
import kotlinx.android.synthetic.main.image_list_item.view.*

class ImageListAdapter : BaseSimpleAdapter<String, ImageListAdapter.ImageListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val holder = ImageListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false))

        val metrics = DisplayMetrics()
        (holder.itemView.context as Activity).windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels

        holder.itemView.layoutParams.width = width / 3 - 2
        holder.itemView.layoutParams.height = width / 3 - 4
        holder.itemView.requestLayout()

        return holder
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {

        holder.bind(items[position])
    }

    inner class ImageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imgUri: String) {
            Glide.with(itemView.context).load(imgUri).into(itemView.img)
        }

    }
}