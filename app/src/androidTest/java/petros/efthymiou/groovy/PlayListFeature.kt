package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.Matchers.allOf
import org.junit.Test
import petros.efthymiou.groovy.di.idlingResource

class   PlayListFeature: BaseAndroidTest() {

    @Test
    fun displayScreenTitle() {
       assertDisplayed("PlayLists")
    }

    @Test
    fun displaysListOfPlayLists(){

        assertRecyclerViewItemCount(R.id.rv_playlist, 10)

        onView(allOf(withId(R.id.playlist_name), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_category), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))))
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.image_playlist), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))))
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

    }

    @Test
    fun displayLoadingWhilePlayListAppears(){
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader_playlist)
    }

    @Test
    fun removeLoadingWhenListAppears(){
        assertNotDisplayed(R.id.loader_playlist)
    }

    @Test
    fun displayRockImageForRockCategoryListItem(){

        onView(allOf(withId(R.id.image_playlist), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 1))))
            .check(matches(withDrawable(R.mipmap.playlist)))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.image_playlist), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))))
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigatesToPlaylistOnClickPlaylistItem(){
        onView(allOf(withId(R.id.image_playlist), isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))))
            .perform(click())

        assertDisplayed(R.id.parent)

    }

}