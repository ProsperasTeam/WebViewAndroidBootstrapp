import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams

class SDKSettingsView(
    private val activity: AppCompatActivity,
    private val initialLocale: String,
    private val initialApikey: String,
    private val initialSessionId: String,
    private val initialUrl: String
) {

    private val newLocale: EditText = EditText(activity)
    private val newApikey: EditText = EditText(activity)
    private val newSessionId: EditText = EditText(activity)
    private val newUrl: EditText = EditText(activity)
    private val callSDKButton: AppCompatButton = AppCompatButton(activity)

    init {
        // Initialize UI elements and set their properties
        // ...

        // Initialize EditText values
        newLocale.setText(initialLocale)
        newApikey.setText(initialApikey)
        newSessionId.setText(initialSessionId)
        newUrl.setText(initialUrl)

        // Generate unique IDs for the views
        val newLocaleId = View.generateViewId()
        val newApikeyId = View.generateViewId()
        val newSessionIdId = View.generateViewId()
        val newUrlId = View.generateViewId()
        val callSDKButtonId = View.generateViewId()

        // Set IDs for the views
        newLocale.id = newLocaleId
        newApikey.id = newApikeyId
        newSessionId.id = newSessionIdId
        newUrl.id = newUrlId
        callSDKButton.id = callSDKButtonId

        // Set text for the button
        callSDKButton.text = "Click Me"

        // Create the ConstraintLayout
        val constraintLayout = ConstraintLayout(activity)

        // Add UI elements to the ConstraintLayout
        constraintLayout.addView(newLocale)
        constraintLayout.addView(newApikey)
        constraintLayout.addView(newSessionId)
        constraintLayout.addView(newUrl)
        constraintLayout.addView(callSDKButton)

        // Add a TextView for the legend/title
        val legendTextView = TextView(activity)
        legendTextView.id = View.generateViewId()
        constraintLayout.addView(legendTextView)

        // Set layout constraints
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        // Connect the legend TextView at the top
        constraintSet.connect(legendTextView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.centerHorizontally(legendTextView.id, ConstraintSet.PARENT_ID)

        // Connect Locale EditText below the legend TextView
        constraintSet.connect(newLocale.id, ConstraintSet.TOP, legendTextView.id, ConstraintSet.BOTTOM)
        constraintSet.setMargin(newLocale.id, ConstraintSet.TOP, convertDpToPixels(16)) // Adjust the spacing (as needed)

        // Connect Apikey EditText below Locale EditText
        constraintSet.connect(newApikey.id, ConstraintSet.TOP, newLocale.id, ConstraintSet.BOTTOM)
        constraintSet.setMargin(newApikey.id, ConstraintSet.TOP, convertDpToPixels(16)) // Adjust the spacing (as needed)

        // Connect SessionId EditText below Apikey EditText
        constraintSet.connect(newSessionId.id, ConstraintSet.TOP, newApikey.id, ConstraintSet.BOTTOM)
        constraintSet.setMargin(newSessionId.id, ConstraintSet.TOP, convertDpToPixels(16)) // Adjust the spacing (as needed)

        // Connect Url EditText below SessionId EditText
        constraintSet.connect(newUrl.id, ConstraintSet.TOP, newSessionId.id, ConstraintSet.BOTTOM)
        constraintSet.setMargin(newUrl.id, ConstraintSet.TOP, convertDpToPixels(16)) // Adjust the spacing (as needed)

        // Connect the button below Url EditText with additional space
        constraintSet.connect(callSDKButton.id, ConstraintSet.TOP, newUrl.id, ConstraintSet.BOTTOM)
        constraintSet.setMargin(callSDKButton.id, ConstraintSet.TOP, convertDpToPixels(48)) // Increase the spacing (adjust as needed)

        // Center all views horizontally
        constraintSet.centerHorizontally(newLocale.id, ConstraintSet.PARENT_ID)
        constraintSet.centerHorizontally(newApikey.id, ConstraintSet.PARENT_ID)
        constraintSet.centerHorizontally(newSessionId.id, ConstraintSet.PARENT_ID)
        constraintSet.centerHorizontally(newUrl.id, ConstraintSet.PARENT_ID)
        constraintSet.centerHorizontally(callSDKButton.id, ConstraintSet.PARENT_ID)

        // Apply constraints to the layout
        constraintSet.applyTo(constraintLayout)

        // Set click listener for the button
        callSDKButton.setOnClickListener {
            val settings = SDKSettings(
                newLocale.text.toString(),
                newApikey.text.toString(),
                newSessionId.text.toString(),
                newUrl.text.toString()
            )
            SDKSettingsController().startSDK(activity, settings)
        }

        // Set the created layout as the content view
        activity.setContentView(constraintLayout)
    }

    // Helper function to convert DP to pixels
    private fun convertDpToPixels(dp: Int): Int {
        val scale = activity.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}
