package cn.taskeren.op.gt.item

import appeng.api.config.Upgrades
import appeng.api.implementations.items.IUpgradeModule
import gregtech.api.items.GT_MetaGenerated_Item_X32
import gregtech.api.util.GT_Utility
import net.minecraft.item.ItemStack

/**
 * Textures should be placed at `assets/gregtech/textures/items/gt.metaitem.op.ae/$id.png`
 *
 * @see gregtech.common.items.GT_MetaGenerated_Item_01
 */
object OP_GeneratedAEUpgradeItem : GT_MetaGenerated_Item_X32("metaitem.op.ae"), IUpgradeModule {

	private val upgradeTypeRegistry = mutableMapOf<ItemStack, Upgrades>()

	fun registerUpgrade(item: ItemStack, upgrades: Upgrades?) {
		if(upgrades != null) {
			upgradeTypeRegistry[item] = upgrades
		} else {
			upgradeTypeRegistry -= item
		}
	}

	override fun getType(stack: ItemStack): Upgrades? {
		for((item, upgrade) in upgradeTypeRegistry) {
			if(GT_Utility.areStacksEqual(item, stack, true)) {
				return upgrade
			}
		}

		return null
	}

}
