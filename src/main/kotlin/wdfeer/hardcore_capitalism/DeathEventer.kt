package wdfeer.hardcore_capitalism

import com.glisco.numismaticoverhaul.currency.CurrencyHelper
import com.mojang.authlib.GameProfile
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.BannedPlayerEntry

private const val MONEY_TAKEN = 500L // todo make config

fun registerOnDeathEvent() {
    ServerPlayerEvents.AFTER_RESPAWN.register { oldPlayer, newPlayer, alive ->
        val gotMoney = CurrencyHelper.deductFromInventory(newPlayer, MONEY_TAKEN)
        if (!gotMoney) {
            val manager = newPlayer.server.playerManager
            manager.remove(newPlayer)
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