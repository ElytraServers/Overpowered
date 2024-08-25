package cn.taskeren.op.mixin.late;

import cn.taskeren.op.insurance.InsuranceManager;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BaseMetaTileEntity.class, remap = false)
public class Insurance_BaseMetaTileEntity_Mixin {

    @Inject(method = "doExplosion", at = @At(value = "INVOKE", target = "Lgregtech/api/metatileentity/MetaTileEntity;doExplosion(J)V"))
    private void onExplosion(long aAmount, CallbackInfo ci) {
        BaseMetaTileEntity self = (BaseMetaTileEntity) (Object) this;
        // calls the insurance manager
        InsuranceManager.INSTANCE.onMachineExplode(self, aAmount);
    }

}
