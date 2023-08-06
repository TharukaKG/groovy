package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.Matchers.allOf
import org.junit.Test
import petros.efthymiou.groovy.di.idlingResource

class PlaylistDetailsFeature: BaseAndroidTest() {

    @Test
    fun displayPlayListDetails(){

        navigate()

        assertDisplayed(R.id.tv_playlist_name)
        assertDisplayed(R.id.tv_songs)

        onView(allOf(withId(R.id.tv_playlist_name)))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.tv_songs)))
            .check(matches(withText("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")))
            .check(matches(isDisplayed()))

        assertNotDisplayed(R.id.loader_details)
    }

    @Test
    fun showLoaderOnLoadingDetails(){
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(400)
        navigate()

        assertDisplayed(R.id.loader_details)
    }

    @Test
    fun showErrorWhenNoPlaylistData(){
        navigateToFailing()
        assertDisplayed("No Playlist content available")
    }

    private fun navigate(){
        onView(allOf(withId(R.id.image_playlist), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))))
            .perform(ViewActions.click())
    }

    private fun navigateToFailing(){
        onView(allOf(withId(R.id.image_playlist), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 1))))
            .perform(ViewActions.click())
    }
}