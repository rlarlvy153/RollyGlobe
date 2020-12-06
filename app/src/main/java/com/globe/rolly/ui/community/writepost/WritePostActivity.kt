package com.globe.rolly.ui.community.writepost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.globe.databinding.ActivityWritePostBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WritePostActivity : AppCompatActivity() {

    private val imageListAdpater = ImageListAdapter()

    private val writePostViewModel: WritePostViewModel by viewModel()

    private lateinit var binding: ActivityWritePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageRecyclerView.run {
            addItemDecoration((GridSpacingItemDecoration(3, 1, true)))
            layoutManager = GridLayoutManager(context, 3)
            adapter = imageListAdpater
        }

        writePostViewModel.imageHashMapLiveData.observe(this, { uris ->

            imageListAdpater.items = uris
        })

        writePostViewModel.loadImagesByUri()

    }
}