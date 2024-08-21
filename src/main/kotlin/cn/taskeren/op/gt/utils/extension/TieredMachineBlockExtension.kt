package cn.taskeren.op.gt.utils.extension

import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock

val GT_MetaTileEntity_TieredMachineBlock.tier get() = mTier.toInt()
