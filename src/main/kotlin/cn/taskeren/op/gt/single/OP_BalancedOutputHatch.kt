package cn.taskeren.op.gt.single

import cn.taskeren.op.gt.utils.InfoDataBuilder
import cn.taskeren.op.gt.utils.OP_Text
import cn.taskeren.op.gt.utils.extension.tier
import cn.taskeren.op.translated
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.FluidStack
import kotlin.math.max
import kotlin.math.min

class OP_BalancedOutputHatch : GT_MetaTileEntity_Hatch_Output {

	private val descriptionOverride: Array<String>

	constructor(aID: Int, aName: String, aNameRegional: String, aTier: Int) : super(aID, aName, aNameRegional, aTier) {
		descriptionOverride = arrayOf(
			// #tr BalancedOutputHatch_Tooltip_1
			// #en Fluid Output for Multiblocks
			// #zh 流体的多方块输出
			translated("BalancedOutputHatch_Tooltip_1"),
			// #tr BalancedOutputHatch_Tooltip_2
			// #en Capacity: %s
			// #zh 容量：%s
			translated("BalancedOutputHatch_Tooltip_2", "${InfoDataBuilder.tiered(tier)}${InfoDataBuilder.numberFormatted(capacity)}L"),
			// #tr BalancedOutputHatch_Tooltip_3
			// #en Balanced Output Hatch will keep the fluids half of its capacity.
			// #zh 平衡输出仓会保持它容量一半的液体
			translated("BalancedOutputHatch_Tooltip_3"),
			OP_Text.TOOLTIP_CREDIT_NOVELTY,
		)
	}

	constructor(aName: String, aTier: Int, aDescriptionArray: Array<String>, aTextures: Array<Array<Array<ITexture>>>?) : super(aName, aTier, aDescriptionArray, aTextures) {
		descriptionOverride = aDescriptionArray
	}

	override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): MetaTileEntity? {
		return OP_BalancedOutputHatch(mName, tier, descriptionOverride, mTextures)
	}

	override fun getDescription(): Array<String> {
		return descriptionOverride
	}

	/**
	 * The amount of overflow part of the half of capacity.
	 */
	val drainableAmount: Int get() {
		if(drainableStack == null) return 0
		return max(0, drainableStack.amount - capacity / 2)
	}

	override fun drain(maxDrain: Int, doDrain: Boolean): FluidStack? {
		if(!canTankBeEmptied()) return null
		val drainableStack = drainableStack ?: return null

		if(drainableStack.amount <= 0) {
			if(isFluidChangingAllowed) {
				setDrainableStack(null)
				baseMetaTileEntity.markDirty()
			}
			return null
		}

		// the number of the fluids to be drained
		// it should not be greater than (>=) the half of [capacity]
		// and it should be less than or equals to the [maxDrain]
		val drainAmount = min(drainableAmount, maxDrain)

		val drainedStack = drainableStack.copy()
		drainedStack.amount = drainAmount

		if(doDrain) {
			drainableStack.amount -= drainAmount
			if(drainableStack.amount <= 0 && isFluidChangingAllowed) {
				setDrainableStack(null)
			}
			baseMetaTileEntity.markDirty()
		}

		return drainedStack
	}

	override fun drain(side: ForgeDirection?, aFluid: FluidStack?, doDrain: Boolean): FluidStack? {
		if(aFluid == null || fluid == null || !aFluid.isFluidEqual(fluid)) return null
		return drain(aFluid.amount, doDrain)
	}

	override fun drain(side: ForgeDirection?, maxDrain: Int, doDrain: Boolean): FluidStack? {
		return drain(maxDrain, doDrain)
	}

	/*
	*
	* @Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (getDrainableStack() == null || !canTankBeEmptied()) return null;
		if (getDrainableStack().amount <= 0 && isFluidChangingAllowed()) {
			setDrainableStack(null);
			getBaseMetaTileEntity().markDirty();
			return null;
		}

		if(getDrainableAmount() <= 0) return null;

		int used = Math.min(getDrainableAmount(), maxDrain);
		if (getDrainableStack().amount < used) used = getDrainableStack().amount;

		if (doDrain) {
			getDrainableStack().amount -= used;
			getBaseMetaTileEntity().markDirty();
		}

		FluidStack drained = getDrainableStack().copy();
		drained.amount = used;

		if (getDrainableStack().amount <= 0 && isFluidChangingAllowed()) {
			setDrainableStack(null);
			getBaseMetaTileEntity().markDirty();
		}

		return drained;
	}
	* */

}
