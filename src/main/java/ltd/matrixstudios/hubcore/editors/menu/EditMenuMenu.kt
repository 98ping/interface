package ltd.matrixstudios.hubcore.editors.menu

import ltd.matrixstudios.hubcore.menus.CustomMenu
import ltd.matrixstudios.hubcore.menus.CustomMenuService
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.conversation.InputPrompt
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.buttons.SimpleActionButton
import ltd.matrixstudios.hubcore.utils.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class EditMenuMenu(val player: Player, val menu: CustomMenu) : PaginatedMenu(18, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
       val buttons = hashMapOf<Int, Button>()

        buttons[0] = SimpleActionButton(
            Material.BOOK_AND_QUILL,
            mutableListOf(),
            Chat.format("&bEdit Menu Title"),
            0
        ).setClickAction { player, clicktype, i ->
            InputPrompt()
                .withText(Chat.format("&aEnter a new title!"))
                .withLimit(100)
                .acceptInput { player, s ->
                    menu.title = s
                    player.sendMessage(Chat.format("&aUpdated the menu title!"))

                    CustomMenuService.saveMenu(menu)
                    EditMenuMenu(player, menu).updateMenu()
                }.start(player)
        }

        buttons[1] = SimpleActionButton(
            Material.IRON_FENCE,
            mutableListOf(),
            Chat.format("&bEdit Menu Size"),
            0
        ).setClickAction { player, clicktype, i ->
            InputPrompt()
                .withText(Chat.format("&aEnter a new size!"))
                .acceptInput { player, s ->
                    var number = 18
                    try {
                        number = s.toInt()
                    } catch (e: NumberFormatException)
                    {
                        player.sendMessage(Chat.format("&cInvalid Number"))
                        return@acceptInput
                    }

                    menu.size = number
                    player.sendMessage(Chat.format("&aUpdated the menu size!"))

                    CustomMenuService.saveMenu(menu)
                    EditMenuMenu(player, menu).updateMenu()
                }.start(player)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Editing: ${menu.id}"
    }

}