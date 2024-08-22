package cn.taskeren.op.insurance

import cn.taskeren.op.mc.util.forEachCompound
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.server.MinecraftServer
import net.minecraft.world.WorldSavedData
import java.util.UUID

class InsuranceWorldSavedData(name: String) : WorldSavedData(name) {

	// uuid (player) ⇒ list of MetaTileId
	private var explodedMachines: MutableMap<String, MutableList<Int>> = mutableMapOf()

	fun addInsuranceInfo(uuid: UUID, machineMetaTileId: Int) {
		explodedMachines.getOrPut(uuid.toString(), ::mutableListOf).add(machineMetaTileId)
		markDirty()
	}

	fun getInsuranceInfo(uuid: UUID): List<Int> {
		return explodedMachines[uuid.toString()].orEmpty()
	}

	fun clearInsuranceInfo(uuid: UUID) {
		val id = uuid.toString()
		if(id in explodedMachines) {
			explodedMachines -= id
			markDirty()
		}
	}

	fun removeInsuranceInfo(uuid: UUID, machineMetaTileId: Int): Boolean {
		val list = explodedMachines[uuid.toString()] ?: return false
		val flag =  list.remove(machineMetaTileId)
		if(flag) markDirty()
		return flag
	}

	override fun readFromNBT(nbt: NBTTagCompound) {
		explodedMachines = mutableMapOf<String, MutableList<Int>>().apply {
			nbt.getTagList("ExplodedMachines", 10).forEachCompound {
				val ownerUUID = it.getString("UUID")
				val lostMachines = it.getIntArray("Machines")
				put(ownerUUID, lostMachines.toMutableList())
			}
		}
	}

	override fun writeToNBT(nbt: NBTTagCompound) {
		// exploded machines
		nbt.setTag("ExplodedMachines", NBTTagList().apply {
			explodedMachines.forEach { uuid, lostMachines ->
				appendTag(NBTTagCompound().apply {
					setString("UUID", uuid)
					setIntArray("Machines", lostMachines.toIntArray())
				})
			}
		})
	}

	companion object {

		/**
		 * @return the [InsuranceWorldSavedData] instance; if absent, create a new one and register the world.
		 */
		fun get(): InsuranceWorldSavedData {
			val world = MinecraftServer.getServer().entityWorld
			val data = world.mapStorage
				.loadData(InsuranceWorldSavedData::class.java, "InsuranceWorldSavedData") as InsuranceWorldSavedData?

			return if(data != null) {
				data
			} else {
				val newData = InsuranceWorldSavedData("InsuranceWorldSavedData")
				world.mapStorage.setData("InsuranceWorldSavedData", newData)
				newData
			}
		}
	}
}
