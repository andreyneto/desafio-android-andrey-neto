package br.com.andreyneto.desafioandroid.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.andreyneto.desafioandroid.databinding.ActivityMainBinding
import br.com.andreyneto.desafioandroid.model.CharacterDataWrapper
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.RecyclerView




class MainActivity: AppCompatActivity() {

    private val adapter: CharactersAdapter by lazy {
        CharactersAdapter()
    }

    private val viewModel: CharacterViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loader.visibility = View.VISIBLE
        setupRecyclerView()
        viewModel.dispatchViewAction(CharacterViewModel.ViewAction.FetchCharacters)
        observeViewModelAction()
    }

    private fun setupRecyclerView() {
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        binding.rvCharacters.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvCharacters.layoutManager = gridLayoutManager
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = gridLayoutManager.childCount
                    totalItemCount = gridLayoutManager.itemCount
                    pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()
                    if (binding.loader.visibility == View.GONE) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            binding.loader.visibility = View.VISIBLE
                            viewModel.dispatchViewAction(CharacterViewModel.ViewAction.FetchCharacters)
                        }
                    }
                }
            }
        })
    }

    private fun observeViewModelAction() {
        viewModel.action.observe(this) { action ->
            when(action) {
                is CharacterViewModel.Action.ShowCharacters -> populate(action.characters)
            }
        }
    }

    private fun populate(characters: List<CharacterDataWrapper.Data.Result>) {
        binding.loader.visibility = View.GONE
        adapter.submitList(characters)
    }
}