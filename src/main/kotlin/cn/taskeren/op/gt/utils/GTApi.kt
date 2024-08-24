package cn.taskeren.op.gt.utils

import gregtech.api.GregTech_API
import net.minecraft.item.ItemStack

object GTApi {

	val MachineBlock get() = GregTech_API.sBlockMachines

	fun getMachineItemStack(metaId: Int): ItemStack {
		require(getMetaTileEntityById(metaId) == null) { "Invalid MetaTileId: $metaId" }
		return ItemStack(MachineBlock, 1, metaId)
	}

	fun getMetaTileEntityById(metaId: Int) = GregTech_API.METATILEENTITIES.getOrNull(metaId)

}
