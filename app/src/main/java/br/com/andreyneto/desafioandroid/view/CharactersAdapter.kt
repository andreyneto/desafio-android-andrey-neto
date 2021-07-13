package br.com.andreyneto.desafioandroid.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.andreyneto.desafioandroid.R
import br.com.andreyneto.desafioandroid.model.CharacterDataWrapper.Data.Result
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*
import okhttp3.OkHttpClient
import android.app.Activity

import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair


class CharactersAdapter: ListAdapter<Result, CharactersAdapter.ViewHolder>(DiffCallBack) {

    object DiffCallBack: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
    }

    class ViewHolder(val v: View): RecyclerView.ViewHolder(v.rootView) {
        fun bind(character: Result) = with(v) {
            lblName.text = character.name
            loader.visibility = View.VISIBLE
            Picasso.get()
                .load(character.thumbnail.path + "/landscape_xlarge." +character.thumbnail.extension)
                .into(imgCharacter, object : Callback {
                    override fun onSuccess() {
                        loader.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        loader.visibility = View.GONE
                    }
                })
            setOnClickListener {
                val intent = Intent(context, CharacterActivity::class.java).apply {
                    putExtra("extra", character)
                }
                imgCharacter.transitionName = "thumbnailTransition"
                val p1 = Pair.create(imgCharacter as View, imgCharacter.transitionName)
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p1)
                context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}