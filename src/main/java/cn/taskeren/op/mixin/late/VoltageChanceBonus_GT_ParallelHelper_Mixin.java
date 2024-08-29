package cn.taskeren.op.mixin.late;

import cn.taskeren.op.OP;
import cn.taskeren.op.gt.recipe_chance_bonus.ChanceBonusManager;
import cn.taskeren.op.gt.utils.GTApi;
import gregtech.api.interfaces.tileentity.IVoidable;
import gregtech.api.util.GT_ParallelHelper;
import gregtech.api.util.GT_Recipe;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.OptionalDouble;

@Mixin(value = GT_ParallelHelper.class, remap = false)
public abstract class VoltageChanceBonus_GT_ParallelHelper_Mixin {

    @Shadow
    private GT_Recipe recipe;

    @Shadow
    private double chanceMultiplier;

    @Shadow
    private IVoidable machine;

    @Unique
    private double op$chanceMultiplier;

    // re-calculate the chance multiplier and cache it to "op$chanceMultiplier"
    @Redirect(method = "determineParallel", at = @At(value = "FIELD", target = "Lgregtech/api/util/GT_ParallelHelper;chanceMultiplier:D", opcode = Opcodes.GETFIELD))
    private double op$determineParallel(GT_ParallelHelper instance) {
        // get the bonus chance multiplier
        OptionalDouble bonusOptional = ChanceBonusManager.getChanceBonusOptional(machine, GTApi.INSTANCE.getVoltagePracticalTier(recipe.mEUt), chanceMultiplier);

        // write the value to the modified chance multiplier
        op$chanceMultiplier = bonusOptional.isPresent()
            ? (OP.INSTANCE.getAccumulativeChanceBonus() ? chanceMultiplier : 1.0) + bonusOptional.getAsDouble()
            : chanceMultiplier;

        // return the modified chance multiplier
        return op$chanceMultiplier;
    }

    // read the modified chance multiplier from "determineParallel" mixin
    @Redirect(method = "calculateItemOutputs", at = @At(value = "FIELD", target = "Lgregtech/api/util/GT_ParallelHelper;chanceMultiplier:D", opcode = Opcodes.GETFIELD))
    private double op$calculateItemOutputs(GT_ParallelHelper instance) {
        return op$chanceMultiplier;
    }

}
