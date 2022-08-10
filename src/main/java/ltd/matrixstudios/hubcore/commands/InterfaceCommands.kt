package ltd.matrixstudios.hubcore.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.hubcore.editors.MainEditorMenu
import ltd.matrixstudios.hubcore.location.SpawnLocationManager
import ltd.matrixstudios.hubcore.menus.CustomMenu
import ltd.matrixstudios.hubcore.menus.CustomMenuButton
import ltd.matrixstudios.hubcore.menus.CustomMenuService
import ltd.matrixstudios.hubcore.menus.action.CustomMenuAction
import ltd.matrixstudios.hubcore.utils.Chat
import org.bukkit.Material
import org.bukkit.entity.Player

@CommandAlias("interface")
class InterfaceCommands : BaseCommand() {

    @HelpCommand
    fun helpCommand(player: Player)
    {
        player.sendMessage(Chat.format("&7&m----------------------"))
        player.sendMessage(Chat.format("&b&lInterface &fHub Core"))
        player.sendMessage(Chat.format("&7Made with &4<3 &7by &a98ping"))
        player.sendMessage(" ")
        if (player.hasPermission("interface.admin"))
        {
            player.sendMessage(Chat.format("&b/interface createMenu &f<name>"))
            player.sendMessage(Chat.format("&b/interface editor"))
            player.sendMessage(Chat.format("&b/interface setspawn"))
        }
        player.sendMessage(Chat.format("&7&m----------------------"))
    }

    @Subcommand("editor")
    @CommandPermission("interface.admin")
    fun editor(player: Player)
    {
        MainEditorMenu(player).openMenu()
    }

    @Subcommand("set-spawn")
    @CommandPermission("interface.admin")
    fun setspawn(player: Player)
    {
        SpawnLocationManager.saveSpawnLocation(player.location)
        player.sendMessage(Chat.format("&aUpdated spawn location"))
    }

    @Subcommand("createmenu")
    @CommandPermission("interface.admin")
    fun createMenu(player: Player, @Name("name")name: String)
    {
        val menu = CustomMenu(name.lowercase())

        val button = CustomMenuButton("Test Button",
            mutableListOf("test"),
            Material.DIRT,
            0,
            CustomMenuAction(
            mutableListOf("joinqueue test"), false, "")
        )

        menu.buttons[10] = button
        menu.buttons[11] = button
        menu.buttons[12] = button

        CustomMenuService.saveMenu(menu)
        player.sendMessage(Chat.format("&aCreated a new menu with the id &f${name.lowercase()}"))
    }
}