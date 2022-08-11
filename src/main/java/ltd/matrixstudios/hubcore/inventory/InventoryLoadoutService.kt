package ltd.matrixstudios.hubcore.inventory

import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.selector.SelectorItem
import ltd.matrixstudios.hubcore.selector.SelectorItemService
import ltd.matrixstudios.hubcore.selector.menu.SelectorMenu
import ltd.matrixstudios.hubcore.services.Service
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.ItemBuilder
import me.lucko.helper.Events
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object InventoryLoadoutService : Service
{
    var items = hashMapOf<Int, InventoryItem>()

    override fun initiate()
    {
        loadItems()
        registerListeners()
    }

    fun loadItems()
    {

        items[SelectorItemService.selectorItemLocation] =
            InventoryItem(
                SelectorItemService.selectorItem,
                false,
                mutableListOf()
            )

        val config = InterfacePlugin.instance.config

        for (item in
            config.getConfigurationSection("items").getKeys(false)
        )
        {
            val name = config.getString("items.$item.name")
            val lore = config.getStringList("items.$item.lore")
            val material = config.getString("items.$item.item")
            val data = config.getInt("items.$item.data").toShort()
            val slot = config.getInt("items.$item.slot")
            val useAction = config.getBoolean("items.$item.useAction")
            val commands = config.getStringList("items.$item.command")

            items[slot] = InventoryItem(
                ItemBuilder.of(Material.getMaterial(material))
                    .name(Chat.format(name))
                    .setLore(lore)
                    .data(data)
                    .build(),
                useAction,
                commands
            )
        }
    }

    fun registerListeners()
    {
        Events.subscribe(PlayerQuitEvent::class.java)
            .handler {
                it.quitMessage = null
            }

        Events.subscribe(PlayerJoinEvent::class.java)
            .handler { playerJoinEvent ->
                val player = playerJoinEvent.player

                playerJoinEvent.joinMessage = null

                player.inventory.clear()

                items.forEach {
                    player.inventory.setItem(it.key, it.value.itemStack)
                }

                player.updateInventory()
            }

        Events.subscribe(PlayerInteractEvent::class.java)
            .filter {
                it.action == Action.RIGHT_CLICK_AIR || it.action == Action.RIGHT_CLICK_BLOCK
            }
            .filter { playerInteractEvent ->
                playerInteractEvent.item != null && playerInteractEvent.item.type != Material.AIR && items.values.firstOrNull {
                    it.itemStack.isSimilar(
                        playerInteractEvent.item
                    )
                } != null
            }
            .handler { event ->
                val item = items.values.firstOrNull {
                    it.itemStack.isSimilar(event.item)
                }
                if (item != null)
                {
                    if (item.usesAction)
                    {
                        item.commands.forEach {
                            event.player.performCommand(it)
                        }
                    }
                }
            }
    }
}