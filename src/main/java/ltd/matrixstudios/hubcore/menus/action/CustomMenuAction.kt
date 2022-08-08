package ltd.matrixstudios.hubcore.menus.action

class CustomMenuAction(
    var actions: MutableList<String>,
    var shouldSendToDiffServer: Boolean,
    var otherServer: String
) {
}