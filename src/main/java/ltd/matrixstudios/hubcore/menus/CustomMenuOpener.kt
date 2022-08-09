package ltd.matrixstudios.hubcore.menus

import ltd.matrixstudios.hubcore.proxy.ProxyUtils
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class CustomMenuOpener(val player: Player, val customMenu: CustomMenu) : Menu(customMenu.size, player)
{
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        customMenu.buttons.forEach {
            buttons[it.key] = CustomMenuActualButton(it.value)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return Chat.format(customMenu.title)
    }

    class CustomMenuActualButton(val customMenuButton: CustomMenuButton) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return customMenuButton.material
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return customMenuButton.lore.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(customMenuButton.name)
        }

        override fun getData(player: Player): Short {
            return customMenuButton.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            val action = customMenuButton.action

            if (action.actions.isNotEmpty())
            {
                action.actions.forEach {
                    player.performCommand(it)
                }
            }

            if (action.shouldSendToDiffServer)
            {
                ProxyUtils.send(player, action.otherServer)
            }
        }

    }
}