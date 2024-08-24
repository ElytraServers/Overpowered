package cn.taskeren.op.gt.init

import cn.taskeren.op.gt.IdItemContainer
import cn.taskeren.op.gt.addRecipe

/**
 * This object is used to store the tasks to be executed later in the lifecycle, including FML Init and PostInit.
 * PreInit should be executed immediately because tasks submitted to here are from pre-init phase.
 *
 * @see cn.taskeren.op.gt.IdItemContainer.addRecipe
 */
object LazyScheduler {

	private class LazyException: Exception("Dummy exception. See call site below, and error message in cause exception")

	data class Action(val action: Runnable, val dummyException: Exception = LazyException())

	private val onInit: MutableList<Action> = mutableListOf()
	private val onPostInit: MutableList<Action> = mutableListOf()

	fun scheduleInit(block: () -> Unit) {
		onInit.add(Action(block))
	}

	fun schedulePostInit(block: () -> Unit) {
		onPostInit.add(Action(block))
	}

	private fun MutableList<Action>.run() {
		for(action in this) {
			try {
				action.action.run()
			} catch(ex: Exception) {
				 action.dummyException.initCause(ex)
				 throw action.dummyException
			}
		}
		this.clear()
	}

	fun runInit() = onInit.run()
	fun runPostInit() = onPostInit.run()

}
