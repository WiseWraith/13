package com.example.gameb.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gameb.R
import com.example.gameb.domain.model.Game

typealias OnGameClickListener = (Game) -> Unit

class GameAdapter (
    private val games : List<Game>,
    private val listener : OnGameClickListener,
) : RecyclerView.Adapter<GameAdapter.GamesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GamesVH(
            layoutInflater.inflate(R.layout.item_game, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: GamesVH, position: Int) =
        holder.bind(games[position])

    override fun getItemCount(): Int = games.size


    class GamesVH(
        view: View,
        listener: OnGameClickListener
    ) : RecyclerView.ViewHolder(view) {

        private val imageProduct = view.findViewById<ImageView>(R.id.imageProduct)
        private val nameProduct = view.findViewById<TextView>(R.id.nameProduct)
        private val descProduct = view.findViewById<TextView>(R.id.descProduct)
        private lateinit var game: Game

        init {
            view.setOnClickListener { listener(game) }
        }

        @SuppressLint("SetTextI18n")
        fun bind(game: Game) {
            this.game = game
            nameProduct.text = game.model + game.genre
            descProduct.text = game.description
            Glide
                .with(itemView)
                .load(game.iconUrl)
                .centerCrop()
                .placeholder(R.drawable.image_icon)
                .into(imageProduct)
        }
    }
}