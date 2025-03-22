package wdfeer.hardcore_capitalism

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents

private const val MONEY_TAKEN = 500 // todo make config

fun registerOnDeathEvent() {
    ServerPlayerEvents.AFTER_RESPAWN.register { _, newPlayer, _ ->
        val server = newPlayer.server

        val command = "numismatic balance ${newPlayer.entityName} subtract $MONEY_TAKEN"
        server.commandManager.dispatcher.execute(command, server.commandSource)

        // TODO: ban player if no money
    }
}