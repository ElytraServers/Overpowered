@file:Suppress("EnumEntryName")

package cn.taskeren.op.mixin_plugin

import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension

enum class OP_Mixin(vararg targetModArg: OP_TargetMod, classNameArg: String? = null) {

	Insurance_BaseMetaTileEntity(OP_TargetMod.GregTech),
	CrashProof_ModularGui_Mixin(OP_TargetMod.ModularUi),

	;

	val className = "${classNameArg ?: name}_Mixin"
	val targetMod = targetModArg.toList()

	init {
		require(targetModArg.isNotEmpty()) { "Mixin must be targeting to at least 1 mod!" }
	}

	fun shouldLoad(loadedMods: Collection<OP_TargetMod>): Boolean {
		 return loadedMods.containsAll(targetMod)
	}

}

/**
 * @see gregtech.mixin.TargetedMod
 * @see com.kuba6000.mobsinfo.mixin.TargetedMod
 * @see com.gtnewhorizons.modularui.mixinplugin.TargetedMod
 */
enum class OP_TargetMod(jarNamePrefixArg: String? = null, val shouldLoadInDev: Boolean = true) {

	GregTech,
	ModularUi

	;

	val jarNamePrefix = (jarNamePrefixArg ?: name).lowercase()

	fun isMatchingJar(path: Path): Boolean {
		return path.extension == "jar" && path.nameWithoutExtension.lowercase().startsWith(jarNamePrefix)
	}
}
