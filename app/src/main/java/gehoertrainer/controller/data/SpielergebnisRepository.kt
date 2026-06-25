package gehoertrainer.controller.data

import android.content.Context
import gehoertrainer.model.SpielergebnisModel
import org.json.JSONArray
import org.json.JSONObject
import androidx.core.content.edit

class SpielergebnisRepository(context: Context) :
    ISpielergebnisRepository {

    private val prefs =
        context.getSharedPreferences(
            "highscores",
            Context.MODE_PRIVATE
        )

    override fun save(result: SpielergebnisModel) {

        val current = load().toMutableList()

        current.add(result)

        val jsonArray = JSONArray()

        current.forEach {

            val obj = JSONObject()

            obj.put("playerId", it.playerId)
            obj.put("rounds", it.rounds)
            obj.put("baseTone", it.baseTone)
            obj.put("intervalMin", it.intervalMin)
            obj.put("intervalMax", it.intervalMax)
            obj.put("polyphon", it.polyphon)
            obj.put("deviceId", it.deviceId)
            obj.put("correct", it.correct)
            obj.put("timestamp", it.timestamp)

            jsonArray.put(obj)
        }

        prefs.edit {
            putString("entries", jsonArray.toString())
        }
    }

    override fun load(): List<SpielergebnisModel> {

        val json =
            prefs.getString("entries", "[]") ?: "[]"

        val array = JSONArray(json)

        val result = mutableListOf<SpielergebnisModel>()

        for (i in 0 until array.length()) {

            val obj = array.getJSONObject(i)

            result.add(
                SpielergebnisModel(
                    obj.getString("playerId"),
                    obj.getInt("rounds"),
                    obj.getBoolean("baseTone"),
                    obj.getInt("intervalMin"),
                    obj.getInt("intervalMax"),
                    obj.getInt("polyphon"),
                    obj.getString("deviceId"),
                    obj.getInt("correct"),
                    obj.getLong("timestamp")
                )
            )
        }

        return result
    }
}