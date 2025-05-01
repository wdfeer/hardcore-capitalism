package wdfeer.hardcore_capitalism

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.MinecraftServer

private const val MONEY_TAKEN = 500 // todo make config

fun registerOnDeathEvent() {
    ServerPlayerEvents.AFTER_RESPAWN.register { _, newPlayer, _ ->
        val server = newPlayer.server
        val playerName = newPlayer.entityName

        subtractMoney(server, playerName)

        if (getMoney(server, playerName) < 0) {
            // TODO: ban
        }
    }
}

private fun subtractMoney(server: MinecraftServer, playerName: String) {
    CommandHelper.run(server, "numismatic balance $playerName subtract $MONEY_TAKEN")
}

private fun getMoney(server: MinecraftServer, playerName: String): Int {
    val command = "numismatic balance $playerName get"
    val string = CommandHelper.run(server, command)!!
    return string.takeLastWhile { it.isDigit() }.toInt()
}
