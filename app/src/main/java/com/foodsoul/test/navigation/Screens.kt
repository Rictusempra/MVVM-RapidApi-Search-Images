package com.foodsoul.test.navigation

import com.foodsoul.test.ui.image_search.ImagesSearchFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun ImageSearch() = FragmentScreen() {
        ImagesSearchFragment.getNewInstance()
    }

}