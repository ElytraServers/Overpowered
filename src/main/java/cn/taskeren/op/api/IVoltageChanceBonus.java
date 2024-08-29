package cn.taskeren.op.api;

import gregtech.api.enums.GTVoltageIndex;
import org.intellij.lang.annotations.MagicConstant;

/**
 * The machines with voltage tier chance bonus.
 * <p>
 * The higher tier, the more bonus.
 *
 * @see cn.taskeren.op.gt.recipe_chance_bonus.ChanceBonusManager
 */
public interface IVoltageChanceBonus {

    /**
     * @return the voltage tier; ULV is 0, LV is 1, etc.
     */
    @MagicConstant(valuesFromClass = GTVoltageIndex.class)
    int getVoltageTier();

    /**
     * @return the tier that tiers after this tier are getting bonus.
     */
    @MagicConstant(valuesFromClass = GTVoltageIndex.class)
    default int getBaseVoltageTier() {
        return GTVoltageIndex.ULV;
    }

    /**
     * @return the bonus chance; 0 does nothing, -0.1 is 10% less, 0.1 is 10% more.
     */
    default double getBonusChance() {
        return getVoltageTier() > getBaseVoltageTier() ? getBonusChancePerVoltage() * (getVoltageTier() - getBaseVoltageTier()) : 0.0;
    }

    default double getBonusChancePerVoltage() {
        return 0.5;
    }

}
