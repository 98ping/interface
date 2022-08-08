package ltd.matrixstudios.hubcore.menus

import ltd.matrixstudios.hubcore.menus.action.CustomMenuAction
import org.bukkit.Material

class CustomMenuButton(
    var name: String,
    var lore: MutableList<String>,
    var material: Material,
    var data: Short,
    var action: CustomMenuAction
) {
}