package ltd.matrixstudios.hubcore.utils.menu

import ltd.matrixstudios.hubcore.InterfacePlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.CompletableFuture

abstract class Menu(
    private val size: Int,
    private val player: Player
) {

    abstract fun getButtons(player: Player) : MutableMap<Int, Button>
    abstract fun getTitle(player: Player) : String

    fun getAllButtons() : MutableMap<Int, Button> {
        return getButtons(player)
    }



    fun openMenu() {
        val inventory = Bukkit.createInventory(null, size, getTitle(player))

        if (player.openInventory != null)
        {
            MenuController.menuMap.remove(player.uniqueId)
            player.closeInventory()
        }

        MenuController.addToMenuMap(player, this)

        CompletableFuture.runAsync {
            for (entry in getAllButtons())
            {
                inventory.setItem(entry.key, entry.value.constructItemStack(player))
            }
        }

        player.openInventory(inventory)

    }





}