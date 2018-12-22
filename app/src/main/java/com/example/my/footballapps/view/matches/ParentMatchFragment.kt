package com.example.my.footballapps.view.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.my.footballapps.R
import com.example.my.footballapps.R.id.button_search
import com.example.my.footballapps.adapter.MatchPagerAdapter
import com.example.my.footballapps.view.matches.search.MatchSearch
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.startActivity


class ParentMatchFragment : Fragment() {

    private var menuItem: Menu? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        view_pager.adapter = MatchPagerAdapter(childFragmentManager)
        tabs_match.setupWithViewPager(view_pager)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)
        menuItem = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            button_search -> {
                context?.startActivity<MatchSearch>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
