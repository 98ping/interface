package ltd.matrixstudios.hubcore.prevention

import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.location.SpawnLocationManager
import me.lucko.helper.Events
import org.bukkit.GameMode
import org.bukkit.entity.EntityType
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerMoveEvent

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

        Events.subscribe(PlayerMoveEvent::class.java)
            .filter { it.to.y == 0.0 }
            .handler {
                if (SpawnLocationManager.spawnLocation != null)
                {
                    it.player.teleport(SpawnLocationManager.spawnLocation)
                }
            }

        Events.subscribe(EntityDamageEvent::class.java)
            .handler {
                if (it.cause == EntityDamageEvent.DamageCause.DROWNING)
                {
                    it.isCancelled = true
                }
            }

        Events.subscribe(BlockBreakEvent::class.java).handler {
            if (it.player.gameMode != GameMode.CREATIVE)
            {
                it.isCancelled = true
            }
        }

        Events.subscribe(EntitySpawnEvent::class.java)
            .handler {
                if (it.entityType != EntityType.ARMOR_STAND
                    &&
                    it.entityType != EntityType.FISHING_HOOK
                    &&
                    it.entityType != EntityType.PLAYER)
                {
                    it.isCancelled = true
                }

            }

        Events.subscribe(PlayerDeathEvent::class.java)
            .handler {
                if (SpawnLocationManager.spawnLocation != null)
                {
                    it.entity.teleport(SpawnLocationManager.spawnLocation)
                }
            }

        Events.subscribe(BlockPlaceEvent::class.java).handler {
            if (it.player.gameMode != GameMode.CREATIVE)
            {
                it.isCancelled = true
            }
        }

        Events.subscribe(EntityDamageByEntityEvent::class.java).handler {
            if (!InterfacePlugin.instance.config.getBoolean("listeners.allowDamageFromPlayer"))
            {
                it.isCancelled = true
            }
        }

        Events.subscribe(EntityDamageEvent::class.java).handler {
            it.isCancelled = true
        }

        Events.subscribe(FoodLevelChangeEvent::class.java).handler {
            it.isCancelled = true
        }
    }
}