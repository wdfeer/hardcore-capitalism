package wdfeer.hardcore_capitalism

import com.glisco.numismaticoverhaul.currency.CurrencyHelper
import net.minecraft.server.network.ServerPlayerEntity

fun getMoney(player: ServerPlayerEntity): Long
    = CurrencyHelper.getMoneyInInventory(player, false)