@file:Suppress("unused")

package cn.taskeren.op.gt.utils

import net.minecraft.util.EnumChatFormatting

data class InfoDataBuilder(private val infoDataList: MutableList<String> = mutableListOf()) {

	companion object {
		val BLACK = EnumChatFormatting.BLACK.toString()
		val DARK_BLUE = EnumChatFormatting.DARK_BLUE.toString()
		val DARK_GREEN = EnumChatFormatting.DARK_GREEN.toString()
		val DARK_AQUA = EnumChatFormatting.DARK_AQUA.toString()
		val DARK_RED = EnumChatFormatting.DARK_RED.toString()
		val DARK_PURPLE = EnumChatFormatting.DARK_PURPLE.toString()
		val GOLD = EnumChatFormatting.GOLD.toString()
		val GRAY = EnumChatFormatting.GRAY.toString()
		val DARK_GRAY = EnumChatFormatting.DARK_GRAY.toString()
		val BLUE = EnumChatFormatting.BLUE.toString()
		val GREEN = EnumChatFormatting.GREEN.toString()
		val AQUA = EnumChatFormatting.AQUA.toString()
		val RED = EnumChatFormatting.RED.toString()
		val LIGHT_PURPLE = EnumChatFormatting.LIGHT_PURPLE.toString()
		val YELLOW = EnumChatFormatting.YELLOW.toString()
		val WHITE = EnumChatFormatting.WHITE.toString()
		val OBFUSCATED = EnumChatFormatting.OBFUSCATED.toString()
		val BOLD = EnumChatFormatting.BOLD.toString()
		val STRIKETHROUGH = EnumChatFormatting.STRIKETHROUGH.toString()
		val UNDERLINE = EnumChatFormatting.UNDERLINE.toString()
		val ITALIC = EnumChatFormatting.ITALIC.toString()
		val RESET = EnumChatFormatting.RESET.toString()
	}

	val black = BLACK
	val darkBlue = DARK_BLUE
	val darkGreen = DARK_GREEN
	val darkAqua = DARK_AQUA
	val darkRed = DARK_RED
	val darkPurple = DARK_PURPLE
	val gold = GOLD
	val gray = GRAY
	val darkGray = DARK_GRAY
	val blue = BLUE
	val green = GREEN
	val aqua = AQUA
	val red = RED
	val lightPurple = LIGHT_PURPLE
	val yellow = YELLOW
	val white = WHITE
	val obfuscated = OBFUSCATED
	val bold = BOLD
	val strikethrough = STRIKETHROUGH
	val underline = UNDERLINE
	val italic = ITALIC
	val reset = RESET

	fun add(info: Array<String>) {
		infoDataList.addAll(info)
	}

	fun text(text: String) {
		infoDataList.add(text)
	}

	fun line(block: StringBuilder.() -> Unit) = text(buildString(block))

	fun build(): Array<String> = infoDataList.toTypedArray()
}

internal fun buildInfoData(block: InfoDataBuilder.() -> Unit): Array<String> = InfoDataBuilder().apply(block).build()
