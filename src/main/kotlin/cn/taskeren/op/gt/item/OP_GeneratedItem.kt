package cn.taskeren.op.gt.item

import cn.taskeren.op.gt.init.OP_ItemList
import cn.taskeren.op.gt.registerItem
import gregtech.api.items.GT_MetaGenerated_Item_X32

/**
 * Textures should be placed at `assets/gregtech/textures/items/gt.metaitem.op/$id.png`
 *
 * @see gregtech.common.items.GT_MetaGenerated_Item_01
 */
object OP_GeneratedItem : GT_MetaGenerated_Item_X32("metaitem.op") {

	init {
		OP_ItemList.DyingBioChip.registerItem {
			addItem(it, "Dying Bio Chip", "Squeezed Living Bio Chip")
		}
		OP_ItemList.CertifiedElectrician.registerItem {
			addItem(it, "Certified Electrician", "Proof of your qualifications on Electrical Engineering")
		}
	}

}
