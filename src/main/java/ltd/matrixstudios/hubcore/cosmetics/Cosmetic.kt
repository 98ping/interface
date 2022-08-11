package ltd.matrixstudios.hubcore.cosmetics

import org.bukkit.Color
import org.bukkit.Material


class Cosmetic(
    var id: String,
    var displayName: String,
    var color: Color,
    var displayItem: Material,
    var type: CosmeticType,
    var data: Short,
    var permission: String
) {
}