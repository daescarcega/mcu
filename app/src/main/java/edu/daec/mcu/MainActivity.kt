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

class MainActivity : AppCompatActivity() {


    private lateinit var mucDudeAdapter : MUCDudeRecyclerAdapter

    var jAvenger : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()// Adapter instanciado
        mucDudeAdapter.setData(getDataSet()) // Datos

    }


    private fun setRecyclerView(){
        recycler_view_muc.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mucDudeAdapter = MUCDudeRecyclerAdapter()
            adapter = mucDudeAdapter
        }
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
