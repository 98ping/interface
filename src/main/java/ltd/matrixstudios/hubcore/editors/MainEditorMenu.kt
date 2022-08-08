package ltd.matrixstudios.hubcore.editors

import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.Menu
import org.bukkit.entity.Player

class MainEditorMenu(val player: Player) : Menu(27, player)
{
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Interface Editor"
    }
}