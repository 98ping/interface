package ltd.matrixstudios.hubcore.inventory

import ltd.matrixstudios.hubcore.selector.SelectorItem
import ltd.matrixstudios.hubcore.selector.SelectorItemService
import ltd.matrixstudios.hubcore.services.Service
import me.lucko.helper.Events
import org.bukkit.event.player.PlayerJoinEvent

object InventoryLoadoutService : Service
{
    override fun initiate() {
        registerListeners()
    }

    fun registerListeners()
    {
        Events.subscribe(PlayerJoinEvent::class.java)
            .handler {
                val player = it.player

                player.inventory.setItem(0, SelectorItemService.selectorItem)

                player.updateInventory()
        }
    }
}