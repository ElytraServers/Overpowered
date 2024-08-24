package cn.taskeren.op.gt.init

import cn.taskeren.op.gt.addRecipe
import cn.taskeren.op.gt.addRecipeSimple
import cn.taskeren.op.gt.item.OP_GeneratedItem
import cn.taskeren.op.gt.item.impl.ActiveTransformerExplosionCoreItemBehaviour
import cn.taskeren.op.gt.item.impl.InsuranceReceiptItemBehaviour
import cn.taskeren.op.gt.registerItem
import cn.taskeren.op.gt.registerMachine
import cn.taskeren.op.gt.single.OP_ActiveTransformerRack
import cn.taskeren.op.gt.single.OP_BalancedOutputHatch
import cn.taskeren.op.gt.single.OP_DebugEnergyHatch
import cn.taskeren.op.gt.single.OP_InsuranceCounter
import cn.taskeren.op.gt.single.OP_OverpowerMachine
import cn.taskeren.op.gt.single.OP_UniHatch
import cn.taskeren.op.gt.utils.PatternRecipeBuilder
import cn.taskeren.op.gt.utils.PatternRecipeBuilder.X
import com.github.technus.tectech.thing.CustomItemList
import com.github.technus.tectech.thing.casing.TT_Container_Casings
import gregtech.api.enums.ItemList
import gregtech.api.enums.Materials
import gregtech.api.enums.TierEU
import gregtech.api.recipe.RecipeMaps
import gregtech.api.util.GT_ModHandler
import gregtech.api.util.GT_RecipeBuilder
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList
import net.minecraft.item.ItemStack
import com.dreammaster.gthandler.CustomItemList as DreamItemList

object OP_GTRegistrar {

	fun registerAllMachines() {
		registerSimpleItems()
		registerSingleMachines()
	}

	private fun registerSimpleItems() = with(OP_GeneratedItem) {
		OP_ItemList.DyingBioChip.registerItem {
			// #tr gt.metaitem.op.32001.name
			// #en Dying Bio Chip

			// #tr gt.metaitem.op.32001.tooltip
			// #en Squeezed Living Bio Chip

			addItem(it, "Dying Bio Chip", "Squeezed Living Bio Chip")
		}
		OP_ItemList.CertifiedElectrician.registerItem {
			// #tr gt.metaitem.op.32002.name
			// #en Certified Electrician
			// #zh 电工资格证
			// #tr gt.metaitem.op.32002.tooltip
			// #en Proof of your qualifications on Electrical Engineering
			// #zh 证明你在电气工程的资历
			addItem(it, "Certified Electrician", "Proof of your qualifications on Electrical Engineering")
		}
		OP_ItemList.InsuranceReceipt.registerItem {
			// #tr gt.metaitem.op.32003.name
			// #en Insurance Receipt
			// #zh 保险单
			addItem(it, "Insurance Receipt", "", InsuranceReceiptItemBehaviour)
		}
		OP_ItemList.ActiveTransformerExplosionCore.registerItem {
			// #tr gt.metaitem.op.32004.name
			// #en Active Transformer Explosion Core
			// #zh 有源变压器爆炸核心
			addItem(it, "Active Transformer Explosion Core", "", ActiveTransformerExplosionCoreItemBehaviour)
		}.addRecipe(RecipeMaps.hammerRecipes) {
			itemInputs(CustomItemList.Machine_Multi_Transformer.get(1)) // active transformer
			itemOutputs(it.copy().also { it.stackSize = 8 })
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_LV)
		}
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
		}.addRecipe(RecipeMaps.assemblerRecipes) {
			itemInputs(ItemStack(TT_Container_Casings.sBlockCasingsTT, 4), DreamItemList.HighEnergyFlowCircuit.get(1))
			itemOutputs(it.copy().also { it.stackSize = 4 })
			duration(8 * GT_RecipeBuilder.SECONDS)
			eut(TierEU.RECIPE_LuV)
		}

		// #tr gt.blockmachines.insurance_counter.name
		// #en Galactic Inc. Insurance Counter
		// #zh 星际保险公司柜台
		OP_MachineItemList.InsuranceCounter.registerMachine {
			OP_InsuranceCounter(it, "insurance_counter", "Galactic Inc. Insurance Counter", 1)
		}.addRecipeSimple {
			with(PatternRecipeBuilder) {
				GT_ModHandler.addCraftingRecipe(
					it,
					arrayOf(
						"WCW",
						"XHX",
						"WCW",
						'W',
						X.WIRE,
						'C',
						X.CIRCUIT,
						'X',
						GregtechItemList.TransmissionComponent_LV.get(1),
						'H',
						X.HULL
					).withTier(1)
				)
			}
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
			itemInputs(
				ItemList.Hatch_Output_HV.get(1),
				ItemList.Cover_FluidStorageMonitor.get(1),
				ItemList.Cover_Controller.get(1)
			)
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
			itemInputs(
				ItemList.Hatch_Output_LuV.get(1),
				ItemList.Cover_FluidStorageMonitor.get(1),
				ItemList.Cover_Controller.get(1)
			)
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
			itemInputs(
				ItemList.Hatch_Output_MAX.get(1),
				ItemList.Cover_FluidStorageMonitor.get(1),
				ItemList.Cover_Controller.get(1)
			)
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
