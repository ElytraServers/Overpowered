package cn.taskeren.op.forge

import cn.taskeren.op.OP_Logger
import cn.taskeren.op.gt.init.OP_GTRegistrar
import cn.taskeren.op.gt.item.OP_GeneratedItem
import cn.taskeren.op.mc.command.CommandInsurance
import cn.taskeren.op.mc.event.WorldGameRuleManager
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.common.MinecraftForge

sealed class CommonInit : OP_Logger {

	open fun preInit(e: FMLPreInitializationEvent) {
		logger.debug("Initializing Generated Items")
		OP_GeneratedItem

		logger.debug("Registering OP Machines")
		OP_GTRegistrar.registerAllMachines()
	}

	open fun init(e: FMLInitializationEvent) {
		// registering event listeners
		MinecraftForge.EVENT_BUS.register(WorldGameRuleManager)
		FMLCommonHandler.instance().bus().register(WorldGameRuleManager)
	}

	open fun postInit(e: FMLPostInitializationEvent) {
	}

	open fun serverStarting(e: FMLServerStartingEvent) {
		e.registerServerCommand(CommandInsurance)
	}

}