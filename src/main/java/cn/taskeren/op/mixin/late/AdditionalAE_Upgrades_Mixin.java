package cn.taskeren.op.mixin.late;

import appeng.api.config.Upgrades;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(value = Upgrades.class, remap = false)
@Unique
public abstract class AdditionalAE_Upgrades_Mixin {

    /*

    Added an extra instance to `Upgrades` enum.

    This tricky method is from https://github.com/SpongePowered/Mixin/issues/387#issuecomment-888408556

    But there is a potential bug that in the runtime the new created instance is added to the $VALUES twice, and
    I cannot find the reason it is added twice. But it works for now, so I assume it affect nothing.

    You should get the instance from `OP_AEUpgrades`.

    */

    @Shadow
    @Final
    @Mutable
    private static Upgrades[] $VALUES;

    private static final Upgrades PROGRAMMED = op$createInstance("PROGRAMMED", 1);

    @Invoker("<init>")
    public static Upgrades op$constructor(String name, int ordinal, int tier) {
        throw new AssertionError();
    }

    private static Upgrades op$createInstance(String name, int tier) {
        // get existing instances
        ArrayList<Upgrades> values = new ArrayList<>(Arrays.asList($VALUES));
        // create the new instance
        Upgrades value = op$constructor(name, values.get(values.size() - 1).ordinal() + 1, tier);
        // add the new instance to the instance cache list
        values.add(value);
        // overwrite the $VALUES to the value list with the new instance
        $VALUES = values.toArray(new Upgrades[0]);
        // return the new instance
        return value;
    }

}
