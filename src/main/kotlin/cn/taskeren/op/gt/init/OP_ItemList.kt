package cn.taskeren.op.gt.init

import cn.taskeren.op.OP_Logger
import cn.taskeren.op.gt.IdItemContainer
import gregtech.api.enums.GT_Values
import gregtech.api.interfaces.IItemContainer
import gregtech.api.util.GT_LanguageManager
import gregtech.api.util.GT_ModHandler
import gregtech.api.util.GT_OreDictUnificator
import gregtech.api.util.GT_Utility
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

enum class OP_ItemList(override val id: Short) : IdItemContainer, OP_Logger {

	DyingBioChip(1),
	CertifiedElectrician(2),

	;

	private lateinit var theItem: ItemStack

	private var deprecated = false
	private var deprecationWarned = false

	override fun hasBeenSet(): Boolean {
		return ::theItem.isInitialized
	}

	override fun set(aItem: Item?): IItemContainer {
		if(aItem != null) {
			val stack = ItemStack(aItem, 1, 0)
			theItem = GT_Utility.copyAmount(1, stack)
		}
		return this
	}

	override fun set(aStack: ItemStack?): IItemContainer {
		theItem = GT_Utility.copyAmount(1, aStack)
		return this
	}

	private fun sanityCheck() {
		// throw if the item is not prepared
		if(!hasBeenSet()) {
			throw IllegalAccessException("The enum '$name' has not been set to an Item at this time!")
		}

		// print deprecated items
		if(deprecated && !deprecationWarned) {
			val e = Exception("The enum '$name' has been deprecated!")
			logger.warn(e)
			deprecationWarned = true
		}
	}

	override fun getItem(): Item? {
		sanityCheck()
		return if(GT_Utility.isStackValid(theItem)) {
			theItem.item
		} else {
			null
		}
	}

	override fun getBlock(): Block? {
		sanityCheck()
		return GT_Utility.getBlockFromItem(item)
	}

	override fun isStackEqual(aStack: Any?): Boolean {
		return isStackEqual(aStack, false, false)
	}

	override fun isStackEqual(
		aStack: Any?,
		aWildcard: Boolean,
		aIgnoreNBT: Boolean,
	): Boolean {
		if(aStack !is ItemStack || GT_Utility.isStackInvalid(aStack)) return false
		return GT_Utility.areUnificationsEqual(aStack, if(aWildcard) getWildcard(1) else get(1), aIgnoreNBT)
	}

	override fun get(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		sanityCheck()
		if(GT_Utility.isStackInvalid(theItem)) {
			logger.warn("Object in the OP_ItemList is null", NullPointerException())
			return GT_Utility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GT_Utility.copyAmount(aAmount.toInt(), GT_OreDictUnificator.get(theItem))
	}

	override fun getWildcard(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		sanityCheck()
		if(GT_Utility.isStackInvalid(theItem)) {
			return GT_Utility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GT_Utility.copyAmountAndMetaData(aAmount.toInt(), GT_Values.W.toInt(), GT_OreDictUnificator.get(theItem))
	}

	override fun getUndamaged(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		sanityCheck()
		if(GT_Utility.isStackInvalid(theItem)) {
			return GT_Utility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GT_Utility.copyAmountAndMetaData(aAmount.toInt(), 0, GT_OreDictUnificator.get(theItem))
	}

	override fun getAlmostBroken(
		aAmount: Long,
		vararg aReplacements: Any?,
	): ItemStack? {
		sanityCheck()
		if(GT_Utility.isStackInvalid(theItem)) {
			return GT_Utility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GT_Utility.copyAmountAndMetaData(
			aAmount.toInt(),
			theItem.maxDamage - 1,
			GT_OreDictUnificator.get(theItem)
		)
	}

	override fun getWithName(
		aAmount: Long,
		aDisplayName: String,
		vararg aReplacements: Any?,
	): ItemStack? {
		val stack = get(1, aReplacements)
		if(stack == null || GT_Utility.isStackInvalid(stack)) return GT_Values.NI

		// CamelCase alphanumeric words from aDisplayName
		val camelCasedDisplayName = buildString {
			aDisplayName.split(Regex("\\W")).forEach { word ->
				append(word.replaceFirstChar { it.uppercase() })
			}

			// CamelCased DisplayName is empty, so use hash of aDisplayName
			if(isEmpty()) {
				append(aDisplayName.hashCode().toString())
			}
		}

		// Construct a translation key from UnlocalizedName and CamelCased DisplayName
		val key = "${stack.unlocalizedName}.with.$camelCasedDisplayName.name"

		stack.setStackDisplayName(GT_LanguageManager.addStringLocalization(key, aDisplayName))
		return GT_Utility.copyAmount(aAmount.toInt(), stack)
	}

	override fun getWithCharge(
		aAmount: Long,
		aEnergy: Int,
		vararg aReplacements: Any?,
	): ItemStack? {
		val stack = get(1, aReplacements)
		if(stack == null || GT_Utility.isStackInvalid(stack)) return null
		GT_ModHandler.chargeElectricItem(stack, aEnergy, Int.MAX_VALUE, true, false)
		return GT_Utility.copyAmount(aAmount.toInt(), stack)
	}

	override fun getWithDamage(
		aAmount: Long,
		aMetaValue: Long,
		vararg aReplacements: Any?,
	): ItemStack? {
		sanityCheck()
		if(GT_Utility.isStackInvalid(theItem)) return GT_Utility.copyAmount(
			aAmount.toInt(),
			aReplacements.filterIsInstance<ItemStack>().firstOrNull()
		)
		return GT_Utility.copyAmountAndMetaData(aAmount.toInt(), aMetaValue.toInt(), GT_OreDictUnificator.get(theItem))
	}

	override fun registerOre(vararg aOreNames: Any?): IItemContainer {
		sanityCheck()
		aOreNames.forEach {
			GT_OreDictUnificator.registerOre(it, get(1))
		}
		return this
	}

	override fun registerWildcardAsOre(vararg aOreNames: Any?): IItemContainer {
		sanityCheck()
		aOreNames.forEach {
			GT_OreDictUnificator.registerOre(it, getWildcard(1))
		}
		return this
	}

}

