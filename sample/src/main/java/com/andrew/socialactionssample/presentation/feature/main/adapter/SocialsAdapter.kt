package com.andrew.socialactionssample.presentation.feature.main.adapter

import android.view.View
import android.view.ViewGroup
import com.andrew.social.login.core.SocialType
import com.andrew.socialactionssample.R
import com.andrew.socialactionssample.presentation.model.SocialModel
import com.andrew.socialactionssample.utils.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_social.view.*

/**
 * Created by Andrew on 17.06.2018.
 */

class SocialsAdapter(
    private val socialsClick: SocialClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<SocialsAdapter.SocialViewHolder>() {

    interface SocialClickListener {
        fun loginClick(socialType: SocialType)
        fun logoutClick(socialType: SocialType)
    }

    private val socials: List<SocialModel> = arrayListOf(
        SocialModel(SocialType.VK),
        SocialModel(SocialType.TWITTER),
        SocialModel(SocialType.INSTAGRAM),
        SocialModel(SocialType.FACEBOOK),
        SocialModel(SocialType.GOOGLE),
        SocialModel(SocialType.LINKED_IN),
        SocialModel(SocialType.GITHUB),
        SocialModel(SocialType.AMAZON)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialViewHolder =
        SocialViewHolder(parent.inflate(R.layout.item_social), socialsClick)

    override fun getItemCount(): Int = socials.size

    override fun onBindViewHolder(holder: SocialViewHolder, position: Int) {
        holder.bind(socials[position])
    }

    fun updateSocial(socialType: SocialType, code: String) {
        socials.find { it.socialType == socialType }
            ?.let {
                val pos = socials.indexOf(it)
                it.code = code
                notifyItemChanged(pos)
            }
    }

    class SocialViewHolder(
        override val containerView: View,
        private val socialsClick: SocialClickListener
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(social: SocialModel) {
            with(itemView) {
                Glide.with(this)
                    .load(social.socialViewType?.drawableRes)
                    .apply(RequestOptions().circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .into(image_social)
                text_code.text = social.code
                button_login.setOnClickListener { socialsClick.loginClick(social.socialType) }
                button_logout.setOnClickListener { socialsClick.logoutClick(social.socialType) }
            }
        }
    }
}