package wdfeer.hardcore_capitalism

import net.fabricmc.loader.api.FabricLoader
import wdfeer.hardcore_capitalism.HardcoreCapitalism.MOD_ID
import java.io.File

val defaultConfig: Config = Config(500)

data class Config(val moneyTaken: Int)

private fun getConfigFile(): File {
    val path = FabricLoader.getInstance().configDir.resolve(MOD_ID)
    return path.toFile()
}

fun loadConfig(): Config? {
    val file = getConfigFile()
    if (!file.exists()) return null

    val lines = file.readLines()
    val map = lines.associate { line ->
        line.takeWhile { it != '=' }.trim() to line.takeLastWhile { it != '=' }.trim()
    }

    val moneyTaken = map["moneyTaken"]?.toIntOrNull()
    return moneyTaken?.let { Config(it) }
}

fun saveConfig(config: Config) {
    val str = "moneyTaken = ${config.moneyTaken}"

    val file = getConfigFile()

    if (!file.exists())
        file.createNewFile()
    else {
        file.delete()
        file.createNewFile()
    }

    file.writeText(str)
}