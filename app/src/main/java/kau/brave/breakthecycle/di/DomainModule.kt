package kau.brave.breakthecycle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kau.brave.breakthecycle.data.repository.SHA256EncoderImpl
import kau.brave.breakthecycle.domain.SHA256Encoder

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSHA256Encoder(
        shA256EncoderImpl: SHA256EncoderImpl
    ): SHA256Encoder
}