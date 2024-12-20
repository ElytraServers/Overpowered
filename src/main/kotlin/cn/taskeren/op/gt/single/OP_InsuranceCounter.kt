package cn.taskeren.op.gt.single

import cn.taskeren.op.OP
import cn.taskeren.op.gt.init.OP_ItemList
import cn.taskeren.op.gt.item.impl.InsuranceReceiptItemBehaviour
import cn.taskeren.op.gt.utils.GTApi
import cn.taskeren.op.gt.utils.OP_FakeRecipe
import cn.taskeren.op.gt.utils.OP_Text
import cn.taskeren.op.gt.utils.OP_Texture
import cn.taskeren.op.gt.utils.extension.tier
import cn.taskeren.op.insurance.InsuranceManager
import cn.taskeren.op.mc.util.plainChat
import cn.taskeren.op.mc.util.sendTranslatedMessage
import cn.taskeren.op.mc.util.translatedChat
import cn.taskeren.op.mc.util.withBlue
import cn.taskeren.op.mc.util.withGold
import cn.taskeren.op.mc.util.withGray
import cn.taskeren.op.translated
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.MTEBasicMachine
import gregtech.api.recipe.RecipeMap
import gregtech.api.util.GTRecipeBuilder
import gregtech.api.util.GTUtility
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import java.util.UUID

class OP_InsuranceCounter : MTEBasicMachine {

	companion object {
		val DESC = arrayOf(
			// #tr Insurance_Counter_Tooltip_1
			// #en Insurance Counter
			// #zh 保险柜台
			translated("Insurance_Counter_Tooltip_1"),
			// #tr Insurance_Counter_Tooltip_2
			// #en Refund your exploded machines back!
			// #zh 将你爆炸的机器赔付回来！
			translated("Insurance_Counter_Tooltip_2"),
			// #tr Insurance_Counter_Tooltip_3
			// #en Rightclick with {BLUE}Receipts {GRAY}to list the exploded machines.
			// #zh 使用{BLUE}保险单{GRAY}右键来查看爆炸的机器。
			translated("Insurance_Counter_Tooltip_3"),
			OP_Text.TOOLTIP_CREDIT,
		)
	}

	constructor(aID: Int, aName: String, aNameRegional: String, aTier: Int) : super(
		aID,
		aName,
		aNameRegional,
		aTier,
		1,
		DESC,
		1,
		1,
		*OP_Texture.getBasicMachineTextures("INSURANCE")
	)

	constructor(
		aName: String,
		aTier: Int,
		aAmp: Int,
		aDescription: Array<String>,
		aTextures: Array<Array<Array<ITexture>>>?,
	) : super(aName, aTier, aAmp, aDescription, aTextures, 1, 1)

	override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity? {
		return OP_InsuranceCounter(mName, tier, mAmperage, mDescriptionArray, mTextures)
	}

	override fun checkRecipe(): Int {
		val item = getInputAt(0)
		if(item == null) return DID_NOT_FIND_RECIPE
		if(getOutputAt(0) != null) return DID_NOT_FIND_RECIPE

		// refund machines from insurance receipts
		if(GTUtility.areStacksEqual(OP_ItemList.InsuranceReceipt.get(1), item, true)) {
			val uuid = InsuranceReceiptItemBehaviour.getOwnerUuid(item)?.let(UUID::fromString)
			val metaId = InsuranceReceiptItemBehaviour.getBoundMetaId(item)
			if(uuid == null || metaId == null) return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS

			if(InsuranceManager.hasExplodedMachine(uuid, metaId) &&
				InsuranceManager.refundExplodedMachine(uuid, metaId)
			) {
				val refundItem = GTApi.getMachineItemStack(metaId)
				mOutputItems[0] = refundItem
				item.stackSize -= 1
				// fixed time
				mMaxProgresstime = 300 * GTRecipeBuilder.SECONDS
				return FOUND_AND_SUCCESSFULLY_USED_RECIPE
			} else {
				return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS
			}
		}

		// get insurance receipt from machines
		if(item.item == Item.getItemFromBlock(OP.gtMachineBlock)) {
			mOutputItems[0] = OP_ItemList.InsuranceReceipt.get(1)
			item.stackSize -= 1
			mMaxProgresstime = 20
			return FOUND_AND_SUCCESSFULLY_USED_RECIPE
		}

		// (hidden) convert living bio chip to dying bio chip
		if(GTUtility.areStacksEqual(OP.livingBioChip, item, true)) {
			mOutputItems[0] = OP_ItemList.DyingBioChip.get(1)
			item.stackSize -= 1
			mMaxProgresstime = 1
			return FOUND_AND_SUCCESSFULLY_USED_RECIPE
		}

		return DID_NOT_FIND_RECIPE
	}

	override fun getRecipeMap(): RecipeMap<*>? {
		return OP_FakeRecipe.FakeInsuranceCounterRecipe
	}

	override fun onRightclick(aBaseMetaTileEntity: IGregTechTileEntity, aPlayer: EntityPlayer): Boolean {
		if(aBaseMetaTileEntity.isServerSide) {
			val item = aPlayer.heldItem
			if(GTUtility.areStacksEqual(item, OP_ItemList.InsuranceReceipt.get(1), true)) {
				val list = InsuranceManager.getMyExplodedMachines(aPlayer.uniqueID)
				val counts = list.groupingBy { it }.eachCount()
				counts.forEach { metaId, count ->
					// #tr Insurance_OutputRecords
					// #en - %s %s %s
					// #zh - %s %s %s
					aPlayer.sendTranslatedMessage(
						"Insurance_OutputRecords",
						plainChat("${count}x").withBlue(),
						translatedChat(GTApi.getMachineUnlocalizedNameOrUnknownMachine(metaId)).withGold(),
						plainChat("($metaId)").withGray()
					)
				}
				return true
			}
		}

		return super.onRightclick(aBaseMetaTileEntity, aPlayer)
	}

	override fun canHaveInsufficientEnergy(): Boolean {
		return true
	}

	override fun stutterProcess() {
		super.stutterProcess()
	}

}
