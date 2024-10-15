package cn.taskeren.op.gt.utils.extension

import gregtech.api.metatileentity.implementations.MTETieredMachineBlock

val MTETieredMachineBlock.tier get() = mTier.toInt()
