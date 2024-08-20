package cn.taskeren.op.gt.init

import cn.taskeren.op.gt.registerMachine
import cn.taskeren.op.gt.single.OP_ActiveTransformerRack
import cn.taskeren.op.gt.single.OP_DebugEnergyHatch
import cn.taskeren.op.gt.single.OP_OverpowerMachine

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
		// #tr OP_NameDebugEnergyHatch
		// #en Ascendant Realm Paracausal Manipulating Unit
		// #zh 上维领域超因果单元
		// #tw 高等維度超因果單元
		OP_MachineItemList.DebugEnergyHatch.registerMachine {
			OP_DebugEnergyHatch(it, "OP_NameDebugEnergyHatch", "Ascendant Realm Paracausal Manipulating Unit")
		}
	}

}
