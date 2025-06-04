package wdfeer.hardcore_capitalism

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.world.GameMode

fun registerOnDeathEvent(config: Config) {
    ServerLivingEntityEvents.AFTER_DEATH.register { entity, source ->
        if (entity is ServerPlayerEntity)
            onDeath(config, entity)
    }
}

private fun onDeath(config: Config, player: ServerPlayerEntity) {
    val server = player.server
    val playerName = player.entityName

    subtractMoney(server, playerName, config.moneyTaken)

    val balance = getMoney(server, playerName)
    if (balance != null && balance < config.banThreshold) {
        if (server.isDedicated) {
            CommandHelper.run(server, "ban $playerName \"Try being less poor next time!\"")
                ?: HardcoreCapitalism.logger.error("Failed banning $playerName")
        } else {
            player.changeGameMode(GameMode.SPECTATOR)
            player.sendMessageToClient(Text.of("Try being less poor next time!"), true)
        }
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
