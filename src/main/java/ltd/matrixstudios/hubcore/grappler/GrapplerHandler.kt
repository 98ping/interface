package ltd.matrixstudios.hubcore.grappler

import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.ItemBuilder
import me.lucko.helper.Events
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

object GrapplerHandler {
    val item = ItemBuilder.of(Material.FISHING_ROD)
        .name(Chat.format(InterfacePlugin.instance.config.getString("grappler.name")!!))
        .setLore(InterfacePlugin.instance.config.getStringList("grappler.lore").map { Chat.format(it) })
        .build()


    fun registerEvents() {
        Events.subscribe(PlayerJoinEvent::class.java)
            .handler {
                if (InterfacePlugin.instance.config.getBoolean("grappler.enabled")) {
                    it.player.inventory.setItem(InterfacePlugin.instance.config.getInt("grappler.slot"), item)
                    it.player.updateInventory()
                }
            }

        Events.subscribe(PlayerFishEvent::class.java)
            .handler {

                if (it.player.itemInHand == null) return@handler

                if (it.player.itemInHand.isSimilar(item))
                {
                    val target: Location = it.hook.location
                    val pLoc: Location = it.player.location
                    var vector = target.toVector().subtract(pLoc.toVector())
                    vector = vector.multiply(0.5).setY(0.7)
                    it.player.velocity = vector
                }
            }
    }
}