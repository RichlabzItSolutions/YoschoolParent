package com.credoapp.parent.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.credoapp.parent.R
import kotlinx.android.synthetic.main.content_web_view_online.*
import kotlinx.android.synthetic.main.no_zoom_pop_up.view.*

class WebViewOnlineActivity : AppCompatActivity() {
    var JoinUrl :String =""
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_online)
        setSupportActionBar(findViewById(R.id.toolbar))

        JoinUrl = intent.getStringExtra("zoomUrl").toString()
        Log.e("URL", JoinUrl)
        liveClassWebView.settings.javaScriptEnabled = true //enable javascript

        liveClassWebView.settings.domStorageEnabled = true
        liveClassWebView.settings.useWideViewPort = true
        liveClassWebView.settings.supportMultipleWindows()
        liveClassWebView.settings.setSupportMultipleWindows(true)
        liveClassWebView.settings.loadWithOverviewMode = true
        val activity: Activity = this
        liveClassWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (Uri.parse(url).scheme == "market") {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        return true
                    } catch (e: ActivityNotFoundException) {
                        //openZoomPlayStore()
                    }
                }
                if (Uri.parse(url).scheme == "zoomus") {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        return true
                    } catch (e: ActivityNotFoundException) {
                        openZoomPlayStore()
                    }
                }
                if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https") || url.toLowerCase().startsWith("file")) {
                    view.loadUrl(url)
                }
                return true
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }


            override fun onPageFinished(view: WebView, url: String) {
            }
        }
        Log.e("Live Class URL", "URL $JoinUrl")
        liveClassWebView.loadUrl(JoinUrl)


    }

    private fun showPopUpNoZoom() {

        val mDialogView =
                LayoutInflater.from(applicationContext).inflate(R.layout.no_zoom_pop_up, null)
        val mBuilder = AlertDialog.Builder(applicationContext)
                .setView(mDialogView)
        val mAlertDialog = mBuilder.show()



        mDialogView.textOkZoomPopUp.setOnClickListener {
            openZoomPlayStore()
            mAlertDialog.dismiss()
        }
    }

    private fun openZoomPlayStore() {
        val uri = Uri.parse("market://details?id=us.zoom.videomeetings&hl=en")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        }
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=us.zoom.videomeetings&hl=en")))
        }
    }
}