package cn.taskeren.op.mixin.late;

import appeng.api.config.Upgrades;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.helpers.DualityInterface;
import cn.taskeren.op.ae.OP_AEUpgrades;
import cn.taskeren.op.gt.utils.GTApi;
import codechicken.lib.inventory.InventoryUtils;
import com.llamalad7.mixinextras.sugar.Local;
import gregtech.api.interfaces.IConfigurationCircuitSupport;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mixin(value = DualityInterface.class, remap = false)
public abstract class ProInterface_DualityInterface_Mixin {

    @Shadow
    private List<ItemStack> waitingToSend;

    @Shadow
    public abstract IInventory getStorage();

    @Shadow
    public abstract int getInstalledUpgrades(Upgrades u);

    @Inject(method = "pushPattern", at = @At(value = "INVOKE", target = "Lappeng/helpers/DualityInterface;pushItemsOut(Ljava/util/EnumSet;)Z", ordinal = 0))
    private void op$pushItemsOut(ICraftingPatternDetails patternDetails, InventoryCrafting table, CallbackInfoReturnable<Boolean> cir, @Local(name = "te") TileEntity tile) {
        if(getInstalledUpgrades(OP_AEUpgrades.getProgrammedUpgrade()) < 1) { // requires the upgrade installed
            return;
        }

        if(tile instanceof BaseMetaTileEntity) {
            BaseMetaTileEntity bmte = (BaseMetaTileEntity) tile;
            IConfigurationCircuitSupport circuitSupport = bmte.getConfigurationCircuitSupport();
            if(circuitSupport != null) {
                // get the first circuit stack in the items that will be inserted to the machine
                Optional<ItemStack> circuitStackOptional = waitingToSend.stream()
                    .filter(Objects::nonNull)
                    .filter(GTApi.INSTANCE::isConfigurationCircuit).findFirst();

                circuitStackOptional.ifPresent(circuitStack -> {
                    if (circuitSupport.getConfigurationCircuits().stream().anyMatch(allowedStack -> GT_Utility.areStacksEqual(allowedStack, circuitStack))) {
                        // set the circuit slot to the circuit stack
                        int circuitSlot = circuitSupport.getCircuitSlot();
                        bmte.getMetaTileEntity().setInventorySlotContents(circuitSlot, circuitStack.copy());
                        // remove the circuit stack from items that will be inserted to the machine
                        waitingToSend.remove(circuitStack);
                        // and return the circuit stack to the interface storage
                        InventoryUtils.insertItem(getStorage(), circuitStack, false);
                    }
                });
            }
        }
    }

}
