package ltd.matrixstudios.hubcore.utils.menu

import ltd.matrixstudios.hubcore.utils.menu.pagination.PaginatedMenu
import org.bukkit.entity.Player
import java.util.*

//bare nothing in this class
object MenuController {
    var menuMap = hashMapOf<UUID, Menu>()
    var paginatedMenuMap = hashMapOf<UUID, PaginatedMenu>()


    fun addToMenuMap(player: Player, menu: Menu) {
        menuMap[player.uniqueId] = menu
    }

}