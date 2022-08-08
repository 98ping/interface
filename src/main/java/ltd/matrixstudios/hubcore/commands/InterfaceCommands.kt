package ltd.matrixstudios.hubcore.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.hubcore.menus.CustomMenu
import ltd.matrixstudios.hubcore.menus.CustomMenuService
import ltd.matrixstudios.hubcore.utils.Chat
import org.bukkit.entity.Player

@CommandAlias("interface")
class InterfaceCommands {

    @HelpCommand
    fun helpCommand(player: Player)
    {
        player.sendMessage(Chat.format("&7&m----------------------"))
        player.sendMessage(Chat.format("&b&lInterface &fHub Core"))
        player.sendMessage(Chat.format("&7Made with &4<3 &7by &a98ping"))
        player.sendMessage(" ")
        if (player.hasPermission("interface.admin"))
        {
            player.sendMessage("&b/interface createMenu &f<name>")
            player.sendMessage("&b/interface editor")
            player.sendMessage("&b/interface setspawn")
        }
        player.sendMessage(Chat.format("&7&m----------------------"))
    }

    @Subcommand("createmenu")
    @CommandPermission("interface.admin")
    fun createMenu(player: Player, @Name("name")name: String)
    {
        val menu = CustomMenu(name.lowercase())

        CustomMenuService.saveMenu(menu)
        player.sendMessage(Chat.format("&aCreated a new menu with the id &f${name.lowercase()}"))
    }
}