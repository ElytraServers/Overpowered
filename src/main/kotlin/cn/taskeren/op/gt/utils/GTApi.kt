package cn.taskeren.op.gt.utils

import cn.taskeren.op.utils.MixinAccessorBridge
import gregtech.api.GregTechAPI
import gregtech.api.enums.GTValues
import gregtech.api.enums.VoltageIndex
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.util.GTUtility
import net.minecraft.item.ItemStack
import org.intellij.lang.annotations.MagicConstant

object GTApi {

	val MachineBlock get() = GregTechAPI.sBlockMachines

	fun getMachineItemStack(metaId: Int): ItemStack {
		require(getMetaTileEntityById(metaId) != null) { "Invalid MetaTileId: $metaId" }
		return ItemStack(MachineBlock, 1, metaId)
	}

	fun getMachineUnlocalizedName(mte: IMetaTileEntity): String {
		return "gt.blockmachines.${mte.metaName}.name"
	}

	fun getMachineUnlocalizedName(metaId: Int): String? {
		val mte = getMetaTileEntityById(metaId) ?: return null
		return getMachineUnlocalizedName(mte)
	}

	fun getMachineUnlocalizedNameOrUnknownMachine(metaId: Int): String {
		// #tr Overpowered_UnknownMachine
		// #en Unknown Machine
		// #zh 未知机器
		return getMachineUnlocalizedName(metaId) ?: "Overpowered_UnknownMachine"
	}

	fun getMetaTileEntityById(metaId: Int) = GregTechAPI.METATILEENTITIES.getOrNull(metaId)

	val configurationCircuits: Collection<ItemStack>
		get() = MixinAccessorBridge.getRealConfigurationList().values()

	fun isConfigurationCircuit(stack: ItemStack): Boolean =
		configurationCircuits.any { GTUtility.areStacksEqual(stack, it) }

	/**
	 * @see GT_Utility.getTier
	 */
	@MagicConstant(valuesFromClass = VoltageIndex::class)
	fun getVoltagePracticalTier(eut: Long): Int {
		var i = -1
		while(++i < GTValues.VP.size) if(eut <= GTValues.VP[i]) return i
		return GTValues.VP.size - 1
	}

	fun checkMetaIdCollision(metaId: Int) {
		val occupiedObject = GregTechAPI.METATILEENTITIES[metaId]
		if(occupiedObject != null) {
			throw IllegalArgumentException(
				"MetaTileId collision occurred, id $metaId, has been occupied by ${occupiedObject.metaName} (${occupiedObject.javaClass})"
			)
		}
	}

}
