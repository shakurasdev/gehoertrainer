package gehoertrainer.controller.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InstallationIdManagerTest {

    private lateinit var manager: InstallationIdManager

    @Before
    fun setup() {

        val context =
            ApplicationProvider.getApplicationContext<Context>()

        context.getSharedPreferences(
            "installation",
            Context.MODE_PRIVATE
        ).edit().clear().commit()

        manager = InstallationIdManager(context)
    }

    @Test
    fun getInstallationId_createsId_whenNoneExists() {

        val id = manager.getInstallationId()

        assertNotNull(id)
        assertTrue(id.isNotBlank())
    }

    @Test
    fun getInstallationId_returnsSameId_onRepeatedCalls() {

        val id1 = manager.getInstallationId()
        val id2 = manager.getInstallationId()

        assertEquals(id1, id2)
    }

    @Test
    fun getInstallationId_persistsId_betweenInstances() {

        val context =
            ApplicationProvider.getApplicationContext<Context>()

        val manager1 =
            InstallationIdManager(context)

        val id1 =
            manager1.getInstallationId()

        val manager2 =
            InstallationIdManager(context)

        val id2 =
            manager2.getInstallationId()

        assertEquals(id1, id2)
    }
}