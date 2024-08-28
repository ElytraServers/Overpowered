package cn.taskeren.op.gt.item.impl

import cn.taskeren.op.api.IVoltageChanceBonus
import cn.taskeren.op.mc.util.sendTranslatedMessage
import gregtech.api.items.GT_MetaBase_Item
import gregtech.api.metatileentity.CommonMetaTileEntity
import gregtech.common.items.behaviors.Behaviour_None
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

object DebugItemBehaviour : Behaviour_None() {

	override fun onItemUse(
		aItem: GT_MetaBase_Item,
		aStack: ItemStack,
		aPlayer: EntityPlayer,
		aWorld: World,
		aX: Int,
		aY: Int,
		aZ: Int,
		ordinalSide: Int,
		hitX: Float,
		hitY: Float,
		hitZ: Float,
	): Boolean {
		if(!aWorld.isRemote) {
			val tile = aWorld.getTileEntity(aX, aY, aZ)
			if(tile is CommonMetaTileEntity) {
				val metaTile = tile.metaTileEntity
				if(metaTile is IVoltageChanceBonus) {
					// #tr OP_Debug_VoltageChanceBonus_1
					// #en Voltage Chance Bonus: %.2f%% (volt tier: %s, base volt tier: %s)
					// #zh 电压概率加成：%.2f%%（电压等级：%s，基础电压等级：%s）
					aPlayer.sendTranslatedMessage(
						"OP_Debug_VoltageChanceBonus_1",
						metaTile.bonusChance * 100,
						metaTile.voltageTier,
						metaTile.baseVoltageTier,
					)
				}
			}
		}

		return false
	}
}
