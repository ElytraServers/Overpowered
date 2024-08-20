package cn.taskeren.op.mixin;

import cn.taskeren.op.OP_Logger;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.common.internal.wrapper.ModularGui;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModularGui.class)
public class CrashProof_ModularGui_Mixin {

    @Unique
    private static final Logger overpowered$LOG = OP_Logger.Companion.logger("CrashProof:ModularGui");

    @Redirect(method = "updateScreen", at = @At(value = "INVOKE", target = "Lcom/gtnewhorizons/modularui/api/screen/ModularWindow;update()V"))
    private void op$updateScreen(ModularWindow window) {
        try {
            window.update();
        } catch (Throwable e) {
            overpowered$LOG.error("An exception thrown when updating the modular window! This crash is caught by CrashProof.", e);
            window.closeWindow();
        }
    }


}
