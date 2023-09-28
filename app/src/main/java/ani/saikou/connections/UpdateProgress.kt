package ani.saikou.connections

import ani.saikou.R
import ani.saikou.Refresh
import ani.saikou.connections.anilist.Anilist
import ani.saikou.connections.mal.MAL
import ani.saikou.currContext
import ani.saikou.media.Media
import ani.saikou.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun updateProgress(media: Media, number: String) {
    if (Anilist.userid != null) {
        CoroutineScope(Dispatchers.IO).launch {
            val a = number.toFloatOrNull()?.roundToInt()
            if (a != media.userProgress) {
                Anilist.mutation.editList(
                    media.id,
                    a,
                    status = if (media.userStatus == "REPEATING") media.userStatus else "CURRENT"
                )
                MAL.query.editList(
                    media.idMAL,
                    media.anime != null,
                    a, null,
                    if (media.userStatus == "REPEATING") media.userStatus!! else "CURRENT"
                )
                toast(currContext()?.getString(R.string.setting_progress, a))
            }
            media.userProgress = a
            Refresh.all()
        }
    } else {
        toast(currContext()?.getString(R.string.login_anilist_account))
    }
}