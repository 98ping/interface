package ltd.matrixstudios.hubcore.utils.menu.navigation

import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PageNavigationMenu(val player: Player, val maxPages: Int, val menu: PaginatedMenu) : PaginatedMenu(9, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (int in 1 until maxPages + 1)
        {
            buttons[index++] = PageNavigationButton(int, menu)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Jump to a Page"
    }

    class PageNavigationButton(val page: Int, val menu: PaginatedMenu) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.BOOK
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return arrayListOf()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&eJump to Page $page")
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            menu.page = page
            menu.updateMenu()
        }

    }
}