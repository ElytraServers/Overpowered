package cn.taskeren.op.mc.command

import cn.taskeren.op.OP_Logger
import cn.taskeren.op.gt.utils.GTApi
import cn.taskeren.op.gt.utils.InfoDataBuilder
import cn.taskeren.op.kubatech.api.helpers.ReflectionHelper
import cn.taskeren.op.mc.util.plainChat
import cn.taskeren.op.mc.util.sendMessage
import cn.taskeren.op.mc.util.translatedChat
import cn.taskeren.op.mc.util.withBlue
import cn.taskeren.op.mc.util.withDarkGray
import cn.taskeren.op.mc.util.withGold
import cn.taskeren.op.mc.util.withGray
import gregtech.api.GregTechAPI
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GTPPMultiBlockBase
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.launchwrapper.Launch

/**
 * THE DEEPEST DARK ARTS OF OVERPOWERED!
 */
object CommandOverpoweredDarkArts : CommandBase(), OP_Logger {

	val tasks = mutableMapOf<String, (sender: ICommandSender, args: MutableList<String>) -> Unit>()

	/**
	 * @return list of [IMetaTileEntity] whose class extends or implements class [c].
	 */
	private fun getMachinesByClass(c: Class<*>): List<IMetaTileEntity>? {
		return if(IMetaTileEntity::class.java.isAssignableFrom(c)) {
			GregTechAPI.METATILEENTITIES.filterNotNull().filter {
				c.isAssignableFrom(it::class.java)
			}
		} else {
			null
		}
	}

	/**
	 * List all the classes extends or implements class [c], and print them to the sender [p].
	 * The class should be the parent class of machines, because this method is designed for printing machines.
	 */
	private fun logMachineClass(p: ICommandSender, c: Class<*>) {
		if(IMetaTileEntity::class.java.isAssignableFrom(c)) {
			val machines = getMachinesByClass(c)
			if(machines == null) {
				p.sendMessage("Unreachable! {} should be IMetaTileEntity, which is checked in this if condition!")
			} else if(machines.size == 1) {
				val machine = machines[0]

				// #tr OP_DarkArts_LogMachine_Single
				// #en - Machine %s %s: %s
				// #zh - 机器 %s %s: %s
				p.addChatMessage(
					translatedChat(
						"OP_DarkArts_LogMachine_Single",
						translatedChat(GTApi.getMachineUnlocalizedName(machine)).withGold(),
						plainChat("(${machine.metaName})").withDarkGray(),
						plainChat(c.name).withBlue(),
					).withGray()
				)
			} else {

				// #tr OP_DarkArts_LogMachine_Multi_Header
				// #en - Multi Machine Class: %s
				// #zh - 多机器 Class: %s
				p.addChatMessage(
					translatedChat(
						"OP_DarkArts_LogMachine_Multi_Header",
						plainChat(c.name).withBlue(),
					).withGray()
				)

				machines.forEach { machine ->

					// #tr OP_DarkArts_LogMachine_Multi_Entry
					// #en * %s %s
					// #zh * %s %s
					p.addChatMessage(
						translatedChat(
							"OP_DarkArts_LogMachine_Multi_Entry",
							translatedChat(GTApi.getMachineUnlocalizedName(machine)).withGold(),
							plainChat("(${machine.metaName})").withDarkGray(),
						).withGray()
					)
				}
			}
		} else {

			// #tr OP_DarkArts_LogMachine_NotMachine
			// #en - Non-Machine: %s
			// #zh - 非机器：%s
			p.addChatMessage(
				translatedChat(
					"OP_DarkArts_LogMachine_NotMachine",
					plainChat(c.name).withBlue(),
				)
			)
		}
	}

	init {
		tasks["listVoltageBonus"] = { sender, args ->
			val subClasses = DarkArts.getAllSubClasses<GTPPMultiBlockBase<*>>()
			logger.info("Subclasses ({})", subClasses.size)
			subClasses.forEach {
				logMachineClass(sender, it)
			}
		}
	}

	override fun getCommandName(): String = "op-DarkArts".lowercase()

	override fun getCommandUsage(sender: ICommandSender?): String = "commands.op-utils.usage"

	override fun processCommand(
		sender: ICommandSender,
		argsRaw: Array<String>,
	) {
		val args = argsRaw.toMutableList()

		val taskName = args.removeFirstOrNull()
		val task = tasks[taskName] ?: return sender.sendMessage("Wrong Usage: task $taskName was not found!")

		runCatching {
			task(sender, args)
		}.onSuccess {
			sender.sendMessage("${InfoDataBuilder.GREEN}Success! ${InfoDataBuilder.GRAY}See console!")
		}.onFailure {
			logger.error("Exception thrown when running $taskName", it)
			sender.sendMessage("${InfoDataBuilder.RED}Failed! ${InfoDataBuilder.GRAY}See console!")
		}

	}

	override fun addTabCompletionOptions(
		sender: ICommandSender,
		argsRaw: Array<String>,
	): List<String> {
		return if(argsRaw.firstOrNull()?.isEmpty() == true) tasks.keys.toList() else emptyList()
	}

}

/**
 * The deepest dark arts!
 */
private object DarkArts {

	val cachedClasses
		get() = ReflectionHelper.getField<java.util.Map<String, Class<*>>>(Launch.classLoader, "cachedClasses")

	inline fun <reified T> getAllSubClasses(): List<Class<*>> {
		return cachedClasses.values().filter { T::class.java.isAssignableFrom(it) }
	}

}
