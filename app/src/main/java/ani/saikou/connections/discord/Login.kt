package ani.saikou.connections.discord

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import ani.saikou.R
import ani.saikou.connections.discord.Discord.saveToken
import ani.saikou.startMainActivity

class Login : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discord)

        val webView = findViewById<WebView>(R.id.discordWebview)
        webView.apply {
            settings.javaScriptEnabled = true
            settings.databaseEnabled = true
            settings.domStorageEnabled = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (url != null && url.endsWith("/app")) {
                    webView.stopLoading()
                    webView.evaluateJavascript("""
                        (function() {
                            const wreq = webpackChunkdiscord_app.push([[Symbol()], {}, w => w])
                            webpackChunkdiscord_app.pop()
                            const token = Object.values(wreq.c).find(m => m.exports?.Z?.getToken).exports.Z.getToken();
                            return token;
                        })()
                    """.trimIndent()){
                        login(it.trim('"'))
                    }
                }
            }
        }
        webView.loadUrl("https://discord.com/login")
    }

    private fun login(token: String) {
        finish()
        saveToken(this, token)
        startMainActivity(this@Login)
    }

}
