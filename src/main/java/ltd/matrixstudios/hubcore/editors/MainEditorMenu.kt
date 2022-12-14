package ltd.matrixstudios.hubcore.editors

import ltd.matrixstudios.hubcore.editors.menu.MenuEditorMenu
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.Menu
import ltd.matrixstudios.hubcore.utils.menu.buttons.PlaceholderButton
import ltd.matrixstudios.hubcore.utils.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class MainEditorMenu(val player: Player) : Menu(27, player)
{
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[0] = SimpleActionButton(
            Material.POWERED_RAIL,
            mutableListOf(),
            Chat.format("&bMenu Editor"),
            0
        ).setClickAction { player, i, clickType ->
            MenuEditorMenu(player).updateMenu()
        }


        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Interface Editor"
    }
}