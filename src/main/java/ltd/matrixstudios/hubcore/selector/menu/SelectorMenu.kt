package ltd.matrixstudios.hubcore.selector.menu

import ltd.matrixstudios.hubcore.selector.SelectorItem
import ltd.matrixstudios.hubcore.selector.SelectorItemService
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SelectorMenu(val player: Player) : Menu(
    SelectorItemService.selectorSize,
    player
)
{
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        SelectorItemService.items.entries.forEach {
            buttons[it.key] = SelectorButton(it.value)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
         return Chat.format(SelectorItemService.selectorTitle)
    }

    class SelectorButton(val selectorItem: SelectorItem) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return selectorItem.material
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return selectorItem.lore.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(selectorItem.displayName)
        }

        override fun getData(player: Player): Short {
            return selectorItem.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            player.performCommand(selectorItem.command)
        }

    }
}