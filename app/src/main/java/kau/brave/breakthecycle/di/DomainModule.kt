package kau.brave.breakthecycle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kau.brave.breakthecycle.data.domain.SHA256EncoderImpl
import kau.brave.breakthecycle.domain.domain.DateParser
import kau.brave.breakthecycle.data.domain.DateParserImpl
import kau.brave.breakthecycle.domain.domain.SHA256Encoder

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSHA256Encoder(
        shA256EncoderImpl: SHA256EncoderImpl
    ): SHA256Encoder

    @Binds
    @ViewModelScoped
    abstract fun bindDateParser(
        dateParserImpl: DateParserImpl
    ): DateParser
}