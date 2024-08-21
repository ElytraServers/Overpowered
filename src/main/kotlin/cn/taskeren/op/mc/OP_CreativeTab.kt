package cn.taskeren.op.mc

import cn.taskeren.op.gt.init.OP_ItemList
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

// #tr itemGroup.op_creativeTab
// #en Overpowered!
// #zh Overpowered!
object OP_CreativeTab : CreativeTabs("op_creativeTab") {

	val itemList: MutableList<ItemStack> = mutableListOf()

	@Deprecated("Use getIconItemStack() instead!", ReplaceWith("getIconItemStack().getItem()"))
	override fun getTabIconItem(): Item? = Items.emerald

	override fun getIconItemStack(): ItemStack? {
		return OP_ItemList.DyingBioChip.get(1) ?: ItemStack(Items.emerald)
	}

	override fun displayAllReleventItems(list: MutableList<ItemStack>) {
		list.addAll(itemList)
	}

}
