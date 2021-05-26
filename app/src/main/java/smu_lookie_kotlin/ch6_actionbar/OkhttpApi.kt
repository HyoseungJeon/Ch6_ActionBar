package smu_lookie_kotlin.ch6_actionbar

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class OkHttpApi {

    fun POST(clinet: OkHttpClient, url:String?, jsonbody : String?): String? {
        var response : Response
        try {
            var request = Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), jsonbody))
                    .build()
            response = clinet.newCall(request).execute()
        } catch (e : IOException){
            return e.toString()
        }
        return response.body()!!.string()
    }

    fun JsonBody(id: String?, pw: String?): String?{
        var JSONObject = JSONObject()
                .put("id",id)
                .put("pw",pw)

        return JSONObject.toString()
    }
}