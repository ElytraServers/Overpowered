package cn.taskeren.op.gt.item.impl

import cn.taskeren.op.mc.util.isCreative
import cn.taskeren.op.translated
import gregtech.api.enums.GTValues
import gregtech.api.items.MetaBaseItem
import gregtech.api.metatileentity.BaseMetaTileEntity
import gregtech.common.items.behaviors.BehaviourNone
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.world.World

object ActiveTransformerExplosionCoreItemBehaviour : BehaviourNone() {

	override fun onItemUse(
		aItem: MetaBaseItem,
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
			val te = aWorld.getTileEntity(aX, aY, aZ)
			if(te is BaseMetaTileEntity) {
				val stackUsed = aStack.stackSize.coerceAtMost(14)
				te.doExplosion(GTValues.V[(stackUsed + 1).coerceAtMost(15)])
				if(aPlayer !is EntityPlayerMP || !aPlayer.isCreative) {
					aStack.stackSize -= stackUsed
				}
				return true
			}
		}
		return false
	}

	override fun getAdditionalToolTips(
		aItem: MetaBaseItem,
		aList: MutableList<String>,
		aStack: ItemStack,
	): MutableList<String> {
		// #tr ActiveTransformerExplosionCore_Tooltip_1
		// #en The very core of Active Transformer and other TecTech machines.
		// #zh 有源变压器和其他 §9Tec§1Tech §7机器核心中的核心。
		aList.add(translated("ActiveTransformerExplosionCore_Tooltip_1"))
		// #tr ActiveTransformerExplosionCore_Tooltip_2
		// #en Put this on other machines to ignite the sky.
		// #zh 对其他机器使用就能点亮天空。
		aList.add(translated("ActiveTransformerExplosionCore_Tooltip_2"))
		// #tr ActiveTransformerExplosionCore_Tooltip_3
		// #en More cores can create a bigger explosion! (Max at 14)
		// #zh 额外的核心能够制造更大的爆炸！（最多14个）
		aList.add(translated("ActiveTransformerExplosionCore_Tooltip_3"))
		return aList
	}
}
