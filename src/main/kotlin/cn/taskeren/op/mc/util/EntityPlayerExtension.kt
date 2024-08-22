package cn.taskeren.op.mc.util

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatComponentTranslation

fun ICommandSender.sendMessage(message: String) = addChatMessage(ChatComponentText(message))
fun ICommandSender.sendTranslatedMessage(key: String, vararg format: Any?) = addChatMessage(ChatComponentTranslation(key, *format))

// #tr Command_InvalidArgument
// #en Invalid argument: %s
// #zh 无效参数：%s
fun ICommandSender.sendInvalidArgument(argName: String) = sendTranslatedMessage("Command_InvalidArgument", argName)

val EntityPlayerMP.isCreative get() = this.theItemInWorldManager.isCreative
