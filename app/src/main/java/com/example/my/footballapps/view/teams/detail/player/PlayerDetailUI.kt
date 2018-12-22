package com.example.my.footballapps.view.teams.detail.player

import android.annotation.SuppressLint
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.myToolbar
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.nestedScrollView

class PlayerDetailUI: AnkoComponent<PlayerDetails> {

    @SuppressLint("ResourceAsColor")
    override fun createView(ui: AnkoContext<PlayerDetails>): View = with(ui) {
        coordinatorLayout {
            appBarLayout{
                collapsingToolbarLayout {
                    isTitleEnabled = false
                     fitsSystemWindows = true
                    setContentScrimColor(R.color.colorDark)

                    imageView{
                        id = R.id.player_banner
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(matchParent, dip(200)){
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                        topMargin = dimenAttr(android.R.attr.actionBarSize)
                    }

                    myToolbar().lparams(matchParent, wrapContent){
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    }
                }.lparams(matchParent, wrapContent){
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                            AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }
            }.lparams(matchParent, wrapContent)

            nestedScrollView {
                verticalLayout {
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            gravity = Gravity.CENTER
                            text = "Weight (kg)"
                        }.lparams(weight = 1f)

                        textView {
                            gravity = Gravity.CENTER
                            text = "Height (m)"
                        }.lparams(weight = 1f)
                    }.lparams(matchParent, wrapContent){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            id = R.id.player_weight
                            gravity = Gravity.CENTER
                            textSize = 26f
                            textColorResource = R.color.colorAccent
                        }.lparams {
                            weight = 1f
                        }

                        textView {
                            id = R.id.player_height
                            gravity = Gravity.CENTER
                            textSize = 26f
                            textColorResource = R.color.colorAccent
                        }.lparams {
                            weight = 1f
                        }
                    }
                    textView {
                        id = R.id.player_position
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, matchParent) {
                        margin = dip(12)
                    }
                    textView {
                        id = R.id.player_desc
                    }.lparams(matchParent, wrapContent){
                        margin = dip(20)
                    }
                }
            }.lparams(matchParent, matchParent){
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
            progressBar {
                id = R.id.player_progressBar
            }.lparams{}
        }
    }
}