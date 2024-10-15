package cn.taskeren.op.gt.init

import cn.taskeren.op.OP_Logger
import cn.taskeren.op.gt.IdItemContainer
import gregtech.api.enums.GTValues
import gregtech.api.interfaces.IItemContainer
import gregtech.api.util.GTLanguageManager
import gregtech.api.util.GTModHandler
import gregtech.api.util.GTOreDictUnificator
import gregtech.api.util.GTUtility
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

enum class OP_ItemList(override val id: Short) : IdItemContainer, OP_Logger {

	// OP_GeneratedItem
	DyingBioChip(1),
	CertifiedElectrician(2),
	InsuranceReceipt(3),
	ActiveTransformerExplosionCore(4),
	DebugItem(5),

	// OP_GeneratedAEUpgradeItem
	ProgrammedUpgrade(0),

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
			theItem = GTUtility.copyAmount(1, stack)
		}
		return this
	}

	override fun set(aStack: ItemStack?): IItemContainer {
		theItem = GTUtility.copyAmount(1, aStack)
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
		return if(GTUtility.isStackValid(theItem)) {
			theItem.item
		} else {
			null
		}
	}

	override fun getBlock(): Block? {
		sanityCheck()
		return GTUtility.getBlockFromItem(item)
	}

	override fun isStackEqual(aStack: Any?): Boolean {
		return isStackEqual(aStack, false, false)
	}

	override fun isStackEqual(
		aStack: Any?,
		aWildcard: Boolean,
		aIgnoreNBT: Boolean,
	): Boolean {
		if(aStack !is ItemStack || GTUtility.isStackInvalid(aStack)) return false
		return GTUtility.areUnificationsEqual(aStack, if(aWildcard) getWildcard(1) else get(1), aIgnoreNBT)
	}

	override fun get(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		sanityCheck()
		if(GTUtility.isStackInvalid(theItem)) {
			logger.warn("Object in the OP_ItemList is null", NullPointerException())
			return GTUtility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GTUtility.copyAmount(aAmount.toInt(), GTOreDictUnificator.get(theItem))
	}

	override fun getWildcard(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		sanityCheck()
		if(GTUtility.isStackInvalid(theItem)) {
			return GTUtility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GTUtility.copyAmountAndMetaData(aAmount.toInt(), GTValues.W.toInt(), GTOreDictUnificator.get(theItem))
	}

	override fun getUndamaged(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		sanityCheck()
		if(GTUtility.isStackInvalid(theItem)) {
			return GTUtility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GTUtility.copyAmountAndMetaData(aAmount.toInt(), 0, GTOreDictUnificator.get(theItem))
	}

	override fun getAlmostBroken(
		aAmount: Long,
		vararg aReplacements: Any?,
	): ItemStack? {
		sanityCheck()
		if(GTUtility.isStackInvalid(theItem)) {
			return GTUtility.copyAmount(aAmount.toInt(), aReplacements.filterIsInstance<ItemStack>().firstOrNull())
		}
		return GTUtility.copyAmountAndMetaData(
			aAmount.toInt(),
			theItem.maxDamage - 1,
			GTOreDictUnificator.get(theItem)
		)
	}

	override fun getWithName(
		aAmount: Long,
		aDisplayName: String,
		vararg aReplacements: Any?,
	): ItemStack? {
		val stack = get(1, aReplacements)
		if(stack == null || GTUtility.isStackInvalid(stack)) return GTValues.NI

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

		stack.setStackDisplayName(GTLanguageManager.addStringLocalization(key, aDisplayName))
		return GTUtility.copyAmount(aAmount.toInt(), stack)
	}

	override fun getWithCharge(
		aAmount: Long,
		aEnergy: Int,
		vararg aReplacements: Any?,
	): ItemStack? {
		val stack = get(1, aReplacements)
		if(stack == null || GTUtility.isStackInvalid(stack)) return null
		GTModHandler.chargeElectricItem(stack, aEnergy, Int.MAX_VALUE, true, false)
		return GTUtility.copyAmount(aAmount.toInt(), stack)
	}

	override fun getWithDamage(
		aAmount: Long,
		aMetaValue: Long,
		vararg aReplacements: Any?,
	): ItemStack? {
		sanityCheck()
		if(GTUtility.isStackInvalid(theItem)) return GTUtility.copyAmount(
			aAmount.toInt(),
			aReplacements.filterIsInstance<ItemStack>().firstOrNull()
		)
		return GTUtility.copyAmountAndMetaData(aAmount.toInt(), aMetaValue.toInt(), GTOreDictUnificator.get(theItem))
	}

	override fun registerOre(vararg aOreNames: Any?): IItemContainer {
		sanityCheck()
		aOreNames.forEach {
			GTOreDictUnificator.registerOre(it, get(1))
		}
		return this
	}

	override fun registerWildcardAsOre(vararg aOreNames: Any?): IItemContainer {
		sanityCheck()
		aOreNames.forEach {
			GTOreDictUnificator.registerOre(it, getWildcard(1))
		}
		return this
	}

}

