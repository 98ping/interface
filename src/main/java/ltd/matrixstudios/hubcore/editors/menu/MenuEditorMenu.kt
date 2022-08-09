package ltd.matrixstudios.hubcore.editors.menu

import ltd.matrixstudios.hubcore.menus.CustomMenuService
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.buttons.SimpleActionButton
import ltd.matrixstudios.hubcore.utils.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class MenuEditorMenu(val player: Player) : PaginatedMenu(18, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var i = 0
        for (menu in CustomMenuService.menus.values)
        {
            buttons[i++] = SimpleActionButton(
                Material.NETHER_STAR,
                mutableListOf(),
                menu.id,
                0
            ).setClickAction { player, clicktype, slot ->
                EditMenuMenu(player, menu).updateMenu()
            }
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Viewing All Menus"
    }
}