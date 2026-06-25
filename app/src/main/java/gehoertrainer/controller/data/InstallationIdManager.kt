package gehoertrainer.controller.data

import android.content.Context
import java.util.UUID
import androidx.core.content.edit

/**
 * liefert und verwaltet eine installationID, damit gespeicherte Ergebnisse eindeutig zugewiesen werden können
 */
class InstallationIdManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "installation",
            Context.MODE_PRIVATE
        )

    fun getInstallationId(): String {

        var id =
            prefs.getString(KEY_INSTALLATION_ID, null)

        if (id == null) {

            id = UUID.randomUUID().toString()

            prefs.edit {
                putString(KEY_INSTALLATION_ID, id)
            }
        }

        return id
    }

    companion object {
        private const val KEY_INSTALLATION_ID =
            "installation_id"
    }
}