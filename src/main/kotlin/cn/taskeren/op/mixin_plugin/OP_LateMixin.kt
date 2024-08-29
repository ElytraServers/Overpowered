@file:Suppress("SpellCheckingInspection")

package cn.taskeren.op.mixin_plugin

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader
import com.gtnewhorizon.gtnhmixins.LateMixin
import net.minecraft.launchwrapper.Launch
import org.apache.logging.log4j.LogManager

@LateMixin
class OP_LateMixin : ILateMixinLoader {

	val logger = LogManager.getLogger("OP:LateMixin")
	val dev = Launch.blackboard["fml.deobfuscatedEnvironment"] as Boolean

	override fun getMixinConfig(): String? {
		return "mixins.Overpowered.late.json"
	}

	override fun getMixins(loadedMods: Set<String>): List<String> {
		logger.info("Initializing Late Mixins")
		if(dev) {
			loadedMods.forEach { logger.info("Loaded Mod: {}", it) }
		}

		val mixinClasses = mutableListOf<String>()
		if("gregtech" in loadedMods) {
			mixinClasses += "Insurance_BaseMetaTileEntity_Mixin"
			mixinClasses += "GTApi_GregTech_API_Mixin"

			mixinClasses += "VoltageChanceBonus_GT_ParallelHelper_Mixin"
		}
		if("modularui" in loadedMods) {
			mixinClasses += "CrashProof_ModularGui_Mixin"
		}
		if("appliedenergistics2" in loadedMods) {
			mixinClasses += "ProInterface_DualityInterface_Mixin"
			mixinClasses += "AdditionalAE_Upgrades_Mixin"
			mixinClasses += "AdditionalAE_UpgradeInventory_Mixin"

			if("AWWayofTime" in loadedMods) {
				mixinClasses += "AEBloodMagicCompat_DualityInterface_Mixin"
			}
		}

		mixinClasses.forEach { logger.info("Applied Mixin: {}", it) }
		return mixinClasses
	}
}
