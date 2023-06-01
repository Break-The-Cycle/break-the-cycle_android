package kau.brave.breakthecycle.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.network.service.AuthService
import kau.brave.breakthecycle.network.service.BraveClient
import kau.brave.breakthecycle.network.service.ViolentRecordService
import kau.brave.breakthecycle.utils.Constants.DEV_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideRunwayInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
//            .authenticator(TokenAuthenticator())
            .addInterceptor(ServiceInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    fun provideRunwayRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(DEV_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideViloentRecordService(
        retrofit: Retrofit
    ): ViolentRecordService {
        return retrofit.create(ViolentRecordService::class.java)
    }

    @Provides
    @Singleton
    fun provideBraveClient(
        authService: AuthService,
        violentRecordService: ViolentRecordService
    ): BraveClient {
        return BraveClient(
            authService = authService,
            violentRecordService = violentRecordService
        )
    }

}