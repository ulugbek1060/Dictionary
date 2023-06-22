package gh.code.dictionary.data.api

import gh.code.dictionary.data.models.ResponseWord
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("entries/en/{word}")
    suspend fun getWord(@Path("word") word: String): List<ResponseWord>

}