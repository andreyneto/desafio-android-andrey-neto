package br.com.andreyneto.desafioandroid.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.andreyneto.desafioandroid.databinding.ActivityComicBinding
import br.com.andreyneto.desafioandroid.enableToggle
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*

class ComicActivity: AppCompatActivity() {

    private val viewModel: ComicViewModel by viewModel()

    private lateinit var binding: ActivityComicBinding

    private val characterId by lazy {
        intent.getStringExtra("extra")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loader.visibility = View.VISIBLE
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        viewModel.dispatchViewAction(ComicViewModel.ViewAction.GetComics(characterId))
        observeViewModelAction()
    }

    private fun observeViewModelAction() {
        viewModel.action.observe(this) { action ->
            when (action) {
                is ComicViewModel.Action.ShowComic -> {
                    action.comic?.let { comic ->
                        supportActionBar?.title = comic?.title
                        val maxValue = comic.prices.maxByOrNull { it.price }
                        supportActionBar?.subtitle = if(maxValue?.type == "printPrice") "Printed" else "Digital"
                        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
                        format.currency = Currency.getInstance("USD")
                        binding.lblPrice.text = format.format(maxValue?.price)
                        binding.lblDescription.text = comic.description
                        binding.lblDescription.enableToggle()
                        Picasso.get()
                            .load(comic.thumbnail.path + "/portrait_uncanny." + comic.thumbnail.extension)
                            .into(binding.imgComic, object : Callback {
                                override fun onSuccess() {
                                    binding.loader.visibility = View.GONE
                                }

                                override fun onError(e: Exception?) {
                                    binding.loader.visibility = View.GONE
                                }
                            })
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}