package ltd.matrixstudios.hubcore.displays

import io.github.thatkawaiisam.assemble.AssembleAdapter
import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.proxy.ProxyUtils
import ltd.matrixstudios.hubcore.queues.QueuePluginService
import ltd.matrixstudios.hubcore.ranks.RankAdapter
import ltd.matrixstudios.hubcore.ranks.RankAdapterService
import ltd.matrixstudios.hubcore.utils.Chat
import org.bukkit.entity.Player

class HubcoreScoreboard : AssembleAdapter {
    override fun getTitle(player: Player?): String {
        return Chat.format(InterfacePlugin.instance.config.getString("scoreboard.title"))
    }

    override fun getLines(player: Player): MutableList<String> {
        val nonQueueLines = InterfacePlugin.instance.config.getStringList("scoreboard.lines")
        val queueLines = InterfacePlugin.instance.config.getStringList("scoreboard.linesQueued")

        val finalNonQueueLines = mutableListOf<String>()
        val finalQueueLines = mutableListOf<String>()
        val queueAdapter = QueuePluginService.queueAdapter

        if (queueAdapter == null || !queueAdapter.isQueued(player)) {

            for (line in nonQueueLines) {
                val finalLine = Chat.format(line)
                    .replace("{rank}", RankAdapterService.getRankDisplay(player))
                    .replace("{online}", ProxyUtils.getPlayerCounts().getOrDefault("ALL", 0).toString())

                finalNonQueueLines.add(finalLine)
            }
        } else {
            for (line in queueLines) {
                val finalLine = Chat.format(line)
                    .replace("{rank}", RankAdapterService.getRankDisplay(player))
                    .replace("{online}", ProxyUtils.getPlayerCounts().getOrDefault("ALL", 0).toString())
                    .replace("{queueName}", queueAdapter.getQueuedServerName(player))
                    .replace("{position}", queueAdapter.getPosition(player).toString())
                    .replace("{total}", queueAdapter.getTotalInQueue(player).toString())


                finalQueueLines.add(finalLine)
            }
        }

        return if (queueAdapter == null || !queueAdapter.isQueued(player)) {
            finalNonQueueLines
        } else finalQueueLines
    }


}