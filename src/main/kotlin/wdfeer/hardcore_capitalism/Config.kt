package wdfeer.hardcore_capitalism

import net.fabricmc.loader.api.FabricLoader
import wdfeer.hardcore_capitalism.HardcoreCapitalism.MOD_ID
import java.io.File

val defaultConfig: Config = Config(500)

data class Config(val moneyTaken: Int)

fun loadConfig(): Config? {
    val path = FabricLoader.getInstance().configDir + MOD_ID
    val lines = File(path.toString()).readLines()
    val map = lines.associate { line ->
        line.takeWhile { it != '=' }.trim() to line.takeLastWhile { it != '=' }.trim()
    }

    val moneyTaken = map["moneyTaken"]?.toIntOrNull()
    return moneyTaken?.let { Config(it) }
}

fun saveConfig(config: Config) {
    // TODO save config to config directory
}