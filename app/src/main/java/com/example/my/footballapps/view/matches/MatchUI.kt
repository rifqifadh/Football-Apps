package com.example.my.footballapps.view.matches

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.ripple
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class MatchUI : AnkoComponent<ViewGroup> {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        cardView {
            ripple(this, 0)
            lparams(matchParent, wrapContent){
                gravity = Gravity.CENTER
                margin = dip(4)
                radius = 10f
            }

            linearLayout {
                backgroundColor = Color.WHITE
                orientation = LinearLayout.VERTICAL
                padding = dip(8)

                textView {
                    id = R.id.dateId
                    text = "Sat,1 Dec 2018"
                    textColor = ContextCompat.getColor(ctx, R.color.colorYellow)
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                textView {
                    id = R.id.timeId
                    text = "19:00"
                    textColor = ContextCompat.getColor(ctx, R.color.colorYellow)
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL

                    textView {
                        id = R.id.homeTeam
                        gravity = Gravity.CENTER
                        textSize = 18f
                        text = "home"
                    }.lparams(matchParent, wrapContent, 1f)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = R.id.homeScore
                            padding = dip(8)
                            textSize = 20f
                            setTypeface(null, Typeface.BOLD)
                            text = "0"
                        }

                        textView {
                            text = "VS"
                        }

                        textView {
                            id = R.id.awayScore
                            padding = dip(8)
                            textSize = 20f
                            setTypeface(null, Typeface.BOLD)
                            text = "0"
                        }
                    }

                    textView {
                        id = R.id.awayTeam
                        gravity = Gravity.CENTER
                        textSize = 18f
                        text = "away"
                    }.lparams(matchParent, wrapContent, 1f)
                }
            }.lparams(matchParent, matchParent){
                setMargins(dip(16), dip(8), dip(16), dip(8))
            }
        }
    }
}
