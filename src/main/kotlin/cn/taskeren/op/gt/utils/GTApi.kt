package cn.taskeren.op.gt.utils

import cn.taskeren.op.utils.MixinAccessorBridge
import gregtech.api.GregTech_API
import gregtech.api.enums.GTVoltageIndex
import gregtech.api.enums.GT_Values
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.util.GT_Utility
import net.minecraft.item.ItemStack
import org.intellij.lang.annotations.MagicConstant

object GTApi {

	val MachineBlock get() = GregTech_API.sBlockMachines

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

	fun getMetaTileEntityById(metaId: Int) = GregTech_API.METATILEENTITIES.getOrNull(metaId)

	val configurationCircuits: Collection<ItemStack>
		get() = MixinAccessorBridge.getRealConfigurationList().values()

	fun isConfigurationCircuit(stack: ItemStack): Boolean =
		configurationCircuits.any { GT_Utility.areStacksEqual(stack, it) }

	/**
	 * @see GT_Utility.getTier
	 */
	@MagicConstant(valuesFromClass = GTVoltageIndex::class)
	fun getVoltagePracticalTier(eut: Long): Int {
		var i = -1
		while(++i < GT_Values.VP.size) if(eut <= GT_Values.VP[i]) return i
		return GT_Values.VP.size - 1
	}

}
