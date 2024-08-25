package cn.taskeren.op.mixin_plugin

import com.gtnewhorizon.gtnhmixins.MinecraftURLClassPath
import net.minecraft.launchwrapper.Launch
import org.apache.logging.log4j.LogManager
import org.spongepowered.asm.lib.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo
import java.io.File
import kotlin.io.path.listDirectoryEntries

class OP_MixinPlugin : IMixinConfigPlugin {

	companion object {
		val LOGGER = LogManager.getLogger()

		val MODS_DIRECTORY_PATH = File(Launch.minecraftHome, "mods/").toPath()

		fun findJarOf(mod: OP_TargetMod): File? {
			return MODS_DIRECTORY_PATH.listDirectoryEntries().firstOrNull { mod.isMatchingJar(it) }?.toFile()
		}

		fun loadJarOf(mod: OP_TargetMod): Boolean {
			try {
				val foundJar = findJarOf(mod)
				if(foundJar == null) {
					LOGGER.error("Unable to find required jar of $mod")
					return false
				}

				LOGGER.info("Attempt to add jar $foundJar to classloader")
				MinecraftURLClassPath.addJar(foundJar)
				return true
			} catch(e: Exception) {
				LOGGER.error("Error occurred while loading jar $mod", e)
				return false
			}
		}
	}

	override fun onLoad(mixinPackage: String?) {
		OP_MixinConfig.init()
	}

	override fun getMixins(): List<String> {
		val isDev = Launch.blackboard["fml.deobfuscatedEnvironment"] as Boolean

		val loadedMods = OP_TargetMod.entries.filter { isDev && it.shouldLoadInDev || loadJarOf(it) }

		OP_TargetMod.entries.forEach {
			if(loadedMods.contains(it)) {
				LOGGER.info("Found ${it.name}, integrating")
			} else {
				LOGGER.info("Unable to find ${it.name}, integrating skipped")
			}
		}

		return OP_Mixin.entries.filter { it.shouldLoad(loadedMods) }.map { it.className }
	}

	override fun getRefMapperConfig(): String? {
		return null
	}

	override fun shouldApplyMixin(targetClassName: String?, mixinClassName: String?): Boolean {
		return true
	}

	override fun acceptTargets(
		myTargets: Set<String?>?,
		otherTargets: Set<String?>?,
	) {
	}

	override fun preApply(
		s: String?,
		classNode: ClassNode?,
		s1: String?,
		iMixinInfo: IMixinInfo?,
	) {
	}

	override fun postApply(
		s: String?,
		classNode: ClassNode?,
		s1: String?,
		iMixinInfo: IMixinInfo?,
	) {
	}
}

private object OP_MixinConfig {

	fun init() {

	}

}
