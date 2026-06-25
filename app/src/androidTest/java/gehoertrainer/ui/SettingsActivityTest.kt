package de.htw.gehoertrainer.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import de.htw.gehoertrainer.R
import gehoertrainer.ui.settings.SettingsActivity
import org.junit.Test

class SettingsActivityTest {

    @Test
    fun settingsActivity_buildsCorrectly() {

        ActivityScenario.launch(SettingsActivity::class.java)

        onView(withText("Einstellungen"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.sliderIntervalMin))
            .check(matches(isDisplayed()))

        onView(withId(R.id.sliderIntervalMax))
            .check(matches(isDisplayed()))

        onView(withId(R.id.sliderRounds))
            .check(matches(isDisplayed()))

        onView(withId(R.id.sliderPolyphony))
            .check(matches(isDisplayed()))

        onView(withId(R.id.switchBaseTone))
            .check(matches(isDisplayed()))
    }
}