package com.abdurashidov.codial.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdurashidov.codial.fragments.ViewPagerItemFragment
import com.abdurashidov.codial.models.PagerItem

class StateAdapter(val list:ArrayList<PagerItem>, fragment:Fragment)
    :FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerItemFragment.newInstance(list[position].open, "")
    }
}