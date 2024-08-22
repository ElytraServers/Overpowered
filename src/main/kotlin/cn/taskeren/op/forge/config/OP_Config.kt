package cn.taskeren.op.forge.config

import net.minecraftforge.common.config.Configuration
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object OP_Config {

	lateinit var config: Configuration

	var allowRefundByCommand by boolean("allow-refund-by-command", "insurance", false, "Set to true if allows refund by command. The command still requires permission of level 4.")

	fun init(config: Configuration) {
		this.config = config
	}

	private fun boolean(name: String, category: String, defaultValue: Boolean, comment: String): ReadWriteProperty<Any, Boolean> {
		return object : ReadWriteProperty<Any, Boolean> {
			override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
				return config.getBoolean(name, category, defaultValue, comment)
			}

			override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
				config.getCategory(category)[name]?.set(value)
				config.save()
			}
		}
	}

}
