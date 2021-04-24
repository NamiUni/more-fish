package me.elsiff.morefish.fishing.competition

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel
import java.awt.Color

data class Embed(
    val color: Color?,
    val authorName: String?,
    val authorUrl: String?,
    val authorImageUrl: String?,
    val thumbnail: String?,
    val titleText: String?,
    val titleUrl: String?,
    val description: String?,
    val fields: List<Field>,
    val image: String?,
    val footerText: String?,
    val footerIconUrl: String?
) {
    data class Field(
        val name: String,
        val value: String,
        val inline: Boolean = false
    )

    fun sendMessage(channel: TextChannel, records: List<Record>) {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(color)
        embedBuilder.setAuthor(
            replace(authorName, records),
            replace(authorUrl, records),
            replace(authorImageUrl, records)
        )
        embedBuilder.setThumbnail(
            replace(thumbnail, records)
        )
        embedBuilder.setTitle(
            replace(titleText, records),
            titleUrl
        )
        embedBuilder.setDescription(
            replace(description, records)
        )
        for (field in fields) {
            val record = try {
                records[fields.indexOf(field)]
            } catch (e: IndexOutOfBoundsException) { null }

            if (record != null) {
                embedBuilder.addField(
                    replace(field.name, record),
                    replace(field.value, record),
                    field.inline
                )
            }
        }
        embedBuilder.setImage(
            replace(image, records)
        )
        embedBuilder.setFooter(
            replace(footerText, records),
            replace(footerIconUrl, records)
        )
        channel.sendMessage(embedBuilder.build()).queue()
    }

    private fun replace(string: String?, record: Record?): String? {
        if (record == null) return string
        return string
            ?.replace("%player%", "${record.fisher.name}")
            ?.replace("%length%", "${record.fish.length}")
            ?.replace("%rarity%", record.fish.type.rarity.displayName)
            ?.replace("%fish%", record.fish.type.displayName)
    }

    private fun replace(string: String?, records: List<Record>): String? {
        return string
            ?.replace("%top_player%", "${records[0].fisher.name}")
            ?.replace("%top_player_uuid%", "${records[0].fisher.uniqueId}")
            ?.replace("%top_length%", "${records[0].fish.length}")
            ?.replace("%top_rarity%", records[0].fish.type.rarity.displayName)
            ?.replace("%top_fish%", records[0].fish.type.displayName)
    }
}
