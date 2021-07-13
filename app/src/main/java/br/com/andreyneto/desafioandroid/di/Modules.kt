package br.com.andreyneto.desafioandroid.di

import br.com.andreyneto.desafioandroid.service.MarvelService
import br.com.andreyneto.desafioandroid.repository.MarvelRepository
import br.com.andreyneto.desafioandroid.repository.MarvelRepositoryImpl
import br.com.andreyneto.desafioandroid.view.CharacterViewModel
import br.com.andreyneto.desafioandroid.view.ComicViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

private const val API_URL = "https://gateway.marvel.com:443/v1/public/"

val networkModule = module {
    factory { provideLogging() }
    factory { provideOkHttpClient(get()) }
    factory { provideMarvelServiceApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
) = OkHttpClient.Builder().apply {
    addInterceptor(httpLoggingInterceptor)
}.build()

fun provideMarvelServiceApi(retrofit: Retrofit) = retrofit.create(MarvelService::class.java)

fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(API_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideLogging() = HttpLoggingInterceptor().apply{
    setLevel(HttpLoggingInterceptor.Level.BASIC)
}

val viewModelModule = module {
    viewModel { CharacterViewModel(get()) }
    viewModel { ComicViewModel(get()) }
}

val repositoryModule = module {
    factory<MarvelRepository> { MarvelRepositoryImpl(get()) }
}