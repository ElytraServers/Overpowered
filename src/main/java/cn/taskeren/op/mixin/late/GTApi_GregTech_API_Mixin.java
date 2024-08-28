package cn.taskeren.op.mixin.late;

import com.google.common.collect.Multimap;
import gregtech.api.GregTech_API;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GregTech_API.class, remap = false)
public interface GTApi_GregTech_API_Mixin {

    @Accessor("sRealConfigurationList")
    static Multimap<Integer, ItemStack> getRealConfigurationList() {
        throw new AssertionError();
    }

}
