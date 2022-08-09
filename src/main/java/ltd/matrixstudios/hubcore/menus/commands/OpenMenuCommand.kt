package ltd.matrixstudios.hubcore.menus.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Name
import ltd.matrixstudios.hubcore.menus.CustomMenuOpener
import ltd.matrixstudios.hubcore.menus.CustomMenuService
import ltd.matrixstudios.hubcore.utils.Chat
import org.bukkit.entity.Player

class OpenMenuCommand : BaseCommand()
{
    @CommandAlias("openmenu")
    fun openMenu(player: Player, @Name("menu-id")menuId: String)
    {
        if (!CustomMenuService.menus.containsKey(menuId.lowercase()))
        {
            player.sendMessage(Chat.format("&cMenu does not exist!"))
            return
        }

        val menu = CustomMenuService.menus[menuId.lowercase()]!!

        CustomMenuOpener(player, menu).openMenu()
    }
}