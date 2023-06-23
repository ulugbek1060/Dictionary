package gh.code.dictionary.data.network

import gh.code.dictionary.data.network.models.Word
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("entries/en/{word}")
    suspend fun getWord(@Path("word") word: String): List<Word>

}