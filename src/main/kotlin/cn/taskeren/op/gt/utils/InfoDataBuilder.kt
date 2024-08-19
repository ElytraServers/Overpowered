@file:Suppress("unused")

package cn.taskeren.op.gt.utils

import net.minecraft.util.EnumChatFormatting

data class InfoDataBuilder(private val infoDataList: MutableList<String> = mutableListOf()) {

	companion object {
		private val BLACK = EnumChatFormatting.BLACK.toString()
		private val DARK_BLUE = EnumChatFormatting.DARK_BLUE.toString()
		private val DARK_GREEN = EnumChatFormatting.DARK_GREEN.toString()
		private val DARK_AQUA = EnumChatFormatting.DARK_AQUA.toString()
		private val DARK_RED = EnumChatFormatting.DARK_RED.toString()
		private val DARK_PURPLE = EnumChatFormatting.DARK_PURPLE.toString()
		private val GOLD = EnumChatFormatting.GOLD.toString()
		private val GRAY = EnumChatFormatting.GRAY.toString()
		private val DARK_GRAY = EnumChatFormatting.DARK_GRAY.toString()
		private val BLUE = EnumChatFormatting.BLUE.toString()
		private val GREEN = EnumChatFormatting.GREEN.toString()
		private val AQUA = EnumChatFormatting.AQUA.toString()
		private val RED = EnumChatFormatting.RED.toString()
		private val LIGHT_PURPLE = EnumChatFormatting.LIGHT_PURPLE.toString()
		private val YELLOW = EnumChatFormatting.YELLOW.toString()
		private val WHITE = EnumChatFormatting.WHITE.toString()
		private val OBFUSCATED = EnumChatFormatting.OBFUSCATED.toString()
		private val BOLD = EnumChatFormatting.BOLD.toString()
		private val STRIKETHROUGH = EnumChatFormatting.STRIKETHROUGH.toString()
		private val UNDERLINE = EnumChatFormatting.UNDERLINE.toString()
		private val ITALIC = EnumChatFormatting.ITALIC.toString()
		private val RESET = EnumChatFormatting.RESET.toString()
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
