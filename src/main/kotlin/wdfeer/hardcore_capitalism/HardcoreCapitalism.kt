package wdfeer.hardcore_capitalism

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object HardcoreCapitalism : ModInitializer {
    private val logger = LoggerFactory.getLogger("hardcore-capitalism")

	override fun onInitialize() {
		val config = loadConfig() ?: defaultConfig.also { saveConfig(it) }

		registerOnDeathEvent(config)

		logger.info("Hardcore Capitalism loaded!")
	}
}