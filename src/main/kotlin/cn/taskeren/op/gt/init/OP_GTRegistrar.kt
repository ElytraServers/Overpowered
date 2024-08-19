package cn.taskeren.op.gt.init

import cn.taskeren.op.gt.registerMachine
import cn.taskeren.op.gt.single.OP_ActiveTransformerRack
import cn.taskeren.op.gt.single.OP_OverpowerMachine

object OP_GTRegistrar {

	fun registerAllMachines() {
		registerSingleMachines()
	}

	private fun registerSingleMachines() {
		OP_MachineItemList.OverpowerMachine.registerMachine {
			OP_OverpowerMachine(it, "OP_NameOverpowerMachine", "Overpower Machine", 9)
		}
		OP_MachineItemList.ActiveTransformerRack.registerMachine {
			OP_ActiveTransformerRack(it, "OP_NameActiveTransformerRack", "Active Transformer Rack", 9)
		}

	}

}
