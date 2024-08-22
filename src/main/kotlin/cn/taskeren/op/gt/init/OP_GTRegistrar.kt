package cn.taskeren.op.gt.init

import cn.taskeren.op.gt.addRecipe
import cn.taskeren.op.gt.addRecipeSimple
import cn.taskeren.op.gt.registerMachine
import cn.taskeren.op.gt.single.OP_ActiveTransformerRack
import cn.taskeren.op.gt.single.OP_BalancedOutputHatch
import cn.taskeren.op.gt.single.OP_DebugEnergyHatch
import cn.taskeren.op.gt.single.OP_InsuranceCounter
import cn.taskeren.op.gt.single.OP_OverpowerMachine
import cn.taskeren.op.gt.single.OP_UniHatch
import cn.taskeren.op.gt.utils.PatternRecipeBuilder
import cn.taskeren.op.gt.utils.PatternRecipeBuilder.X
import gregtech.api.enums.GT_Values
import gregtech.api.enums.ItemList
import gregtech.api.enums.Materials
import gregtech.api.enums.TierEU
import gregtech.api.recipe.RecipeMaps
import gregtech.api.util.GT_ModHandler
import gregtech.api.util.GT_RecipeBuilder
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList

object OP_GTRegistrar {

	fun registerAllMachines() {
		registerSingleMachines()
	}

	private fun registerSingleMachines() {
		// #tr gt.blockmachines.overpower_machine.name
		// #en Incredible Malicious Machine Overpowering System
		// #zh 超级绝绝子机器强化系统
		OP_MachineItemList.OverpowerMachine.registerMachine {
			OP_OverpowerMachine(it, "overpower_machine", "Incredible Malicious Machine Overpowering System", 9)
		}

		// #tr gt.blockmachines.active_transformer_rack.name
		// #en Active Transformer Rack
		// #zh 有源变压器机械架
		// #tw 有源變壓器機械架
		OP_MachineItemList.ActiveTransformerRack.registerMachine {
			OP_ActiveTransformerRack(it, "active_transformer_rack", "Active Transformer Rack", 9)
		}

		// #tr gt.blockmachines.insurance_counter.name
		// #en Galactic Inc. Insurance Counter
		// #zh 星际保险公司柜台
		OP_MachineItemList.InsuranceCounter.registerMachine {
			OP_InsuranceCounter(it, "insurance_counter", "Galactic Inc. Insurance Counter", 1)
		}.addRecipeSimple {
			GT_ModHandler.addCraftingRecipe(it, PatternRecipeBuilder.format(1, arrayOf("WCW", "XHX", "WCW", 'W', X.WIRE, 'C', X.CIRCUIT, 'X', GregtechItemList.TransmissionComponent_LV, 'H', X.HULL)))
		}

		// region UniHatch

		// #tr gt.blockmachines.uni_hatch_ulv.name
		// #en UniHatch ULV
		// #zh 组合式输入仓 ULV
		OP_MachineItemList.UniHatch_ULV.registerMachine {
			OP_UniHatch(it, "uni_hatch_ulv", "UniHatch ULV", 0)
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
		// #tr gt.blockmachines.uni_hatch_lv.name
		// #en UniHatch LV
		// #zh 组合式输入仓 LV
		OP_MachineItemList.UniHatch_LV.registerMachine {
			OP_UniHatch(it, "uni_hatch_lv", "UniHatch LV", 1)
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
		// #tr gt.blockmachines.uni_hatch_mv.name
		// #en UniHatch MV
		// #zh 组合式输入仓 MV
		OP_MachineItemList.UniHatch_MV.registerMachine {
			OP_UniHatch(it, "uni_hatch_mv", "UniHatch MV", 2)
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
		// #tr gt.blockmachines.uni_hatch_hv.name
		// #en UniHatch HV
		// #zh 组合式输入仓 HV
		OP_MachineItemList.UniHatch_HV.registerMachine {
			OP_UniHatch(it, "uni_hatch_hv", "UniHatch HV", 3)
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
		// #tr gt.blockmachines.uni_hatch_ev.name
		// #en UniHatch EV
		// #zh 组合式输入仓 EV
		OP_MachineItemList.UniHatch_EV.registerMachine {
			OP_UniHatch(it, "uni_hatch_ev", "UniHatch EV", 4)
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

		// #tr gt.blockmachines.balanced_output_hatch_hv.name
		// #en Balanced Output Hatch HV
		// #zh 平衡输出仓 HV
		OP_MachineItemList.BalancedOutputHatch_HV.registerMachine {
			OP_BalancedOutputHatch(it, "balanced_output_hatch_hv", "Balanced Output Hatch HV", 3)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hatch_Output_HV.get(1), ItemList.Cover_FluidStorageMonitor.get(1), ItemList.Cover_Controller.get(1))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_HV)
		}
		// #tr gt.blockmachines.balanced_output_hatch_luv.name
		// #en Balanced Output Hatch LuV
		// #zh 平衡输出仓 LuV
		OP_MachineItemList.BalancedOutputHatch_LuV.registerMachine {
			OP_BalancedOutputHatch(it, "balanced_output_hatch_luv", "Balanced Output Hatch LuV", 6)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hatch_Output_LuV.get(1), ItemList.Cover_FluidStorageMonitor.get(1), ItemList.Cover_Controller.get(1))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_HV)
		}
		// #tr gt.blockmachines.balanced_output_hatch_uhv.name
		// #en Balanced Output Hatch UHV
		// #zh 平衡输出仓 UHV
		OP_MachineItemList.BalancedOutputHatch_UHV.registerMachine {
			OP_BalancedOutputHatch(it, "balanced_output_hatch_uhv", "Balanced Output Hatch HV", 9)
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemList.Hatch_Output_MAX.get(1), ItemList.Cover_FluidStorageMonitor.get(1), ItemList.Cover_Controller.get(1))
			itemOutputs(it)
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_HV)
		}

		// endregion

		// #tr gt.blockmachines.debug_energy_hatch.name
		// #en Ascendant Realm Paracausal Manipulating Unit
		// #zh 上维领域超因果单元
		// #tw 高等維度超因果單元
		OP_MachineItemList.DebugEnergyHatch.registerMachine {
			OP_DebugEnergyHatch(it, "debug_energy_hatch", "Ascendant Realm Paracausal Manipulating Unit")
		}
	}

}

internal fun addRecipe(block: GT_RecipeBuilder.() -> Unit) {
	GT_Values.RA.stdBuilder().apply(block)
}
