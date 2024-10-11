package cn.taskeren.op.mixin.late;

import cn.taskeren.op.gt.recipe_chance_bonus.ChanceBonusManager;
import cn.taskeren.op.gt.utils.GTApi;
import gregtech.api.interfaces.tileentity.IVoidable;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.ParallelHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalDouble;

@Mixin(value = ParallelHelper.class, remap = false)
public abstract class VoltageChanceBonus_GT_ParallelHelper_Mixin {

    @Shadow
    private GTRecipe recipe;

    @Shadow
    private double chanceMultiplier;

    @Shadow
    private IVoidable machine;

    @Inject(method = "determineParallel", at = @At("HEAD"))
    private void op$determineParallel(CallbackInfo ci) {
        OptionalDouble bonusOptional = ChanceBonusManager.getChanceBonusOptional(machine, GTApi.INSTANCE.getVoltagePracticalTier(recipe.mEUt), chanceMultiplier);
        if(bonusOptional.isPresent()) {
            recipe = ChanceBonusManager.copyAndBonusChance(recipe, bonusOptional.getAsDouble());
        }
    }

}
