package com.example.batikkita.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.text.Layout.JUSTIFICATION_MODE_NONE
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.batikkita.databinding.ActivityDetailBinding
import com.example.batikkita.ui.error.ErrorActivity
import com.example.batikkita.ui.main.MainActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getBatikDetail()

    }

    fun getBatikDetail(){
        val name = intent.getStringExtra("result")?.replace("\n","")?.replace("\r","")

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        showLoading(true)

        if (name != null) {
            viewModel.setBatikDetail(name)
        }
        else {
            val intent = Intent(this, ErrorActivity::class.java)
            startActivity(intent)
        }

        viewModel.getBatikDetail().observe(this){
            showLoading(false)
            binding.apply { ->
                namaBatik.text = it.name
                batikDescription.text = it.description
                Glide.with(this@DetailActivity)
                    .load(it.thumbnail)
                    .into(ivPhotoDetail)
                Glide.with(this@DetailActivity)
                    .load(it.similar1)
                    .into(similar1)
                Glide.with(this@DetailActivity)
                    .load(it.similar2)
                    .into(similar2)
                Glide.with(this@DetailActivity)
                    .load(it.similar3)
                    .into(similar3)
                Glide.with(this@DetailActivity)
                    .load(it.similar4)
                    .into(similar4)
                Glide.with(this@DetailActivity)
                    .load(it.similar5)
                    .into(similar5)
                Glide.with(this@DetailActivity)
                    .load(it.similar6)
                    .into(similar6)
                Glide.with(this@DetailActivity)
                    .load(it.similar7)
                    .into(similar7)
                Glide.with(this@DetailActivity)
                    .load(it.similar8)
                    .into(similar8)
                Glide.with(this@DetailActivity)
                    .load(it.similar9)
                    .into(similar9)
                Glide.with(this@DetailActivity)
                    .load(it.similar10)
                    .into(similar10)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}