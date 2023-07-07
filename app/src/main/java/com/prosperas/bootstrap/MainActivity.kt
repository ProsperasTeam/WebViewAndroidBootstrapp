package com.prosperas.bootstrap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prosperas.marketplace.CreditoSDK
//import com.creditosegundos.creditosdk.CreditoSDK
//import com.google.gson.Gson
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var creditoSegundos: Button? = null
    private var creditoSegundosHistory: Button? = null

    //Bambu starts this in another activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        creditoSegundos = findViewById(R.id.callSDK)

        creditoSegundos?.setOnClickListener {
            try{

                CreditoSDK.init(applicationContext)
                CreditoSDK.startCredit(this,
                    "d/fvIRnSFf2gQslGnCVm/pz7u8K5JTkEFY5w7KJ1rSawvF0tFj68wuL+Zuy2DrEkr8I/dqUj27iW0fg4RyMaU+jsqimIAsTVZnJjjYWpymAwyFRXa7rZyk8nz0jL1DWG",
                    "53788FFB-B0C2-48C7-929F-7130C2A52128","es-rMX")

            }catch (e: Exception){
                Toast.makeText(applicationContext, "ERROR $e", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                val result = data!!.extras!!
                val success = result.getString("result")
                Toast.makeText(applicationContext, success, Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}