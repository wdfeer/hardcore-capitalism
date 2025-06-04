package wdfeer.hardcore_capitalism

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity

fun registerOnDeathEvent(config: Config) {
    ServerPlayerEvents.AFTER_RESPAWN.register { _, newPlayer, _ ->
        execute(config, newPlayer)
    }
}

private fun execute(config: Config, player: ServerPlayerEntity) {
    val server = player.server
    val playerName = player.entityName

    subtractMoney(server, playerName, config.moneyTaken)

    val balance = getMoney(server, playerName)
    if (balance != null && balance < config.banThreshold) {
        CommandHelper.run(server, "ban $playerName \"Try being less poor next time!\"")
            ?: HardcoreCapitalism.logger.error("Failed banning $playerName")
    }
}

private fun subtractMoney(server: MinecraftServer, playerName: String, amount: Int) {
    CommandHelper.run(server, "numismatic balance $playerName subtract $amount")
        ?: HardcoreCapitalism.logger.error("Failed to subtract $amount bronze from $playerName")
}

private fun getMoney(server: MinecraftServer, playerName: String): Int? {
    val command = "numismatic balance $playerName get"
    val string = CommandHelper.run(server, command)
    return if (string == null) {
        HardcoreCapitalism.logger.error("Failed to inquire $playerName's balance through \"$command\"")
        null
    } else
        string.takeLastWhile { it != ' ' }.toInt()
}
