package edu.daec.mcu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.InputStream
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class MainActivity : AppCompatActivity() {



    private lateinit var mucDudeAdapter : MUCDudeRecyclerAdapter

    var jAvenger : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()// Adapter instanciado

        Log.i("MUCMARVEL APII", getMarvelAPIUrl())

        //mucDudeAdapter.setData(getDataSet()) // Datos
        MUCMarvelVolley(getMarvelAPIUrl(), this, mucDudeAdapter).callMarvelAPI()

    }



    private fun setRecyclerView(){
        recycler_view_muc.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mucDudeAdapter = MUCDudeRecyclerAdapter()
            adapter = mucDudeAdapter
        }
    }

    fun String.md5():String{
        val md5Al = MessageDigest.getInstance("MD5")
        return BigInteger(1,
            md5Al.digest(toByteArray())).toString(16).padStart(32, '0')
    }
    fun getMarvelAPIUrl(): String{
        val tString = Timestamp(System.currentTimeMillis()).toString()
        val hString = tString + "ede49375699321e3736436b53011574333433f40" + "1681a9eefcf8fbf43de66c59727718da"
        val hash = hString.md5()

        var marvelAPI : String = "https://gateway.marvel.com:443/v1/public/characters?ts=" +
                tString +
                "&limit=100&apikey=ede49375699321e3736436b53011574333433f40&hash="+hash
        return marvelAPI

    }

    private fun getDataSet() : ArrayList<MUCDude> {
        val dudes = ArrayList<MUCDude>()
        jAvenger = leaJSON()
        Log.i("edu.daec.mcu", jAvenger)
        val jArray = JSONArray(jAvenger)
        for ( i in 0..jArray.length()-1){
            val jobject = jArray.getJSONObject(i)
            val name = jobject.getString("name/alias")
            val notes = jobject.getString("notes")
            if(name!= null)  dudes.add(  MUCDude( name, notes ) )
        }
        return  dudes
    }


    fun leaJSON():String?{
        var jContenido:String? = null
        try {

            val inputS : InputStream = assets.open("avengers.json")
            jContenido = inputS.bufferedReader().use { it.readLine() }
        }catch ( ex: Exception){
            ex.printStackTrace()
            Toast.makeText(this, "POM! no Avenger",Toast.LENGTH_LONG).show()

        }

        return jContenido
    }
}
