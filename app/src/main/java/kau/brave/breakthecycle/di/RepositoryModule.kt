package kau.brave.breakthecycle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import kau.brave.breakthecycle.data.repository.AuthRepositoryImpl
import kau.brave.breakthecycle.domain.repository.AuthRepository

@Module
@InstallIn(ViewModelScoped::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}