package ani.saikou.parsers

import ani.saikou.Lazier
import ani.saikou.lazyList
import ani.saikou.parsers.anime.*

object AnimeSources : WatchSources() {
    override val list: List<Lazier<BaseParser>> = lazyList(
        ::Gogo,
        ::AllAnime,
        ::NineAnime,
        ::Zoro,
        ::Tenshi,
        ::Kamyroll,
        ::AnimeKisa,
    )
    override val names: List<String> = listOf(
        "Gogo",
        "AllAnime",
        "9Anime",
        "Zoro",
        "Tenshi",
        "Kamyroll",
        "9Anime Backup",
    )
}

object HAnimeSources : WatchSources() {

    override val list: List<Lazier<BaseParser>> = lazyList(
        ::Haho,
        ::HentaiMama,
        ::HentaiStream,
        ::HentaiFF,
        ::NineAnime,
        ::Gogo,
        ::Zoro,
        ::Tenshi,
        ::Kamyroll,
        ::AllAnime,
        ::AnimeKisa,
    )

    override val names: List<String> = listOf(
        "Haho",
        "HentaiMama",
        "HentaiStream",
        "HentaiFF",
        "9Anime",
        "Gogo",
        "Zoro",
        "Tenshi",
        "Kamyroll",
        "AllAnime",
        "9Anime Backup",
    )
}
