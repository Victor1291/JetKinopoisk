package com.shu.jetcinema

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.JetCinemaTheme
import com.shu.home.HomeScreen
import com.shu.models.CinemaItem
import com.shu.models.Countries
import com.shu.models.FilmVip
import com.shu.models.Genres
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts
import com.shu.models.media_posts.Post
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty

class HomeScreenTest {


    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes resId: Int) =
        ReadOnlyProperty<Any, String> { _, _ -> activity.getString(resId) }

    private val home by composeTestRule.stringResource(R.string.home)


    @Test
    fun check_HomeScreen(): Unit = with(composeTestRule) {
        composeTestRule.setContent {
            JetCinemaTheme {
                HomeScreen(
                    manyScreens = manymanyScreens,
                    innerPadding = PaddingValues(4.dp),
                    posts = ListPosts(
                        total = 10,
                        totalPages = null,
                        items = listOf(
                            post,
                            post,
                            post,
                            post,
                            post,
                            post,
                            post,
                            post,
                            post,
                            post,
                            post,
                            post
                        )
                    ),
                    onMovieClick = {},
                    onPostClick = {},
                    onListClick = {},
                )

            }
        }
        onRoot().printToLog("Ui test")

        onNodeWithText("Test Post").assertIsDisplayed()
        onNodeWithText("Premieres").assertIsDisplayed()


        onNodeWithContentDescription("collection").assertIsDisplayed()
    }

}

val post = Post(
    kinopoiskId = 1,
    imageUrl = null,
    title = "Test Post",
    description = "description",
    url = "url",
    publishedAt = "publishedAt"
)

val cinema = CinemaItem(
    kinopoiskId = 1,
    nameRu = "nameRu",
    nameEn = "nameEn",
    nameOriginal = "nameOriginal",
    posterUrl = "posterUrl",
    posterUrlPreview = "posterUrlPrevie",
    year = 2024,
    rating = "8.0",
    countries = listOf(Countries(1, "Russia")),
    genres = listOf(Genres(1, "action")),
    premiereRu = "2.07.2024",
)

val oneList = listOf(cinema, cinema, cinema, cinema, cinema, cinema, cinema, cinema, cinema)

val manymanyScreens = ManyScreens(
    homeListScreen = listOf(
        oneList, oneList, oneList, oneList, oneList, oneList, oneList
    ),
    listTitle = listOf("Премьеры", "Популярное", "Топ 250", "", "", "Сериалы"),
    filmVipOne = FilmVip(),
    filmVipTwo = FilmVip(),

    )
