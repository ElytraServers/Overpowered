package cn.taskeren.op.gt

import gregtech.api.interfaces.IItemContainer
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import net.minecraft.item.ItemStack

interface IdItemContainer : IItemContainer {

	/**
	 * The identifier for this item.
	 */
	val id: Short
}

/**
 * Create an instance of the block with given id. It will do the rest things for you.
 */
fun IdItemContainer.registerMachine(block: (id: Int) -> IMetaTileEntity) = registerItem {
	block(it).getStackForm(1)
}

/**
 * Create an instance of the item with given id. It will do the rest things for you.
 */
fun IdItemContainer.registerItem(block: (id: Int) -> ItemStack) {
	val constructed = block(id.toInt())
	set(constructed)
}
