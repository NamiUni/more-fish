package me.elsiff.morefish.hooker

import github.scarsz.discordsrv.DiscordSRV
import me.elsiff.morefish.MoreFish

class DiscordSRVHooker : PluginHooker {
    override val pluginName = "DiscordSRV"
    override var hasHooked = false
    lateinit var discordSRV: DiscordSRV

    override fun hook(plugin: MoreFish) {
        discordSRV = DiscordSRV.getPlugin()
        hasHooked = true
    }
}
