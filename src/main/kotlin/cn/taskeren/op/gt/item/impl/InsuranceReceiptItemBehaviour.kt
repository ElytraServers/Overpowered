package cn.taskeren.op.gt.item.impl

import cn.taskeren.op.gt.utils.GTApi
import cn.taskeren.op.mc.util.plainChat
import cn.taskeren.op.mc.util.sendTranslatedMessage
import cn.taskeren.op.mc.util.translatedChat
import cn.taskeren.op.mc.util.withGold
import cn.taskeren.op.mc.util.withGray
import cn.taskeren.op.translated
import gregtech.api.items.MetaBaseItem
import gregtech.api.metatileentity.CommonMetaTileEntity
import gregtech.api.util.GTUtility
import gregtech.common.items.behaviors.BehaviourNone
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

object InsuranceReceiptItemBehaviour : BehaviourNone() {

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
		val te = aWorld.getTileEntity(aX, aY, aZ)

		if(getBoundMetaId(aStack) != null) {
			// #tr Insurance_Message_ReceiptAlreadyBound
			// #en It has already bound!
			// #zh 已经绑定过啦！
			if(!aWorld.isRemote) aPlayer.sendTranslatedMessage("Insurance_Message_ReceiptAlreadyBound")
			return true
		}

		if(te is CommonMetaTileEntity) {
			if(aStack.stackSize > 1) {
				val split = aStack.splitStack(1)
				setBoundMetaId(split, te.metaTileID)
				setOwnerUuid(split, aPlayer.uniqueID.toString())
				GTUtility.addItemToPlayerInventory(aPlayer, split)
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
		aItem: MetaBaseItem,
		tooltips: MutableList<String>,
		aStack: ItemStack,
	): MutableList<String> {
		val metaId = getBoundMetaId(aStack)
		if(metaId != null) {
			// #tr Insurance_Receipt_Bound
			// #en {YELLOW}Bound Machine: %s %s
			// #zh {YELLOW}绑定的机器：%s %s
			tooltips.add(translatedChat("Insurance_Receipt_Bound", translatedChat(GTApi.getMachineUnlocalizedNameOrUnknownMachine(metaId)).withGold(), plainChat("($metaId)").withGray()).formattedText)
		} else {
			// #tr Insurance_Receipt_Unbound
			// #en {YELLOW}Unbound
			// #zh {YELLOW}未绑定
			tooltips.add(translated("Insurance_Receipt_Unbound"))
		}
		return tooltips
	}

}
