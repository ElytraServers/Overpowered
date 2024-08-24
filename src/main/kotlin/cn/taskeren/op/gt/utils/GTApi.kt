package cn.taskeren.op.gt.utils

import gregtech.api.GregTech_API
import net.minecraft.item.ItemStack

object GTApi {

	val MachineBlock get() = GregTech_API.sBlockMachines

	fun getMachineItemStack(metaId: Int): ItemStack {
		require(getMetaTileEntityById(metaId) != null) { "Invalid MetaTileId: $metaId" }
		return ItemStack(MachineBlock, 1, metaId)
	}

	fun getMachineUnlocalizedName(metaId: Int): String? {
		val mte = getMetaTileEntityById(metaId) ?: return null
		return "gt.blockmachines.${mte.metaName}.name"
	}

	fun getMachineUnlocalizedNameOrUnknownMachine(metaId: Int): String {
		// #tr Overpowered_UnknownMachine
		// #en Unknown Machine
		// #zh 未知机器
		return getMachineUnlocalizedName(metaId) ?: "Overpowered_UnknownMachine"
	}

	fun getMetaTileEntityById(metaId: Int) = GregTech_API.METATILEENTITIES.getOrNull(metaId)

}
