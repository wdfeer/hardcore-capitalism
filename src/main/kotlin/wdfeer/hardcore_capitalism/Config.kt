package wdfeer.hardcore_capitalism

val defaultConfig: Config = Config(500)
data class Config(val moneyTaken: Int)

fun loadConfig(): Config? {
    // TODO try loading config
    return null
}

fun saveConfig(config: Config) {
    // TODO save config to config directory
}