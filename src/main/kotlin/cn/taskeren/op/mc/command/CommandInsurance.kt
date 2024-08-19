package cn.taskeren.op.mc.command

import cn.taskeren.op.insurance.InsuranceManager
import cn.taskeren.op.mc.util.sendInvalidArgument
import cn.taskeren.op.mc.util.sendMessage
import cn.taskeren.op.mc.util.sendTranslatedMessage
import gregtech.api.GregTech_API
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP

object CommandInsurance : CommandBase() {

	override fun getCommandName(): String = "insurance"

	override fun getCommandUsage(sender: ICommandSender?): String = "/insurance help"

	override fun processCommand(
		sender: ICommandSender,
		args: Array<String>
	) {
		val mArgs = args.toMutableList()
		when(mArgs.removeFirstOrNull()) {
			null, "help" -> {
				// #tr Insurance_Command_Help.0
				// #en Insurance Command Help
				// #zh 保险指令帮助
				sender.sendTranslatedMessage("Insurance_Command_Help.0")
				// #tr Insurance_Command_Help.1
				// #en /insurance list - List all the exploded machines recorded in the insurance.
				// #zh /insurance list - 列出所有记录在保险中的爆炸的机器。
				sender.sendTranslatedMessage("Insurance_Command_Help.1")
				// #tr Insurance_Command_Help.2
				// #en /insurance refund <id> - Make a refund request for the machine with id.
				// #zh /insurance refund <id> - 发起一个ID为id的机器的保险赔付。
				sender.sendTranslatedMessage("Insurance_Command_Help.2")
				// #tr Insurance_Command_Help.3
				// #en You'll need to have some emeralds to pay for the insurance.
				// #zh 你需要一些绿宝石来购买保险。
				sender.sendTranslatedMessage("Insurance_Command_Help.3")
			}
			"list" -> { // list the exploded machines of mine
				if(sender is EntityPlayer) {
					val list = InsuranceManager.getMyExplodedMachines(sender.uniqueID)
					// #tr Insurance_Message_ExplodedMachineList
					// #en Your insurance covers %s machines.
					// #zh 你的保险覆盖了%s台机器。
					sender.sendTranslatedMessage("Insurance_Message_ExplodedMachineList", list.size)
					list.forEach { metaId ->
						sender.sendMessage("- $metaId (${GregTech_API.METATILEENTITIES[metaId]?.metaName ?: "Unknown Name"})")
					}
				} else {
					sender.sendMessage("Console is not allowed!")
				}
			}
			"refund" -> {
				if(sender is EntityPlayerMP) {
					val metaId = mArgs.removeFirstOrNull()?.toIntOrNull()
					if(metaId == null) return sender.sendInvalidArgument("mId")

					InsuranceManager.refundExplodedMachine(sender, metaId)
				} else {
					sender.sendMessage("Console is not allowed!")
				}
			}
		}
	}
}
