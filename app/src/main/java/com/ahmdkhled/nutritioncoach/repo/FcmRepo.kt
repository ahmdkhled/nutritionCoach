package com.ahmdkhled.nutritioncoach.repo

import android.util.Log
import com.ahmdkhled.nutritioncoach.App
import com.ahmdkhled.nutritioncoach.model.FcmPayload
import com.ahmdkhled.nutritioncoach.model.FcmRes
import com.ahmdkhled.nutritioncoach.model.Notification
import com.ahmdkhled.nutritioncoach.network.GsonHelper
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class FcmRepo {

    private val TAG = "FcmRepo"
    private val url = "https://fcm.googleapis.com/fcm/send"

    @ExperimentalStdlibApi
    suspend fun send(name: String, message: String, token: String): FcmRes {

        val notification = Notification(name, message, "")
        val fcmPayload = FcmPayload(notification, token)
        val payloadBytes = GsonHelper().toString(fcmPayload).encodeToByteArray()


        val request = object : StringRequest(Method.POST, url, Response.Listener {
            Log.d(TAG, "send: $it")

        }, Response.ErrorListener {
            Log.d(TAG, "send: "+it)
        }) {

            override fun getBody(): ByteArray {
                return payloadBytes
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] =
                    "key=AAAA-hA1n6s:APA91bH3GHkbBI-YQJNrGHr8dayQkQanozuns1QUchklmtaAfcW2BTyqmJAibkByCw6TqjSQBF7CeM9dapYTv5-7rThAfruenlnViLHGcPrtqg0qeG9QPKvHrrmV9rK21IRR1JukkkJj"
                headers["Content-Type"]="application/json"
                return headers
            }


        }
        Volley.newRequestQueue(App.context)
            .add(request)
        return FcmRes(0,1)



    }
}