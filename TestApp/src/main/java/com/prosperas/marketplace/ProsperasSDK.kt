package com.prosperas.marketplace

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.prosperas.marketplace.databinding.ActivityMainBinding

//import kotlinx.android.synthetic.main.activity_main.*


class ProsperasSDK : AppCompatActivity() {
    private var fileChooserCallback: ValueCallback<Array<Uri>>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        GenerateWebView("https://creditos-web.prosperas.com?sessionid=d/fvIRnSFf2gQslGnCVm/pz7u8K5JTkEFY5w7KJ1rSawvF0tFj68wuL+Zuy2DrEkr8I/dqUj27iW0fg4RyMaU+jsqimIAsTVZnJjjYWpymAwyFRXa7rZyk8nz0jL1DWG&apikey=f0627e97-48fb-456b-ba05-c20ce8c56105&locale=es-rMX")
    }

    override fun onPause() {
        super.onPause()
    }
    fun openWebURL(url: String?) {
        var url = url ?: return
        if (!URLUtil.isValidUrl(url)) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://$url"
        }
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
    private fun GenerateWebView(uriString: String)
    {
        val home = Uri.parse(uriString)

        val view = binding.eazulWebview


        val settings = view.settings
        settings.allowContentAccess = true
        settings.allowFileAccess = true
        settings.databaseEnabled = true
        settings.domStorageEnabled = true
        settings.setGeolocationEnabled(true)
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.javaScriptEnabled = true
        settings.loadsImagesAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.setSupportMultipleWindows(true)
        //settings.userAgentString = "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.5735.57 Mobile Safari/537.36"
        view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                vw: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.toString().contains(home.host!!)) {
                    vw.loadUrl(request.url.toString())
                } else {
                    //val intent = Intent(Intent.ACTION_VIEW, request.url)
                    //vw.context.startActivity(intent)
                    vw.loadUrl(request.url.toString())
                }
                return true
            }
        }
        view.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }

            override fun onShowFileChooser(
                vw: WebView, filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (fileChooserCallback != null) {
                    fileChooserCallback!!.onReceiveValue(null)
                }
                fileChooserCallback = filePathCallback
                val selectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                selectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                selectionIntent.type = "*/*"
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, selectionIntent)
                startActivityForResult(chooserIntent, 0)
                return true
            }
        }
        view.setOnKeyListener { v: View, keyCode: Int, event: KeyEvent ->
            val vw = v as WebView
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && vw.canGoBack()) {
                vw.goBack()
                return@setOnKeyListener true
            }
            false
        }
        view.setDownloadListener { uri: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long ->
            handleURI(
                uri
            )
        }
        view.setOnLongClickListener { v: View ->
            handleURI((v as WebView).hitTestResult.extra)
            true
        }

        view.loadUrl(home.toString())
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (intent != null) {
            fileChooserCallback!!.onReceiveValue(arrayOf(Uri.parse(intent.dataString)))
        }

        if(resultCode != Activity.RESULT_OK)
        {
            fileChooserCallback!!.onReceiveValue(arrayOf<Uri>())
            fileChooserCallback = null
        }

        fileChooserCallback = null
    }

    private fun handleURI(uri: String?) {
        if (uri != null) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(uri.replaceFirst("^blob:".toRegex(), ""))
            startActivity(i)
        }
    }
}

