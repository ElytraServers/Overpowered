@file:Suppress("SpellCheckingInspection")

package cn.taskeren.op.mc.command

import cn.taskeren.op.mc.util.sendMessage
import cofh.lib.util.helpers.EntityHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.command.WrongUsageException
import net.minecraft.server.MinecraftServer
import net.minecraftforge.common.DimensionManager

object CommandOpTp : CommandBase() {

	override fun getCommandName(): String = "optp"

	// #tr commands.op_tp.usage
	// #en /optp <x> <y> <z> [dim = current dimesion] [player = myself] or /optp <dest_player> [player = myself]
	// #zh /optp <x> <y> <z> [dim = 当前维度] [player = 自身] or /optp <dest_player> [player = 自身]
	override fun getCommandUsage(sender: ICommandSender?): String = "commands.op_tp.usage"

	override fun getRequiredPermissionLevel(): Int = 2

	override fun processCommand(
		sender: ICommandSender,
		argsRaw: Array<String>,
	) {
		val args = argsRaw.toMutableList()
		val self = getCommandSenderAsPlayer(sender)
		try {
			when(args.size) {
				3, 4, 5 -> {
					val x = args[0].toDouble()
					val y = args[1].toDouble()
					val z = args[2].toDouble()
					val dim = args.getOrNull(3)?.toInt()
					val target = args.getOrNull(4)?.let { getPlayer(sender, it) } ?: self

					if(dim != null) {
						if(DimensionManager.isDimensionRegistered(dim)) {
							EntityHelper.transferEntityToDimension(
								target,
								dim,
								MinecraftServer.getServer().configurationManager
							)
						} else {
							sender.sendMessage("Dimension $dim is not registered!")
							return
						}
					}

					target.setPositionAndUpdate(x, y, z)
					return
				}

				1, 2 -> {
					val dest = getPlayer(sender, args[0])
					val target = args.getOrNull(1)?.let { getPlayer(sender, it) } ?: self

					if(dest.dimension != target.dimension) {
						EntityHelper.transferEntityToDimension(
							target,
							dest.dimension,
							MinecraftServer.getServer().configurationManager
						)
					}

					target.setPositionAndUpdate(dest.posX, dest.posY, dest.posZ)
					return
				}

				else -> WrongUsageException("commands.op_tp.usage")
			}
		} catch(nfe: NumberFormatException) {
			sender.sendMessage("Argument: not a valid number!")
		}
	}
}
