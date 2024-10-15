package cn.taskeren.op.gt.utils

import cn.taskeren.op.gt.init.OP_ItemList
import cn.taskeren.op.gt.item.impl.InsuranceReceiptItemBehaviour
import gregtech.api.enums.ItemList
import gregtech.api.enums.TierEU
import gregtech.api.recipe.RecipeMapBuilder
import gregtech.api.util.GTRecipeBuilder

object OP_FakeRecipe {

	// #tr op.recipe.insurance_counter
	// #en Insurance Counter
	// #zh 保险柜台
	val FakeInsuranceCounterRecipe = RecipeMapBuilder.of("op.recipe.insurance_counter")
		.maxIO(1, 1, 0, 0)
		.minInputs(1, 0)
		.build()

	init {
		GTRecipeBuilder.builder()
			.fake()
			.itemInputs(OP_ItemList.InsuranceReceipt.get(1)?.also { InsuranceReceiptItemBehaviour.setBoundMetaId(it, 1189) })
			.itemOutputs(ItemList.Generator_Naquadah_Mark_V.getWithName(1, "Exploded Machine"))
			.duration(300 * GTRecipeBuilder.SECONDS)
			.eut(TierEU.ULV.toInt())
			.addTo(FakeInsuranceCounterRecipe)

		GTRecipeBuilder.builder()
			.fake()
			.itemInputs(ItemList.Generator_Naquadah_Mark_V.getWithName(1, "Any Machine"))
			.itemOutputs(OP_ItemList.InsuranceReceipt.get(1))
			.duration(20)
			.eut(TierEU.ULV.toInt())
			.addTo(FakeInsuranceCounterRecipe)

	}

}
