package com.foodsoul.test.di

import com.foodsoul.test.MainActivity
import com.foodsoul.test.di.module.DatabaseModule
import com.foodsoul.test.di.module.NavigationModule
import com.foodsoul.test.di.module.RemoteModule
import com.foodsoul.test.di.module.ViewModelModule
import com.foodsoul.test.ui.image_search.ImagesSearchViewModel
import com.foodsoul.test.ui.image_search.ImagesSearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NavigationModule::class,
    RemoteModule::class,
    DatabaseModule::class,
    ViewModelModule::class
])

interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ImagesSearchFragment)
    fun inject(viewModel: ImagesSearchViewModel)
}