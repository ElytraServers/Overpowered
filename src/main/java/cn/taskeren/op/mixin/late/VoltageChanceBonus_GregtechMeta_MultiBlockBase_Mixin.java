package cn.taskeren.op.mixin.late;

import cn.taskeren.op.api.IVoltageChanceBonus;
import gregtech.api.enums.GTVoltageIndex;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.base.GregtechMeta_MultiBlockBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

@Mixin(value = GregtechMeta_MultiBlockBase.class, remap = false)
public abstract class VoltageChanceBonus_GregtechMeta_MultiBlockBase_Mixin<T extends GT_MetaTileEntity_ExtendedPowerMultiBlockBase<T>> extends GT_MetaTileEntity_ExtendedPowerMultiBlockBase<T> implements IVoltageChanceBonus {

    @Shadow
    public ArrayList<GT_MetaTileEntity_Hatch> mAllEnergyHatches;

    protected VoltageChanceBonus_GregtechMeta_MultiBlockBase_Mixin(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public int getVoltageTier() {
        Optional<GT_MetaTileEntity_Hatch> minTierEnergyHatch = mAllEnergyHatches.stream().min(Comparator.comparingLong(GT_MetaTileEntity_Hatch::getInputTier));
        return minTierEnergyHatch.isPresent() ? minTierEnergyHatch.get().mTier : -1;
    }

    @Override
    public double getBonusChancePerVoltage() {
        return 0.1;
    }

    @Override
    public int getBaseVoltageTier() {
        return GTVoltageIndex.HV;
    }
}
