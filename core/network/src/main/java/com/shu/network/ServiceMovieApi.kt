package  com.shu.network

import com.shu.network.modelDetail.ActorsDto
import com.shu.network.modelDetail.DetailMovieDto
import com.shu.network.models.ListCollectionsDto
import com.shu.network.models.ListFiltersFilmDto
import com.shu.network.models.ListPremiersDto
import com.shu.network.models.filters.ListFiltersDto
import com.shu.network.models.gallery_models.ListGalleryItemsDto
import com.shu.network.models.media_posts.ListPostsDto
import com.shu.network.models.similar_models.ListSimilarDto
import com.shu.network.models_person.ListSearchPersonDto
import com.shu.network.models_person.MovieOfActorDto
import com.shu.network.models_person.PersonDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceMovieApi {

    @GET("/api/v2.2/films/premieres")
    suspend fun movies(@Query("year") year: Int, @Query("month") month: String): ListPremiersDto


    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun popular(@Query("page") page: Int): ListCollectionsDto


    @GET("/api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun top250(@Query("page") page: Int): ListCollectionsDto


    @GET("/api/v2.2/films/top?type=TOP_AWAIT_FILMS")
    suspend fun awaitFilms(@Query("page") page: Int): ListCollectionsDto

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilm(@Path("id") id: Int): DetailMovieDto

    @GET("api/v2.2/films")
    suspend fun filmVip(
        @Query("countries") country: Int = 1,
        @Query("genres") genres: Int = 11,
        @Query("order") order: String = "NUM_VOTE",
        @Query("type") type: String = "FILM",
        @Query("ratingFrom") ratingFrom: Float = 0.0f,
        @Query("ratingTo") ratingTo: Float = 10.0f,
        @Query("yearFrom") yearFrom: Int = 2000,
        @Query("yearTo") yearTo: Int = 2024,
        @Query("page") page: Int = 1,
        @Query("keyword") keyword: String = ""
    ): ListFiltersFilmDto

    //
    @GET("api/v2.2/films")
    suspend fun serialVip(
        @Query("order") order: String = "NUM_VOTE",
        @Query("type") type: String = "TV_SERIES",
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 2000,
        @Query("yearTo") yearTo: Int = 2023,
        @Query("page") page: Int = 1
    ): ListFiltersFilmDto

    @GET("/api/v1/staff")
    suspend fun actors(@Query("filmId") filmId: Int): List<ActorsDto>

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun similar(@Path("id") id: Int?): ListSimilarDto

    @GET("/api/v2.2/films/{id}/images")
    suspend fun galleryTotal(
        @Path("id") id: Int,
        @Query("page") page: Int = 1,
        @Query("type") type: String = "STILL",
    ): ListGalleryItemsDto

    @GET("/api/v2.2/films/filters")
    suspend fun genreCountry(): ListFiltersDto


    @GET("/api/v1/staff/{id}")
    suspend fun person(@Path("id") id: Int): PersonDto

    @GET("/api/v1/persons")
    suspend fun searchPerson(
        @Query("name") name: String,
        @Query("page") page: Int,
        ): ListSearchPersonDto

    @GET("/api/v2.2/films/{id}")
    suspend fun detailFilmography(@Path("id") id: Int?): MovieOfActorDto

    /**   /api/v1/media_posts
     *  получить медиа новости с сайта кинопоиск
     */
    @GET("/api/v1/media_posts")
    suspend fun getPosts(@Query("page") page: Int): ListPostsDto

}
/*
 @GET("/api/v2.2/films/{id}/facts")
 suspend fun factList(@Path("id") id: Int): FactList



/*
 @GET("/api/v2.2/films/{id}")
 suspend fun detailFilmography(@Path("id") id: Int?): MovieImageDto
*/
//
 @GET("/api/v2.2/films/filters")
 suspend fun genreCountry(): ListFiltersDto

/*
 @GET("/api/v2.2/films/{id}/seasons")
 suspend fun serial(@Path("id") id: Int): SeasonsSerialDto


 @GET("/api/v2.2/films/{id}/similars")
 suspend fun similar(@Path("id") id: Int?): ListSimilarDto


 @GET("/api/v2.1/films/search-by-keyword")
 suspend fun search(
     @Query("page") page: Int,
     @Query("keyword") keyword: String
 ): SearchListDto


 @GET("/api/v2.2/films/{id}/images")
 suspend fun gallery(
     @Path("id") id: Int,
     @Query("page") page: Int,
     @Query("type") type: String = "STILL",
 ): GalleryListDto


 @GET("/api/v2.2/films/{id}/images")
 suspend fun galleryTotal(
     @Path("id") id: Int,
     @Query("page") page: Int = 1,
     @Query("type") type: String = "STILL",
 ): GalleryTotalListDto*/

//


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
}

/*
отсчёт страниц начинается с 1 а не с 0
*/
*/