package ltd.matrixstudios.hubcore.selector

import org.bukkit.Material

class SelectorItem(
    var displayName: String,
    var lore: MutableList<String>,
    var material: Material,
    var data: Short,
    var slot: Int,
    var command: String
) {
}