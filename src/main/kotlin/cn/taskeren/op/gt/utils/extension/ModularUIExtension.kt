package cn.taskeren.op.gt.utils.extension

import com.gtnewhorizons.modularui.api.forge.ItemStackHandler
import com.gtnewhorizons.modularui.api.screen.ModularWindow
import com.gtnewhorizons.modularui.api.widget.Widget
import com.gtnewhorizons.modularui.common.widget.ButtonWidget
import com.gtnewhorizons.modularui.common.widget.DrawableWidget
import com.gtnewhorizons.modularui.common.widget.SlotWidget
import com.gtnewhorizons.modularui.common.widget.TextWidget
import com.gtnewhorizons.modularui.common.widget.textfield.NumericWidget

fun ModularWindow.Builder.widget(block: () -> Widget) = apply { widget(block()) }

fun ModularWindow.Builder.drawable(block: DrawableWidget.() -> Unit) = widget {
	DrawableWidget().apply(block)
}

fun ModularWindow.Builder.slot(inventoryHandler: ItemStackHandler, slot: Int, block: SlotWidget.() -> Unit) = widget {
	SlotWidget(inventoryHandler, slot).apply(block)
}

fun ModularWindow.Builder.text(block: TextWidget.() -> Unit) = widget {
	TextWidget().apply(block)
}

fun ModularWindow.Builder.text(text: String, block: TextWidget.() -> Unit) = widget {
	TextWidget(text).apply(block)
}

fun ModularWindow.Builder.numeric(block: NumericWidget.() -> Unit) = widget {
	NumericWidget().apply(block)
}

fun ModularWindow.Builder.button(block: ButtonWidget.() -> Unit) = widget {
	ButtonWidget().apply(block)
}
