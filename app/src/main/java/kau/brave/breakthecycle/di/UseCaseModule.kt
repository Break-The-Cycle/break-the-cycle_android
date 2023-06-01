package kau.brave.breakthecycle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kau.brave.breakthecycle.data.usecase.CalendarUseCaseImpl
import kau.brave.breakthecycle.data.usecase.HomeUseCaseImpl
import kau.brave.breakthecycle.domain.usecase.CalendarUseCase
import kau.brave.breakthecycle.domain.usecase.HomeUseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCalendarUseCase(
        calendarUseCaseImpl: CalendarUseCaseImpl
    ): CalendarUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindHomeUseCase(
        homeUseCaseImpl: HomeUseCaseImpl
    ): HomeUseCase
}