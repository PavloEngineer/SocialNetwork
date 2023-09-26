package com.shpp.application.level_2.presentation.utils.extensions

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
                .load(link ?: R.drawable.ic_user_avatar)
                .circleCrop()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .into(this)
    } else {
        link?.let {
            Picasso
                    .with(this.context)
                    .load(it)
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(this)
        } ?: Picasso
                .with(this.context)
                .load(R.drawable.ic_user_avatar)
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .into(this)
    }
}
