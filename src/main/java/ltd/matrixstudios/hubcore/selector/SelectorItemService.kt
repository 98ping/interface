package ltd.matrixstudios.hubcore.selector

import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.selector.menu.SelectorMenu
import ltd.matrixstudios.hubcore.services.Service
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.ItemBuilder
import me.lucko.helper.Events
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.logging.Level
import kotlin.properties.Delegates

object SelectorItemService : Service
{

    var items = mutableMapOf<Int, SelectorItem>()

    var selectorTitle: String = "Server Selector"
    var selectorSize: Int = 18
    var command: String = ""
    lateinit var selectorItem: ItemStack
    //in the inventory
    var selectorItemLocation by Delegates.notNull<Int>()

    fun loadMenuValues()
    {
        val config = InterfacePlugin.instance.config

        selectorTitle = config.getString("selector.title")
        selectorSize = config.getInt("selector.size")

        val selectorItemName = config.getString("selector.clickable.name")
        val selectorItemLore = config.getStringList("selector.clickable.lore")
        val selectorItemMaterial = config.getString("selector.clickable.material")
        val selectorItemData = config.getInt("selector.clickable.data")

        if (config.getString("selector.command") != "")
        {
            command = config.getString("selector.command")
        }

        selectorItemLocation = config.getInt("selector.invSlot")

        selectorItem = ItemBuilder.of(
            Material.getMaterial(selectorItemMaterial.uppercase())
        ).data(selectorItemData.toShort())
            .name(Chat.format(selectorItemName))
            .setLore(selectorItemLore)
            .build()

    }

    fun loadItems()
    {
        val startingMs = System.currentTimeMillis()
        val config = InterfacePlugin.instance.config
        for (key in config.getConfigurationSection(
            "selector.items"
        ).getKeys(
            false
        ))
        {
            //menu info
            val slot = config.getInt("selector.items.$key.slot")
            val displayName = config.getString("selector.items.$key.displayName")
            val lore = config.getStringList("selector.items.$key.lore")

            //material info
            val material = config.getString("selector.items.$key.material")
            val data = config.getInt("selector.items.$key.data")

            //on execute
            val command = config.getString("selector.items.$key.command")

            items[slot] = SelectorItem(
                displayName,
                lore,
                Material.getMaterial(material.toUpperCase()),
                data.toShort(),
                slot,
                command
            )

        }
        InterfacePlugin.instance.logger.log(Level.INFO, "[items] Selector loaded in ${System.currentTimeMillis().minus(startingMs)} milliseconds")
    }

    fun setupListeners()
    {
        Events.subscribe(PlayerInteractEvent::class.java)
            .filter {
                it.action == Action.RIGHT_CLICK_AIR || it.action == Action.RIGHT_CLICK_BLOCK
            }
            .filter {
                it.item != null && it.item.type != Material.AIR && it.item.isSimilar(selectorItem)
            }
            .handler {
                if (command == "") {
                    SelectorMenu(it.player).openMenu()
                } else {
                    it.player.performCommand(command)
                }
            }
    }

    override fun initiate()
    {
        loadMenuValues()
        loadItems()
        setupListeners()
    }


}