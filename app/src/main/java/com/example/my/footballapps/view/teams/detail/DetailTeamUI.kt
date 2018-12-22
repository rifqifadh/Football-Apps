package com.example.my.footballapps.view.teams.detail

import android.graphics.Color
import android.support.design.widget.TabLayout
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.example.my.footballapps.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.design.themedTabLayout
import org.jetbrains.anko.support.v4.viewPager

class DetailTeamUI : AnkoComponent<DetailTeamActivity> {
    override fun createView(ui: AnkoContext<DetailTeamActivity>): View = with(ui) {
        linearLayout {
            id = R.id.detail_team_UI
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(10)
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    imageView {
                        id = R.id.team_badge_detail
                    }.lparams(height = dip(75))

                    textView {
                        id = R.id.team_name_detail
                        this.gravity = Gravity.CENTER
                        textSize = 20f
                        textColor = Color.WHITE
                    }.lparams {
                        topMargin = dip(5)
                    }

                    textView {
                        id = R.id.team_formedyear_detail
                        textColor = Color.WHITE
                        this.gravity = Gravity.CENTER
                    }

                    textView {
                        id = R.id.team_stadium_detail
                        this.gravity = Gravity.CENTER
                        textColor = Color.WHITE
                    }

                    themedTabLayout(R.style.ThemeOverlay_AppCompat_Dark) {
                        lparams(matchParent) {
                            tabMode = TabLayout.MODE_FIXED
                        }
                        id = R.id.tabs_detail_team
                    }
                }
            }

            viewPager {
                id = R.id.viewpager_detail_team

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    progressBar {
                        id = R.id.progress_bar_detail
                    }.lparams { centerHorizontally() }
                }
            }.lparams(matchParent, matchParent)

        }
    }
}