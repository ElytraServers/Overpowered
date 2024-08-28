package cn.taskeren.op.mixin.late;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBloodOrb;
import WayofTime.alchemicalWizardry.common.tileEntity.TEWritingTable;
import appeng.helpers.DualityInterface;
import appeng.util.InventoryAdaptor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DualityInterface.class, remap = false)
public class AEBloodMagicCompat_DualityInterface_Mixin {

    @Inject(method = "inventoryCountsAsEmpty", at = @At("RETURN"), cancellable = true)
    private void op$inventoryCountsAsEmpty(TileEntity te, InventoryAdaptor ad, ForgeDirection side, CallbackInfoReturnable<Boolean> cir) {
        if(te instanceof TEWritingTable) {
            TEWritingTable alchemicalTile = (TEWritingTable) te;

            // iterate the inventory to find any itemstack that is not blood orb
            for (int i = 0; i < alchemicalTile.getSizeInventory(); i++) {
                ItemStack stack = alchemicalTile.getStackInSlot(i);
                if (stack != null && !(stack.getItem() instanceof IBloodOrb)) {
                    return; // not the case
                }
            }

            cir.setReturnValue(true); // the case, have nothing but blood orb, ignore that blood orb!
        }
    }

}
