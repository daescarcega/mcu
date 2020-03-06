package edu.daec.mcu

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MUCMarvelVolley(val url:String, val contexto:Context, val mucAdapter: MUCDudeRecyclerAdapter){
    val queue = Volley.newRequestQueue(contexto)

    fun callMarvelAPI(){
        val dataHeroes = ArrayList<MUCDude>()
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                val heroes = response.getJSONObject("data").getJSONArray("results")
                for (i in 0..heroes.length()-1){
                    val character = heroes.getJSONObject(i)
                    val image = character.getJSONObject("thumbnail")
                    val thumbnail = image.getString("path") + "."+image.getString("extension")

                    val dude  =  MUCDude(character.getString("name"), character.getString("description"))
                    dataHeroes.add(dude)
                }


                mucAdapter.setData(dataHeroes)
            },
            Response.ErrorListener {
                Log.e("Volley Error", it.toString())
                Toast.makeText(contexto, "That didn't work!" + it.toString(), Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}