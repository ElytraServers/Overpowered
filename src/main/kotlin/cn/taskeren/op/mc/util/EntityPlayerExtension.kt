package cn.taskeren.op.mc.util

import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.event.ClickEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent

fun ICommandSender.sendMessage(message: String) = addChatMessage(ChatComponentText(message))
fun ICommandSender.sendTranslatedMessage(key: String, vararg format: Any?) = addChatMessage(ChatComponentTranslation(key, *format))

// #tr Command_InvalidArgument
// #en Invalid argument: %s
// #zh 无效参数：%s
fun ICommandSender.sendInvalidArgument(argName: String) = sendTranslatedMessage("Command_InvalidArgument", argName)

val EntityPlayerMP.isCreative get() = this.theItemInWorldManager.isCreative

fun IChatComponent.flatten() = formattedText

fun IChatComponent.withChatStyle(block: ChatStyle.() -> Unit) = apply { chatStyle.apply(block) }

fun plainChat(message: String) = ChatComponentText(message)
fun translatedChat(translateKey: String, vararg format: Any) = ChatComponentTranslation(translateKey, *format)

fun IChatComponent.withBlack() = withChatStyle { setColor(EnumChatFormatting.BLACK) }
fun IChatComponent.withDarkBlue() = withChatStyle { setColor(EnumChatFormatting.DARK_BLUE) }
fun IChatComponent.withDarkGreen() = withChatStyle { setColor(EnumChatFormatting.DARK_GREEN) }
fun IChatComponent.withDarkAqua() = withChatStyle { setColor(EnumChatFormatting.DARK_AQUA) }
fun IChatComponent.withDarkRed() = withChatStyle { setColor(EnumChatFormatting.DARK_RED) }
fun IChatComponent.withDarkPurple() = withChatStyle { setColor(EnumChatFormatting.DARK_PURPLE) }
fun IChatComponent.withGold() = withChatStyle { setColor(EnumChatFormatting.GOLD) }
fun IChatComponent.withGray() = withChatStyle { setColor(EnumChatFormatting.GRAY) }
fun IChatComponent.withDarkGray() = withChatStyle { setColor(EnumChatFormatting.DARK_GRAY) }
fun IChatComponent.withBlue() = withChatStyle { setColor(EnumChatFormatting.BLUE) }
fun IChatComponent.withGreen() = withChatStyle { setColor(EnumChatFormatting.GREEN) }
fun IChatComponent.withAqua() = withChatStyle { setColor(EnumChatFormatting.AQUA) }
fun IChatComponent.withRed() = withChatStyle { setColor(EnumChatFormatting.RED) }
fun IChatComponent.withLightPurple() = withChatStyle { setColor(EnumChatFormatting.LIGHT_PURPLE) }
fun IChatComponent.withYellow() = withChatStyle { setColor(EnumChatFormatting.YELLOW) }
fun IChatComponent.withWhite() = withChatStyle { setColor(EnumChatFormatting.WHITE) }
fun IChatComponent.withObfuscated() = withChatStyle { setObfuscated(true) }
fun IChatComponent.withBold() = withChatStyle { setBold(true) }
fun IChatComponent.withStrikethrough() = withChatStyle { setStrikethrough(true) }
fun IChatComponent.withUnderline() = withChatStyle { setUnderlined(true) }
fun IChatComponent.withItalic() = withChatStyle { setItalic(true) }
fun IChatComponent.withReset() = withChatStyle { setColor(EnumChatFormatting.RESET) }

// #tr Overpowered_TeleportChat
// #en %s/%s/%s
// #zh %s/%s/%s
fun teleportChat(x: Int, y: Int, z: Int) = translatedChat(
	"Overpowered_TeleportChat",
	plainChat("$x").withRed(),
	plainChat("$y").withGreen(),
	plainChat("$z").withBlue(),
).withUnderline().withChatStyle {
	setChatClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @p $x $y $z"))
}

fun teleportChat(x: Float, y: Float, z: Float) = translatedChat(
	"Overpowered_TeleportChat",
	plainChat("$x").withRed(),
	plainChat("$y").withGreen(),
	plainChat("$z").withBlue(),
).withUnderline().withChatStyle {
	setChatClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @p $x $y $z"))
}
