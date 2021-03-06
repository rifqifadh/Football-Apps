package com.example.my.footballapps.view.teams.detail.player

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.ripple
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {

        cardView {
            ripple(this, 0)
            lparams(matchParent, wrapContent) {
                gravity = Gravity.CENTER
                margin = dip(4)
                radius = 10f
            }


            linearLayout {
                lparams(width = matchParent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                imageView {
                    id = R.id.player_photo

                }.lparams(width = dip(0)) {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                    textSize = 16f
                    textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                    this.gravity = Gravity.START
                }.lparams(weight = 1f, width = dip(0))

                textView {
                    id = R.id.player_position
                    textSize = 16f
                    gravity = Gravity.END
                }.lparams(weight = 1f, width = dip(0)) {
                    rightMargin = dip(5)
                }
            }
        }
    }
}