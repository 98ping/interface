package ltd.matrixstudios.hubcore.prevention

import me.lucko.helper.Events
import org.bukkit.GameMode
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent

object PreventionListeners
{
    fun load()
    {
        Events.subscribe(PlayerDropItemEvent::class.java).handler {
            it.isCancelled = true
        }

        Events.subscribe(PlayerInteractEvent::class.java).handler {
            it.isCancelled = true
        }

        Events.subscribe(BlockBreakEvent::class.java).handler {
            if (it.player.gameMode != GameMode.CREATIVE)
            {
                it.isCancelled = true
            }
        }

        Events.subscribe(BlockPlaceEvent::class.java).handler {
            if (it.player.gameMode != GameMode.CREATIVE)
            {
                it.isCancelled = true
            }
        }

        Events.subscribe(EntityDamageByEntityEvent::class.java).handler {
            it.isCancelled = true
        }

        Events.subscribe(EntityDamageEvent::class.java).handler {
            it.isCancelled = true
        }
    }
}