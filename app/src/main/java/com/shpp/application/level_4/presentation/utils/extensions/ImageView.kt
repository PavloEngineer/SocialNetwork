package com.shpp.application.level_4.presentation.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.squareup.picasso.Picasso

private const val USE_GLIDE = true

fun ImageView.downloadAndPutPhoto(
    link: String? = null
) {
    if (USE_GLIDE) {
        Glide
            .with(this.context)
            .load(link)
            .circleCrop()
            .placeholder(R.drawable.ic_user_avatar)
            .error(R.drawable.ic_user_avatar)
            .into(this)
    } else {
        Picasso
            .with(this.context)
            .load(link)
            .placeholder(R.drawable.ic_user_avatar)
            .error(R.drawable.ic_user_avatar)
            .into(this)
    }
}
