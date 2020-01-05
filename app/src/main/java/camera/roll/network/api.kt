import android.util.Log
import camera.roll.BuildConfig
import camera.roll.model.PictureItem
import camera.roll.network.RandomuserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomuserApi {

    @GET("/api")
    suspend fun getApi(@Query("results") results: Int): RandomuserResponse

    companion object {
        fun create(): RandomuserApi {
            val client = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
                })
                .build()
            return Retrofit.Builder().baseUrl("https://randomuser.me")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(RandomuserApi::class.java)
        }
    }
}

class RandomuserApiRequestHandler {

    private val api = RandomuserApi.create()

    suspend fun getData(results: Int): List<PictureItem> {
        Log.d("API", "Making the request")
        val data = api.getApi(results = results)
        Log.d("API", "Received data $data")
        return withContext(Dispatchers.Default) {
            transformResponse(data)
        }
    }

    private fun transformResponse(response: RandomuserResponse?): List<PictureItem> {
        Log.d("API", "Transforming data")
        return when (response) {
            null -> listOf()
            else -> response.results.map { item ->
                PictureItem(
                    imageUrl = item.picture.large,
                    placeholder = "${item.name.first} ${item.name.last}"
                )
            }
        }
    }

}
