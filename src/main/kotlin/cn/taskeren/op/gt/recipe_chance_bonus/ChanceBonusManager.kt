package cn.taskeren.op.gt.recipe_chance_bonus

import cn.taskeren.op.OP_Logger
import cn.taskeren.op.OP_Logger.Companion.marker
import cn.taskeren.op.api.IVoltageChanceBonus
import gregtech.api.enums.GTVoltageIndex
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IVoidable
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase
import org.intellij.lang.annotations.MagicConstant
import java.util.LinkedList
import java.util.OptionalDouble

/**
 * The chance bonus provider.
 *
 * Return the additional chance or `null`.
 *
 * `machine` can be anything that SHOULD implement [IVoidable] and [IMetaTileEntity].
 */
typealias ChanceBonusProvider = (machine: Any, recipeTier: Int, prevChanceMultiplier: Double) -> Double?

object ChanceBonusManager : OP_Logger {

	val MarkerGTPPVoltageBonus = marker("GTPP_VoltageBonus")

	private val bonusProvider = LinkedList<ChanceBonusProvider>()

	@JvmName("_addLastBonusProvider_kt")
	fun addLastBonusProvider(chanceBonusProvider: ChanceBonusProvider) = bonusProvider.addLast(chanceBonusProvider)

	@JvmName("_addFirstBonusProvider_kt")
	fun addFirstBonusProvider(chanceBonusProvider: ChanceBonusProvider) = bonusProvider.addFirst(chanceBonusProvider)

	data class ChanceBonusProviderContext(val machine: Any, val recipeTier: Int, val prevChanceMultiplier: Double)

	@JvmStatic
	fun addLastBonusProvider(chanceBonusProvider: java.util.function.Function<ChanceBonusProviderContext, Double?>) =
		bonusProvider.addLast { machine, recipeTier, prevChanceMultiplier ->
			chanceBonusProvider.apply(ChanceBonusProviderContext(machine, recipeTier, prevChanceMultiplier))
		}

	@JvmStatic
	fun addFirstBonusProvider(chanceBonusProvider: java.util.function.Function<ChanceBonusProviderContext, Double?>) =
		bonusProvider.addFirst { machine, recipeTier, prevChanceMultiplier ->
			chanceBonusProvider.apply(ChanceBonusProviderContext(machine, recipeTier, prevChanceMultiplier))
		}

	/**
	 * Get the linear chance bonus for the voltage tier.
	 *
	 * @return the chance bonus for the circumstance.
	 * @param tier the machine tier or the energy hatch tier.
	 * @param baseTier the minimal (exclusive) bonus tier.
	 * @param chanceBonusPerTier the chance bonus per tier above the [baseTier].
	 */
	private fun getTierChanceBonus(tier: Int, baseTier: Int, chanceBonusPerTier: Double): Double {
		return if(tier <= baseTier) 0.0
		else (tier - baseTier) * chanceBonusPerTier
	}

	init {
		// builtin for IVoltageChanceBonus
		addLastBonusProvider { machine, _, _ -> if(machine is IVoltageChanceBonus) machine.bonusChance else null }

		// support for GT++ big machines
		addLastBonusProvider { machine, recipeTier, prevBonus ->
			when(machine) {
				is GregtechMeta_MultiBlockBase<*> -> {
					val minTierEnergyHatch = machine.mAllEnergyHatches.minOfOrNull { it.mTier }?.toInt() ?: 0
					getTierChanceBonus(minTierEnergyHatch, recipeTier, 0.15)
				}

				else -> null
			}
		}
	}

	/**
	 * Iterate [bonusProvider] to get the first non-null bonus, or `null`.
	 *
	 * @return get the chance bonus.
	 */
	fun getChanceBonus(
		machine: Any,
		@MagicConstant(valuesFromClass = GTVoltageIndex::class) recipeTier: Int,
		prevChanceMultiplier: Double,
	): Double? {
		return bonusProvider.firstNotNullOfOrNull { it(machine, recipeTier, prevChanceMultiplier) }
	}

	/**
	 * @return the optional double of the chance bonus.
	 * @see getChanceBonus
	 */
	@JvmStatic
	fun getChanceBonusOptional(
		machine: Any,
		@MagicConstant(valuesFromClass = GTVoltageIndex::class) recipeTier: Int,
		prevChanceMultiplier: Double,
	): OptionalDouble {
		return getChanceBonus(machine, recipeTier, prevChanceMultiplier)?.let(OptionalDouble::of)
			?: OptionalDouble.empty()
	}

}
