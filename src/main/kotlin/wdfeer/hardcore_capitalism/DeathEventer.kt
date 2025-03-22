package wdfeer.hardcore_capitalism

import com.mojang.authlib.GameProfile
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.BannedPlayerEntry
import net.minecraft.text.Text

private const val MONEY_TAKEN = 500 // todo make config

fun registerOnDeathEvent() {
    ServerPlayerEvents.AFTER_RESPAWN.register { _, newPlayer, _ ->
        val server = newPlayer.server

        val gotMoney: Boolean = run {
            val command = "numismatic subtract ${newPlayer.entityName} $MONEY_TAKEN"
            val result = server.commandManager.dispatcher.parse(command, server.commandSource)
            result.exceptions.isEmpty()
        }

        if (!gotMoney) {
            val manager = server.playerManager

            newPlayer.networkHandler.disconnect(Text.of("Try being less poor next time!"))
            manager.userBanList.add(
                BannedPlayerEntry(
                    GameProfile(
                        newPlayer.uuid,
                        newPlayer.entityName
                    )
                )
            )
        }
    }
}