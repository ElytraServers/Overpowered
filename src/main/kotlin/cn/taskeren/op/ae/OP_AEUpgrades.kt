package cn.taskeren.op.ae

import appeng.api.AEApi
import appeng.api.config.Upgrades
import cn.taskeren.op.OP_Logger
import com.glodblock.github.loader.ItemAndBlockHolder as AE2FCItemAndBlockHolder

/**
 * @see cn.taskeren.op.mixin.late.AdditionalAE_Upgrades_Mixin
 */
object OP_AEUpgrades : OP_Logger {

	/**
	 * @return the instance of Programmed Upgrade, or `null` if the mixin is failed or AE2 is not available.
	 */
	@JvmStatic
	val programmedUpgrade: Upgrades? by lazy { runCatching { Upgrades.valueOf("PROGRAMMED") }.getOrNull() }

	/**
	 * @return the available OP additional upgrades.
	 */
	@JvmStatic
	val upgrades: List<Upgrades> by lazy { listOf(programmedUpgrade).filterNotNull() }

	fun init() {
		val aeApi = AEApi.instance()

		// add upgrades to the allowlist of the ae2 parts and blocks
		if(programmedUpgrade != null) {
			logger.info("Programmed Upgrade is found!")
			// ae2
			programmedUpgrade?.registerItem(aeApi.definitions().parts().iface(), 1)
			programmedUpgrade?.registerItem(aeApi.definitions().blocks().iface(), 1)
			// ae2fc
			programmedUpgrade?.registerItem(AE2FCItemAndBlockHolder.FLUID_INTERFACE.stack(), 1)
			programmedUpgrade?.registerItem(AE2FCItemAndBlockHolder.INTERFACE.stack(), 1)
		}
	}

}
