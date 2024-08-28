package cn.taskeren.op.mixin.late;

import appeng.api.config.Upgrades;
import appeng.parts.automation.UpgradeInventory;
import cn.taskeren.op.ae.OP_AEUpgrades;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = UpgradeInventory.class, remap = false)
public abstract class AdditionalAE_UpgradeInventory_Mixin {

    /*

    The additional upgrade items are registered as `OP_GeneratedAEUpgradeItem` and it implements IUpgradeModule, so we don't need to
    do extra things in `isItemValidForSlot`.

    In this class, we should manually count our custom upgrades and return the right value in the method.

    */

    @Unique
    private int op$programmedUpgrades = 0;

    @Inject(method = "updateUpgradeInfo", at = @At("HEAD"))
    private void op$updateUpgradeInfo_cleanse(CallbackInfo ci) {
        op$programmedUpgrades = 0;
    }

    @Inject(method = "updateUpgradeInfo", at = @At(value = "INVOKE_ASSIGN", target = "Lappeng/api/implementations/items/IUpgradeModule;getType(Lnet/minecraft/item/ItemStack;)Lappeng/api/config/Upgrades;"))
    private void op$updateUpgradeInfo_count(CallbackInfo ci, @Local(name = "myUpgrade") Upgrades myUpgrade) {
        if(myUpgrade == OP_AEUpgrades.getProgrammedUpgrade()) {
            op$programmedUpgrades++;
        }
    }

    @Inject(method = "getInstalledUpgrades", at = @At("RETURN"), cancellable = true)
    private void op$getInstalledUpgrades(Upgrades u, CallbackInfoReturnable<Integer> cir) {
        // overwrite
        if (u == OP_AEUpgrades.getProgrammedUpgrade()) {
            cir.setReturnValue(op$programmedUpgrades);
        }
    }


}
