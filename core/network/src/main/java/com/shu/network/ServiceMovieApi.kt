package  com.shu.network

import com.shu.network.models.CinemaItemDto
import com.shu.network.models.ListCinemaDto
import com.shu.network.models.filters.ListFiltersDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ServiceMovieApi {
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun movies(@Query("year") year: Int, @Query("month") month: String): ListCinemaDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun popular(@Query("page") page: Int): ListCinemaDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun top250(@Query("page") page: Int): ListCinemaDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/top?type=TOP_AWAIT_FILMS")
    suspend fun awaitFilms(@Query("page") page: Int): ListCinemaDto


   /* @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/facts")
    suspend fun factList(@Path("id") id: Int): FactList

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun detail(@Path("id") id: Int): DetailMovieDto*/

   /* @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun detailFilmography(@Path("id") id: Int?): MovieImageDto
*/
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun genreCountry(): ListFiltersDto

   /* @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun serial(@Path("id") id: Int): SeasonsSerialDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun similar(@Path("id") id: Int?): ListSimilarDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun search(
        @Query("page") page: Int,
        @Query("keyword") keyword: String
    ): SearchListDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun gallery(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("type") type: String = "STILL",
    ): GalleryListDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun galleryTotal(
        @Path("id") id: Int,
        @Query("page") page: Int = 1,
        @Query("type") type: String = "STILL",
    ): GalleryTotalListDto*/

    @Headers("X-API-KEY: $api_key")
    @GET("api/v2.2/films")
    suspend fun filmVip(
        @Query("countries") country : Int = 1,
        @Query("genres") genres : Int = 11,
        @Query("order") order: String = "NUM_VOTE",
        @Query("type") type: String = "FILM",
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 2000,
        @Query("yearTo") yearTo: Int = 2024,
        @Query("page") page: Int = 1,
        @Query("keyword") keyword: String = ""
    ): ListCinemaDto

    @Headers("X-API-KEY: $api_key")
    @GET("api/v2.2/films")
    suspend fun serialVip(
        @Query("order") order: String = "NUM_VOTE",
        @Query("type") type: String = "TV_SERIES",
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 2000,
        @Query("yearTo") yearTo: Int = 2023,
        @Query("page") page: Int = 1
    ): ListCinemaDto

  /*  @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun actors(@Query("filmId") filmId: Int): List<ActorsDto>

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff/{id}")
    suspend fun person(@Path("id") id: Int): PersonDto*/

// order=RATING&type=FILM&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=1
    /*
    order = RATING, NUM_VOTE, YEAR
    type = FILM, TV_SHOW, TV_SERIES,MINI_SERIES, ALL
    imdbid : String = imdbid

countries 1 - США 2- Швейцария 3 - Франция 4 - Польша 5 - Великобритания 6 -Швеция
genres 1 - триллер 2 - драма 3 -криминал 4 - мелодрама 5- детектив 6 - фантастика

7- Индия 8 - Испания 9 - германия

7 - приключения 8 -биография 9 - фильм-нуар  10 - вестерн 11 - боевик
     */
    private companion object {
      //private const val api_key = "e3390a86-ab61-4dcd-b721-7cee593b1ecd" // 18.10
    private const val api_key = "6f544dac-8b44-4204-a502-203a753e8985" //13.10
    //  private const val api_key = "00129dee-5912-4f04-bfb7-57b378a0b86e" // 14.l0
    }
}

/*
отсчёт страниц начинается с 1 а не с 0
 */
