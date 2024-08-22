package cn.taskeren.op.gt.utils

import cn.taskeren.op.gt.init.OP_ItemList
import cn.taskeren.op.gt.item.impl.InsuranceReceiptItemBehaviour
import gregtech.api.enums.ItemList
import gregtech.api.enums.TierEU
import gregtech.api.recipe.RecipeMapBuilder
import gregtech.api.util.GT_RecipeBuilder

object OP_FakeRecipe {

	// #tr op.recipe.insurance_counter
	// #en Insurance Counter
	// #zh 保险柜台
	val FakeInsuranceCounterRecipe = RecipeMapBuilder.of("op.recipe.insurance_counter")
		.maxIO(1, 1, 0, 0)
		.minInputs(1, 0)
		.build()

	init {
		FakeInsuranceCounterRecipe.addFakeRecipe(
			false,
			arrayOf(OP_ItemList.InsuranceReceipt.get(1)?.also { InsuranceReceiptItemBehaviour.setBoundMetaId(it, 1189) }),
			arrayOf(ItemList.Generator_Naquadah_Mark_V.getWithName(1, "Exploded Machine")),
			null,
			null,
			null,
			300 * GT_RecipeBuilder.SECONDS,
			TierEU.ULV.toInt(),
			0
		)
		FakeInsuranceCounterRecipe.addFakeRecipe(
			false,
			arrayOf(ItemList.Generator_Naquadah_Mark_V.getWithName(1, "Any Machine")),
			arrayOf(OP_ItemList.InsuranceReceipt.get(1)),
			null,
			null,
			null,
			20,
			TierEU.ULV.toInt(),
			0
		)
	}

}
