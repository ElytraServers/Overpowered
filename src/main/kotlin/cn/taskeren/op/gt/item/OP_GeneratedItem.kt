package cn.taskeren.op.gt.item

import cn.taskeren.op.gt.init.OP_ItemList
import cn.taskeren.op.gt.item.impl.ActiveTransformerExplosionCoreItemBehaviour
import cn.taskeren.op.gt.item.impl.InsuranceReceiptItemBehaviour
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
			// #tr gt.metaitem.op.32001.name
			// #en Dying Bio Chip

			// #tr gt.metaitem.op.32001.tooltip
			// #en Squeezed Living Bio Chip

			addItem(it, "Dying Bio Chip", "Squeezed Living Bio Chip")
		}
		OP_ItemList.CertifiedElectrician.registerItem {
			// #tr gt.metaitem.op.32002.name
			// #en Certified Electrician
			// #zh 电工资格证
			// #tr gt.metaitem.op.32002.tooltip
			// #en Proof of your qualifications on Electrical Engineering
			// #zh 证明你在电气工程的资历
			addItem(it, "Certified Electrician", "Proof of your qualifications on Electrical Engineering")
		}
		OP_ItemList.InsuranceReceipt.registerItem {
			// #tr gt.metaitem.op.32003.name
			// #en Insurance Receipt
			// #zh 保险单
			addItem(it, "Insurance Receipt", "", InsuranceReceiptItemBehaviour)
		}
		OP_ItemList.ActiveTransformerExplosionCore.registerItem {
			// #tr gt.metaitem.op.32004.name
			// #en Active Transformer Explosion Core
			// #zh 有源变压器爆炸核心
			addItem(it, "Active Transformer Explosion Core", "", ActiveTransformerExplosionCoreItemBehaviour)
		}
	}

}
