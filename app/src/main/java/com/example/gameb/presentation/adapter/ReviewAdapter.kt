package com.example.gameb.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gameb.R
import com.example.gameb.domain.model.Review

typealias OnReviewClickListener = (Review) -> Unit
class ReviewAdapter(
    private val reviews: List<Review>,
    private val listener: OnReviewClickListener,
) : RecyclerView.Adapter<ReviewAdapter.ReviewsVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReviewsVH(
            layoutInflater.inflate(R.layout.item_reviews, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ReviewsVH, position: Int) = holder.bind(reviews[position])

    override fun getItemCount(): Int = reviews.size


    class ReviewsVH(
        view: View,
        listener: OnReviewClickListener
    ) : RecyclerView.ViewHolder(view) {

        private val userPhoto = view.findViewById<ImageView>(R.id.user_photo)
        private val userName = view.findViewById<TextView>(R.id.User_name)
        private val dateReview = view.findViewById<TextView>(R.id.reviews_date)
        private val commentProduct = view.findViewById<TextView>(R.id.comments_text)
        private lateinit var review: Review


        init {
            view.setOnClickListener { listener(review) }
        }

        @SuppressLint("SetTextI18n")
        fun bind(review: Review) {
            this.review = review
            userName.text = review.user.name + review.user.lastname
            dateReview.text = review.Date
            commentProduct.text = review.comment
            userPhoto.setImageResource(review.user.coverResId)
        }
    }
}