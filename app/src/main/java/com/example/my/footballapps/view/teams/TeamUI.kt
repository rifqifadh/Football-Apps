package com.example.my.footballapps.view.teams

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.ripple
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class TeamUI: AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>)= with(ui) {
        cardView {
            ripple(this, 0)
            lparams(matchParent, wrapContent) {
                gravity = Gravity.CENTER
                margin = dip(4)
                radius = 10f
            }

            linearLayout {
                backgroundColor = Color.WHITE
                orientation = LinearLayout.HORIZONTAL
                padding = dip(16)

                imageView {
                    id = R.id.team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 15f
                }.lparams{
                    margin = dip(15)
                }
            }
        }
    }
}