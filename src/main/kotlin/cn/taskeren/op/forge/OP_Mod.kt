@file:Suppress("unused")

package cn.taskeren.op.forge

import cn.taskeren.op.OP
import cn.taskeren.op.OP.t
import cn.taskeren.op.OP_Logger
import cn.taskeren.op.Tags
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent

/**
 * This is the Mod class of Overpowered.
 *
 * It should not be modified, and things should be added to either [CommonInit] or [CommonInit].
 *
 * Look at [OP] where you may find something you need.
 */
@Mod(
	modid = "Overpowered",
	modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
	name = "Overpowered",
	version = Tags.VERSION,
	dependencies = "required-after:gregtech;" +
		"required-after:miscutils;" + // gt++
		"required-after:tectech;" + // TecTech
		"required-after:dreamcraft;"
)
object OP_Mod : OP_Logger {

	@SidedProxy(
		serverSide = "cn.taskeren.op.forge.CommonInit",
		clientSide = "cn.taskeren.op.forge.ClientInit",
	)
	lateinit var proxy: CommonInit

	init {
		if(OP.dev) {
			// #tr Overpowered_Author_Taskeren
			// #en &6&lTaskeren
			// #zh &6&lTaskeren（楓）
			// #tw &6&lTaskeren（楓）
			logger.info("Overpowering " + t("Overpowered_Author_Taskeren") + " !")
		}
	}

	@Mod.EventHandler
	fun preInit(e: FMLPreInitializationEvent) {
		proxy.preInit(e)
	}

	@Mod.EventHandler
	fun init(e: FMLInitializationEvent) {
		proxy.init(e)
	}

	@Mod.EventHandler
	fun postInit(e: FMLPostInitializationEvent) {
		proxy.postInit(e)
	}

	@Mod.EventHandler
	fun serverStarting(e: FMLServerStartingEvent) {
		proxy.serverStarting(e)
	}

}
