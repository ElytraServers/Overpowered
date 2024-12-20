package cn.taskeren.op.gt

import cn.taskeren.op.gt.init.LazyScheduler
import cn.taskeren.op.gt.utils.GTApi
import cn.taskeren.op.mc.OP_CreativeTab
import gregtech.api.enums.GTValues
import gregtech.api.interfaces.IItemContainer
import gregtech.api.interfaces.IRecipeMap
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.util.GTRecipeBuilder
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
	try {
		block(it).getStackForm(1)
	} catch(e: IllegalArgumentException) {
		GTApi.checkMetaIdCollision(id.toInt()) // throw before the cause if collision
		throw e
	}
}

/**
 * Create an instance of the item with given id. It will do the rest things for you.
 */
fun IdItemContainer.registerItem(block: (id: Int) -> ItemStack) = apply {
	val constructed = block(id.toInt())
	set(constructed)
	// add to the creative tab
	OP_CreativeTab.itemList += constructed
}

fun IdItemContainer.addRecipe(recipeMap: IRecipeMap, block: GTRecipeBuilder.(defaultItem: ItemStack) -> Unit) = apply {
	LazyScheduler.schedulePostInit {
		GTValues.RA.stdBuilder().apply { block(get(1)) }.addTo(recipeMap)
	}
}

fun IdItemContainer.useItemStackPostInit(block: (defaultItem: ItemStack) -> Unit) = apply {
	LazyScheduler.schedulePostInit {
		block(get(1))
	}
}

fun IdItemContainer.addRecipeSimple(block: (defaultItem: ItemStack) -> Unit) = useItemStackPostInit(block)
