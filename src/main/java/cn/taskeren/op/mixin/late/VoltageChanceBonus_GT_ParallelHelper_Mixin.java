package cn.taskeren.op.mixin.late;

import cn.taskeren.op.OP_Logger;
import cn.taskeren.op.api.IVoltageChanceBonus;
import gregtech.api.interfaces.tileentity.IVoidable;
import gregtech.api.util.GT_ParallelHelper;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(value = GT_ParallelHelper.class, remap = false)
public abstract class VoltageChanceBonus_GT_ParallelHelper_Mixin {

    @Unique
    private static final Logger op$LOG = OP_Logger.Companion.logger("Bonus Machine");

    @Shadow
    @Nonnull
    public abstract GT_ParallelHelper setChanceMultiplier(double chanceMultiplier);

    @Inject(method = "setMachine(Lgregtech/api/interfaces/tileentity/IVoidable;ZZ)Lgregtech/api/util/GT_ParallelHelper;", at = @At("HEAD"))
    private void op$setMachine(IVoidable machine, boolean protectExcessItem, boolean protectExcessFluid, CallbackInfoReturnable<GT_ParallelHelper> cir) {
        if(machine instanceof IVoltageChanceBonus) {
            double bonusChance = ((IVoltageChanceBonus) machine).getBonusChance();
            setChanceMultiplier(1.0 + bonusChance);

            op$LOG.info("Voltage Chance Bonus Machine found! Bonus Chance: {}", bonusChance);
        }
    }

}
