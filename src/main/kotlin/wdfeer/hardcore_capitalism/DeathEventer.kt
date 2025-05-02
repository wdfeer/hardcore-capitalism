package wdfeer.hardcore_capitalism

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.MinecraftServer

fun registerOnDeathEvent(config: Config) {
    ServerPlayerEvents.AFTER_RESPAWN.register { _, newPlayer, _ ->
        val server = newPlayer.server
        val playerName = newPlayer.entityName

        subtractMoney(server, playerName, config.moneyTaken)

        if (getMoney(server, playerName) < config.banThreshold) {
            CommandHelper.run(server, "ban $playerName \"Try being less poor next time!\"")
        }
    }
}

private fun subtractMoney(server: MinecraftServer, playerName: String, amount: Int) {
    CommandHelper.run(server, "numismatic balance $playerName subtract $amount")
}

private fun getMoney(server: MinecraftServer, playerName: String): Int {
    val command = "numismatic balance $playerName get"
    val string = CommandHelper.run(server, command)!!
    return string.takeLastWhile { it != ' ' }.toInt()
}
