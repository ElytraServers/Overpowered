package cn.taskeren.op.utils;

import cn.taskeren.op.mixin.late.GTApi_GregTech_API_Mixin;
import com.google.common.collect.Multimap;
import net.minecraft.item.ItemStack;

/**
 * This class is a bridge that used to access {@link org.spongepowered.asm.mixin.gen.Accessor}s in Kotlin while not throwing {@link IncompatibleClassChangeError}.
 */
public class MixinAccessorBridge {

    /**
     * @return gregtech.api.GregTech_API#sRealConfigurationList
     */
    public static Multimap<Integer, ItemStack> getRealConfigurationList() {
        return GTApi_GregTech_API_Mixin.getRealConfigurationList();
    }

}
