package br.com.andreyneto.desafioandroid.view

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.andreyneto.desafioandroid.R
import br.com.andreyneto.desafioandroid.databinding.ActivityCharacterBinding
import br.com.andreyneto.desafioandroid.enableToggle
import br.com.andreyneto.desafioandroid.model.CharacterDataWrapper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCharacterBinding

    private val character by lazy {
        intent.getSerializableExtra("extra") as CharacterDataWrapper.Data.Result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.shared_element_transition)
        binding.imgCharacter.transitionName = "thumbnailTransition"
        Picasso.get()
            .load(character.thumbnail.path + "/landscape_xlarge." +character.thumbnail.extension)
            .into(binding.imgCharacter)
        lblDescription.text = if(character.description.isEmpty()) "No description available" else character.description
        lblDescription.enableToggle()
        supportActionBar?.title = character.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        btnComic.setOnClickListener {
            startActivity(
                Intent(this, ComicActivity::class.java).apply {
                    putExtra("extra", character.id)
                })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}