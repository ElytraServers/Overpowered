package cn.taskeren.op.gt.init

import cn.taskeren.op.gt.addRecipe
import cn.taskeren.op.gt.registerMachine
import cn.taskeren.op.gt.single.OP_ActiveTransformerRack
import cn.taskeren.op.gt.single.OP_BalancedOutputHatch
import cn.taskeren.op.gt.single.OP_DebugEnergyHatch
import cn.taskeren.op.gt.single.OP_OverpowerMachine
import cn.taskeren.op.gt.single.OP_UniHatch
import gregtech.api.enums.GT_Values
import gregtech.api.enums.ItemList
import gregtech.api.enums.Materials
import gregtech.api.enums.TierEU
import gregtech.api.recipe.RecipeMaps
import gregtech.api.util.GT_RecipeBuilder

object OP_GTRegistrar {

	fun registerAllMachines() {
		registerSingleMachines()
	}

	private fun registerSingleMachines() {
		// #tr OP_NameOverpowerMachine
		// #en Incredible Malicious Machine Overpowering System
		// #zh 超级绝绝子机器强化系统
		OP_MachineItemList.OverpowerMachine.registerMachine {
			OP_OverpowerMachine(it, "OP_NameOverpowerMachine", "Incredible Malicious Machine Overpowering System", 9)
		}

		// #tr OP_NameActiveTransformerRack
		// #en Active Transformer Rack
		// #zh 有源变压器机械架
		// #tw 有源變壓器機械架
		OP_MachineItemList.ActiveTransformerRack.registerMachine {
			OP_ActiveTransformerRack(it, "OP_NameActiveTransformerRack", "Active Transformer Rack", 9)
		}

		// region UniHatch

		// #tr OP_NameUniHatch_ULV
		// #en UniHatch ULV
		// #zh 组合式输入仓 ULV
		OP_MachineItemList.UniHatch_ULV.registerMachine {
			OP_UniHatch(it, "OP_NameUniHatch_ULV", "UniHatch ULV", 0)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_ULV.get(1), ItemList.Hatch_Input_Bus_ULV.get(1), ItemList.Hatch_Input_ULV.get(1))
			fluidInputs(Materials.Glue.getFluid(2L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(16 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_ULV)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_ULV.get(1), ItemList.Hatch_Input_Bus_ULV.get(1), ItemList.Hatch_Input_ULV.get(1))
			fluidInputs(Materials.AdvancedGlue.getFluid(1L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_ULV)
		}
		// #tr OP_NameUniHatch_LV
		// #en UniHatch LV
		// #zh 组合式输入仓 LV
		OP_MachineItemList.UniHatch_LV.registerMachine {
			OP_UniHatch(it, "OP_NameUniHatch_LV", "UniHatch LV", 1)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_LV.get(1), ItemList.Hatch_Input_Bus_LV.get(1), ItemList.Hatch_Input_LV.get(1))
			fluidInputs(Materials.AdvancedGlue.getFluid(4L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(16 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_LV)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_LV.get(1), ItemList.Hatch_Input_Bus_LV.get(1), ItemList.Hatch_Input_LV.get(1))
			fluidInputs(Materials.Plastic.getMolten(2L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_LV)
		}
		// #tr OP_NameUniHatch_MV
		// #en UniHatch MV
		// #zh 组合式输入仓 MV
		OP_MachineItemList.UniHatch_MV.registerMachine {
			OP_UniHatch(it, "OP_NameUniHatch_MV", "UniHatch MV", 2)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_MV.get(1), ItemList.Hatch_Input_Bus_MV.get(1), ItemList.Hatch_Input_MV.get(1))
			fluidInputs(Materials.Plastic.getMolten(16L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(16 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_MV)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_MV.get(1), ItemList.Hatch_Input_Bus_MV.get(1), ItemList.Hatch_Input_MV.get(1))
			fluidInputs(Materials.PolyvinylChloride.getMolten(4L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_MV)
		}
		// #tr OP_NameUniHatch_HV
		// #en UniHatch HV
		// #zh 组合式输入仓 HV
		OP_MachineItemList.UniHatch_HV.registerMachine {
			OP_UniHatch(it, "OP_NameUniHatch_HV", "UniHatch HV", 3)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_HV.get(1), ItemList.Hatch_Input_Bus_HV.get(1), ItemList.Hatch_Input_HV.get(1))
			fluidInputs(Materials.PolyvinylChloride.getMolten(24L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(16 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_HV)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_HV.get(1), ItemList.Hatch_Input_Bus_HV.get(1), ItemList.Hatch_Input_HV.get(1))
			fluidInputs(Materials.Polytetrafluoroethylene.getMolten(4L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_HV)
		}
		// #tr OP_NameUniHatch_EV
		// #en UniHatch EV
		// #zh 组合式输入仓 EV
		OP_MachineItemList.UniHatch_EV.registerMachine {
			OP_UniHatch(it, "OP_NameUniHatch_EV", "UniHatch EV", 4)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_EV.get(1), ItemList.Hatch_Input_Bus_EV.get(1), ItemList.Hatch_Input_EV.get(1))
			fluidInputs(Materials.Polytetrafluoroethylene.getMolten(32L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(16 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_EV)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hull_EV.get(1), ItemList.Hatch_Input_Bus_EV.get(1), ItemList.Hatch_Input_EV.get(1))
			fluidInputs(Materials.Polybenzimidazole.getMolten(16L * GT_RecipeBuilder.INGOTS))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_EV)
		}

		// endregion

		// region Balanced Output Hatch

		OP_MachineItemList.BalancedOutputHatch_HV.registerMachine {
			OP_BalancedOutputHatch(it, "OP_NameBalancedOutputHatch_HV", "Balanced Output Hatch HV", 3)
		}

		OP_MachineItemList.BalancedOutputHatch_LuV.registerMachine {
			OP_BalancedOutputHatch(it, "OP_NameBalancedOutputHatch_LuV", "Balanced Output Hatch LuV", 6)
		}

		OP_MachineItemList.BalancedOutputHatch_UHV.registerMachine {
			OP_BalancedOutputHatch(it, "OP_NameBalancedOutputHatch_UHV", "Balanced Output Hatch HV", 9)
		}

		// endregion

		// #tr OP_NameDebugEnergyHatch
		// #en Ascendant Realm Paracausal Manipulating Unit
		// #zh 上维领域超因果单元
		// #tw 高等維度超因果單元
		OP_MachineItemList.DebugEnergyHatch.registerMachine {
			OP_DebugEnergyHatch(it, "OP_NameDebugEnergyHatch", "Ascendant Realm Paracausal Manipulating Unit")
		}
	}

}

internal fun addRecipe(block: GT_RecipeBuilder.() -> Unit) {
	GT_Values.RA.stdBuilder().apply(block)
}
