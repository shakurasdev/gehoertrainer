package gehoertrainer.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import gehoertrainer.ui.main.MainActivity
import org.junit.Test
import de.htw.gehoertrainer.R

class MainActivityNavigationTest {

    @Test
    fun clickSettings_opensSettingsActivity() {

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btnStart))
            .perform(click())

        onView(withId(R.id.tvTitle))
            .check(matches(withText("Einstellungen")))
    }

    @Test
    fun highscoreButton_opensHighscoreActivity() {

        ActivityScenario.launch(MainActivity::class.java)

        onView(withText("Bestenliste"))
            .perform(click())

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
}