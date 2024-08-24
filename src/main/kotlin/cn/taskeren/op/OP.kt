package cn.taskeren.op

import gregtech.api.GregTech_API
import gregtech.api.enums.ItemList
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.launchwrapper.Launch
import net.minecraft.util.StatCollector

object OP : OP_Logger {

	fun t(key: String, vararg format: Any?): String {
		return StatCollector.translateToLocalFormatted(key, *format)
	}

	@JvmStatic
	@get:JvmName("isDev")
	var dev = System.getenv("DEV") == "true" || Launch.blackboard["fml.deobfuscatedEnvironment"] as Boolean

	val propertyDumpStackTraceOnMachineExplode get() = System.getProperty("op.dumpStackTraceOnMachineExplode") == "true"

	val gtMachineBlock get() = GregTech_API.sBlockMachines
	val livingBioChip get() = ItemList.Circuit_Parts_Chip_Bioware.get(1)

	fun isMachineBlock(block: Block) = block == gtMachineBlock
	fun isMachineBlock(item: Item) = isMachineBlock(Block.getBlockFromItem(item))

}

internal fun translated(key: String, vararg format: Any?): String = OP.t(key, *format)
