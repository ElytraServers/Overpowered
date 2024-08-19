package cn.taskeren.op.mc.event

import cn.taskeren.op.OP_Logger
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraftforge.event.world.WorldEvent

object WorldGameRuleManager : OP_Logger {

	const val GAME_RULE_DO_WEATHER_CYCLE: String = "doWeatherCycle"

	@SubscribeEvent
	fun onWorldLoad(e: WorldEvent.Load) {
		val gameRules = e.world.gameRules
		if(!gameRules.hasRule(GAME_RULE_DO_WEATHER_CYCLE)) {
			gameRules.addGameRule(GAME_RULE_DO_WEATHER_CYCLE, "true")
			logger.info("Added GameRule $GAME_RULE_DO_WEATHER_CYCLE to World ${e.world}")
		}
	}

	@SubscribeEvent
	fun onWorldTick(e: TickEvent.WorldTickEvent) {
		val world = e.world
		val rules = world.gameRules
		if(rules.hasRule(GAME_RULE_DO_WEATHER_CYCLE) && !rules.getGameRuleBooleanValue(GAME_RULE_DO_WEATHER_CYCLE)) {
			val info = world.worldInfo
			if(info.isRaining) {
				info.isRaining = false
				logger.debug("Set weather to clear at {}", world)
			}
		}
	}

}
