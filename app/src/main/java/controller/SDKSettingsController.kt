import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prosperas.activity.CreditoSDK

class SDKSettingsController {

    fun startSDK(activity: AppCompatActivity, settings: SDKSettings) {
        try {
            CreditoSDK.init(activity.applicationContext)
            CreditoSDK.startCredit(
                activity,
                settings.sessionId,
                settings.apikey,
                settings.locale,
                settings.url,
                "#00FF00"
            )
        } catch (e: Exception) {
            Toast.makeText(activity.applicationContext, "ERROR $e", Toast.LENGTH_LONG).show()
        }
    }
}
