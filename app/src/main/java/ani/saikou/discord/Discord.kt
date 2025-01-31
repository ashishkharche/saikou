package ani.saikou.discord

import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import ani.saikou.R
import ani.saikou.discord.serializers.User
import ani.saikou.toast
import ani.saikou.tryWith
import ani.saikou.tryWithSuspend
import kotlinx.coroutines.Dispatchers
import java.io.File

object Discord {

    fun loginIntent(context: Context) {
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
    }

    var token: String? = null
    var userid: String? = null
    var avatar: String? = null

    private const val TOKEN = "discord_token"

    fun getSavedToken(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        token = sharedPref.getString(TOKEN, null)
        return token != null
    }

    fun saveToken(context: Context, token: String) {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        sharedPref.edit {
            putString(TOKEN, token)
            commit()
        }
    }

    fun removeSavedToken(context: Context) {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        sharedPref.edit {
            remove(TOKEN)
            commit()
        }

        tryWith(true) {
            val dir = File(context.filesDir?.parentFile, "app_webview")
            if (dir.deleteRecursively())
                toast(context.getString(R.string.discord_logout_success))
        }
    }

    private var rpc : RPC? = null
    suspend fun getUserData() = tryWithSuspend(true) {
        if(rpc==null) {
            val rpc = RPC(token!!, Dispatchers.IO).also { rpc = it }
            val user: User = rpc.getUserData()
            userid = user.username
            avatar = user.userAvatar()
            rpc.close()
            true
        } else true
    } ?: false

}