package wdfeer.hardcore_capitalism

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HardcoreCapitalism : ModInitializer {
    const val MOD_ID = "hardcore-capitalism"
    val logger: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        val config: Config = run {
            val loaded = loadConfig()
            if (loaded == null) {
                logger.error("Failed to load config! Creating a new default config.")
                saveConfig(defaultConfig)
                defaultConfig
            } else {
                logger.debug("Hardcore Capitalism config loaded successfully!")
                loaded
            }
        }

        registerOnDeathEvent(config)

        logger.info("Hardcore Capitalism loaded!")
    }
}