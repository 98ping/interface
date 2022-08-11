package ltd.matrixstudios.hubcore.cosmetics.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import ltd.matrixstudios.hubcore.cosmetics.menu.GeneralCosmeticMenu
import org.bukkit.entity.Player

class CosmeticsCommand : BaseCommand()
{
    @CommandAlias("cosmetics")
    fun cosmetics(player: Player)
    {
        GeneralCosmeticMenu(player).openMenu()
    }
}