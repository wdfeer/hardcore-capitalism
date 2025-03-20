package wdfeer.hardcore_capitalism

import com.glisco.numismaticoverhaul.currency.CurrencyHelper
import com.mojang.authlib.GameProfile
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.BannedPlayerEntry
import net.minecraft.text.Text

private const val MONEY_TAKEN = 500L // todo make config

fun registerOnDeathEvent() {
    ServerPlayerEvents.AFTER_RESPAWN.register { _, newPlayer, _ ->
        val gotMoney = CurrencyHelper.deductFromInventory(newPlayer, MONEY_TAKEN) // todo: this doesn't check the 'purse', only coins in inventory
        if (!gotMoney) {
            val manager = newPlayer.server.playerManager

            newPlayer.networkHandler.disconnect(Text.of("Try being less poor next time!"))
            manager.userBanList.add(
                BannedPlayerEntry(
                    GameProfile(
                        newPlayer.uuid,
                        newPlayer.name.string
                    )
                )
            )
        }
    }
}