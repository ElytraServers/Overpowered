package cn.taskeren.op.gt.item.impl

import cn.taskeren.op.gt.utils.InfoDataBuilder
import cn.taskeren.op.mc.util.sendTranslatedMessage
import cn.taskeren.op.translated
import gregtech.api.GregTech_API
import gregtech.api.items.GT_MetaBase_Item
import gregtech.api.metatileentity.CommonMetaTileEntity
import gregtech.api.util.GT_Utility
import gregtech.common.items.behaviors.Behaviour_None
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.StatCollector
import net.minecraft.world.World

object InsuranceReceiptItemBehaviour : Behaviour_None() {

	fun getBoundMetaId(stack: ItemStack): Int? {
		return stack.tagCompound?.getInteger("BoundMetaId")
	}

	fun setBoundMetaId(stack: ItemStack, id: Int) {
		stack.tagCompound = (stack.tagCompound ?: NBTTagCompound()).apply { setInteger("BoundMetaId", id) }
	}

	fun getOwnerUuid(stack: ItemStack): String? {
		return stack.tagCompound?.getString("OwnerUuid")
	}

	fun setOwnerUuid(stack: ItemStack, uuid: String) {
		stack.tagCompound = (stack.tagCompound ?: NBTTagCompound()).apply { setString("OwnerUuid", uuid) }
	}

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
		hitZ: Float
	): Boolean {
		if(getBoundMetaId(aStack) != null) {
			// #tr Insurance_Message_ReceiptAlreadyBound
			// #en It has already bound!
			// #zh 已经绑定过啦！
			if(!aWorld.isRemote) aPlayer.sendTranslatedMessage("Insurance_Message_ReceiptAlreadyBound")
			return true
		}

		val te = aWorld.getTileEntity(aX, aY, aZ)
		if(te is CommonMetaTileEntity) {
			if(aStack.stackSize > 1) {
				val split = aStack.splitStack(1)
				setBoundMetaId(split, te.metaTileID)
				setOwnerUuid(split, aPlayer.uniqueID.toString())
				GT_Utility.addItemToPlayerInventory(aPlayer, split)
			} else {
				setBoundMetaId(aStack, te.metaTileID)
				setOwnerUuid(aStack, aPlayer.uniqueID.toString())
			}

			// #tr Insurance_Message_ReceiptBindingSuccess
			// #en Insurance bound successfully!
			// #zh 保险绑定成功！
			if(!aWorld.isRemote) aPlayer.sendTranslatedMessage("Insurance_Message_ReceiptBindingSuccess")
			return true
		}
		return super.onItemUse(aItem, aStack, aPlayer, aWorld, aX, aY, aZ, ordinalSide, hitX, hitY, hitZ)
	}

	override fun getAdditionalToolTips(
		aItem: GT_MetaBase_Item,
		tooltips: MutableList<String>,
		aStack: ItemStack,
	): MutableList<String> {
		val metaId = getBoundMetaId(aStack)
		if(metaId != null) {
			// #tr Insurance_Receipt_Bound
			// #en Bound MetaId: %s
			// #zh 绑定的 MetaId：%s
			tooltips.add(InfoDataBuilder.YELLOW + translated("Insurance_Receipt_Bound", metaId))
			tooltips.add(InfoDataBuilder.YELLOW + StatCollector.translateToLocal(GregTech_API.METATILEENTITIES[metaId].localName))
		} else {
			// #tr Insurance_Receipt_Unbound
			// #en Unbound
			// #zh 未绑定
			tooltips.add(InfoDataBuilder.YELLOW + translated("Insurance_Receipt_Unbound"))
		}
		return tooltips
	}

}
