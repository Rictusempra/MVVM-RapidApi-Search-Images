package com.foodsoul.test.di.module

import com.foodsoul.test.ui.image_search.ImagesSearchViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideImageSearchViewModel(): ImagesSearchViewModel {
        return ImagesSearchViewModel()
    }

}
