package cn.taskeren.op.debug

import cn.taskeren.op.OP_Logger
import gregtech.api.enums.GTValues
import gregtech.api.enums.TierEU
import gregtech.api.recipe.RecipeMaps
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

object DebugRegistrar : OP_Logger {

	fun addDebugRecipes() {
		logger.info("Adding zero-chance recipes")
		GTValues.RA.stdBuilder()
			.itemInputs(ItemStack(Blocks.bedrock))
			.itemOutputs(ItemStack(Items.apple), ItemStack(Items.golden_apple))
			.outputChances(10000, 0)
			.duration(20)
			.eut(TierEU.RECIPE_ULV)
			.setNEIDesc("Added by Overpowered for debug purposes")
			.addTo(RecipeMaps.maceratorRecipes)
	}

}
