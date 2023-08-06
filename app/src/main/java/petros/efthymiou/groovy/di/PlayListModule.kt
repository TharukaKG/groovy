package petros.efthymiou.groovy.di

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import petros.efthymiou.groovy.services.PlayListClients.PLayListClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okHttp", client)

@Module
@InstallIn(FragmentComponent::class)
class PlayListModule {

    @Provides
    fun playListClient(retrofit:Retrofit):PLayListClient = retrofit.create(PLayListClient::class.java)

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.5:3000/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}