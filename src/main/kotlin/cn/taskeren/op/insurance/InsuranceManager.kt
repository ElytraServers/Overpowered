package cn.taskeren.op.insurance

import cn.taskeren.op.OP
import cn.taskeren.op.OP_Logger
import cn.taskeren.op.mc.util.sendTranslatedMessage
import gregtech.api.GregTech_API
import gregtech.api.metatileentity.BaseMetaTileEntity
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import java.util.UUID

/**
 * Everything about Insurance is here.
 *
 * All the machines that explode will be registered here, from [cn.taskeren.op.mixin.Insurance_BaseMetaTileEntity_Mixin].
 * Players will have chances to refund their machines back by costing a bunch of resources like Emeralds.
 *
 * Players can see their exploded machines using command `/insurance list`. The listed numbers are the MetaTileEntity IDs.
 */
object InsuranceManager : OP_Logger {

	fun onMachineExplode(bmte: BaseMetaTileEntity) {
		logger.info("{} exploded! owner: {}", bmte, bmte.ownerUuid)

		if(OP.dev || OP.propertyDumpStackTraceOnMachineExplode) {
			logger.info("The machine explosion stacktrace.", Throwable())
		}

		val ownerPlayer =
			MinecraftServer.getServer().configurationManager.playerEntityList.firstOrNull { it.uniqueID == bmte.ownerUuid }

		if(ownerPlayer != null) {
			// #tr Insurance_Message_MachineExploded
			// #en Your machine exploded at %s/%s/%s!
			// #zh 你位于%s/%s/%s的机器爆炸了！
			ownerPlayer.sendTranslatedMessage(
				"Insurance_Message_MachineExploded",
				bmte.xCoord,
				bmte.yCoord,
				bmte.zCoord
			)
		} else {
			logger.info("A machine exploded at ${bmte.xCoord}/${bmte.yCoord}/${bmte.zCoord} and his owner ${bmte.ownerName}(uuid=${bmte.ownerUuid}) was not found!")
		}

		InsuranceWorldSavedData.get().addInsuranceInfo(bmte.ownerUuid, bmte.metaTileID)
	}

	fun getMyExplodedMachines(uuid: UUID): List<Int> {
		return InsuranceWorldSavedData.get().getInsuranceInfo(uuid)
	}

	/**
	 * @param player the player
	 * @param mId the MetaTileEntity id
	 */
	fun refundExplodedMachine(player: EntityPlayerMP, mId: Int): Boolean {
		if(mId !in getMyExplodedMachines(player.uniqueID)) {
			// #tr Insurance_Message_NoSuchMachineExploded
			// #en Unable to find the machine explosion record.
			// #zh 无法找到机器爆炸的记录。
			player.sendTranslatedMessage("Insurance_Message_NoSuchMachineExploded")
			return false
		}

		// cost 25 emeralds
		if(GT_Utility.consumeItems(player, player.heldItem, Items.emerald, 25) || player.theItemInWorldManager.isCreative) {
			if(InsuranceWorldSavedData.get().removeInsuranceInfo(player.uniqueID, mId)) {
				GT_Utility.addItemToPlayerInventory(player, ItemStack(GregTech_API.sBlockMachines, 1, mId))
				// #tr Insurance_Message_Success
				// #en The machine is back to your inventory!
				// #zh 机器回到了你的背包里！
				player.sendTranslatedMessage("Insurance_Message_Success")
				logger.info("A player ${player.displayName} has made a refund for machine $mId")
				return true
			} else {
				logger.warn("Unreachable error!")
				return false
			}
		} else {
			// #tr Insurance_Message_CannotAfford
			// #en You cannot afford the payment!
			// #zh 你付不起账单！
			player.sendTranslatedMessage("Insurance_Message_CannotAfford")
			return false
		}
	}

}
