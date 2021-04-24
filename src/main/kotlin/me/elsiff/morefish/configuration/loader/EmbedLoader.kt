package me.elsiff.morefish.configuration.loader

import me.elsiff.morefish.configuration.ConfigurationValueAccessor
import me.elsiff.morefish.fishing.competition.Embed
import java.awt.Color

class EmbedLoader : CustomLoader<Embed> {
    // section:catch-fish path:embed
    override fun loadFrom(section: ConfigurationValueAccessor, path: String): Embed {
        section[path].let {
            return Embed(
                color = hexToColor(it.string("color")),
                authorName = if (it.contains("author.name")) it.string("author.name") else null,
                authorUrl = if (it.contains("author.url")) it.string("author.url") else null,
                authorImageUrl = if (it.contains("author.image-url")) it.string("author.image-url") else null,
                thumbnail = if (it.contains("thumbnail-url")) it.string("thumbnail-url") else null,
                titleText = if (it.contains("title.text")) it.string("title.text") else null,
                titleUrl = if (it.contains("title.url")) it.string("title.url") else null,
                description = if (it.contains("description")) it.string("description") else null,
                fields = it["fields"].children.map { field ->
                    Embed.Field(
                        field.string("name"),
                        field.string("value"),
                        false
                    )
                },
                image = if (it.contains("image-url")) it.string("image-url") else null,
                footerText = if (it.contains("footer.text")) it.string("footer.text") else null,
                footerIconUrl = if (it.contains("footer.icon-url")) it.string("footer.icon-url") else null
            )
        }
    }

    private fun hexToColor(colorStr: String): Color {
        return Color(
            Integer.valueOf(colorStr.substring(1, 3), 16),
            Integer.valueOf(colorStr.substring(3, 5), 16),
            Integer.valueOf(colorStr.substring(5, 7), 16)
        )
    }
}
