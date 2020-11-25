package com.globe.rolly.ui.community.writepost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.globe.R
import kotlinx.android.synthetic.main.activity_write_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WritePostActivity : AppCompatActivity() {

    private val imageListAdpater = ImageListAdapter()

    private val writePostViewModel: WritePostViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        imageRecyclerView.run {
            addItemDecoration((GridSpacingItemDecoration(3, 1, true)))
            layoutManager = GridLayoutManager(context, 3)
            adapter = imageListAdpater
        }

        writePostViewModel.imageHashMapLiveData.observe(this, Observer { uris ->

            imageListAdpater.items = uris
        })

        writePostViewModel.loadImagesByUri()

    }
}