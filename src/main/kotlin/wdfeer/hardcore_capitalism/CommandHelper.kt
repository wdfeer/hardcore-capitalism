package wdfeer.hardcore_capitalism

import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.CommandOutput
import net.minecraft.text.Text

object CommandHelper {
    fun run(server: MinecraftServer, command: String): String? {
        val output = object : CommandOutput {
            var result: String? = null

            override fun sendMessage(message: Text?) {
                result = message?.string
            }

            override fun shouldReceiveFeedback(): Boolean = true
            override fun shouldTrackOutput(): Boolean = true
            override fun shouldBroadcastConsoleToOps(): Boolean = false
        }

        val source = server.commandSource.withOutput(output)

        server.commandManager.dispatcher.execute(command, source)

        return output.result
    }
}