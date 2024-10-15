package cn.taskeren.op.insurance

import cn.taskeren.op.OP
import cn.taskeren.op.OP_Logger
import cn.taskeren.op.gt.utils.GTApi
import cn.taskeren.op.mc.util.plainChat
import cn.taskeren.op.mc.util.sendTranslatedMessage
import cn.taskeren.op.mc.util.teleportChat
import cn.taskeren.op.mc.util.translatedChat
import cn.taskeren.op.mc.util.withGold
import cn.taskeren.op.mc.util.withYellow
import gregtech.api.GregTechAPI
import gregtech.api.metatileentity.BaseMetaTileEntity
import gregtech.api.util.GTUtility
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import java.util.UUID

/**
 * Everything about Insurance is here.
 *
 * All the machines that explode will be registered here, from [cn.taskeren.op.mixin.late.Insurance_BaseMetaTileEntity_Mixin].
 * Players will have chances to refund their machines back by costing a bunch of resources like Emeralds.
 *
 * Players can see their exploded machines using command `/insurance list`. The listed numbers are the MetaTileEntity IDs.
 */
object InsuranceManager : OP_Logger {

	fun onMachineExplode(bmte: BaseMetaTileEntity, level: Long) {
		logger.info("{} exploded! owner: {}", bmte, bmte.ownerUuid)

		if(OP.dev || OP.propertyDumpStackTraceOnMachineExplode) {
			logger.info("The machine explosion stacktrace.", Throwable("Don't be panic! This is a machine explosion stacktrace!"))
		}

		val ownerPlayer =
			MinecraftServer.getServer().configurationManager.playerEntityList.firstOrNull { it.uniqueID == bmte.ownerUuid }

		val machineTile = GTApi.getMetaTileEntityById(bmte.metaTileID)

		if(ownerPlayer != null) {
			// #tr Insurance_Message_MachineExploded
			// #en Your machine %3$s exploded at %1$s(%2$s)!
			// #zh 你位于%1$s（%2$s）的机器%3$s爆炸了！
			ownerPlayer.addChatMessage(translatedChat(
				"Insurance_Message_MachineExploded",
				teleportChat(bmte.xCoord, bmte.yCoord, bmte.zCoord),
				plainChat("${bmte.world.provider.dimensionId}").withYellow(),
				translatedChat(GTApi.getMachineUnlocalizedNameOrUnknownMachine(bmte.metaTileID)).withGold()
			))
		} else {
			logger.info("A machine exploded at ${bmte.xCoord}/${bmte.yCoord}/${bmte.zCoord} and his owner ${bmte.ownerName}(uuid=${bmte.ownerUuid}) was not found!")
		}

		InsuranceWorldSavedData.get().addInsuranceInfo(bmte.ownerUuid, bmte.metaTileID)
	}

	fun getMyExplodedMachines(uuid: UUID): List<Int> {
		return InsuranceWorldSavedData.get().getInsuranceInfo(uuid)
	}

	fun clearMyExplodedMachines(uuid: UUID) {
		InsuranceWorldSavedData.get().clearInsuranceInfo(uuid)
	}

	/**
	 * @param player the player
	 * @param mId the MetaTileEntity id
	 */
	@Deprecated("This is the old design!")
	fun refundExplodedMachine(player: EntityPlayerMP, mId: Int): Boolean {
		if(mId !in getMyExplodedMachines(player.uniqueID)) {
			// #tr Insurance_Message_NoSuchMachineExploded
			// #en Unable to find the machine explosion record.
			// #zh 无法找到机器爆炸的记录。
			player.sendTranslatedMessage("Insurance_Message_NoSuchMachineExploded")
			return false
		}

		// cost 25 emeralds
		if(GTUtility.consumeItems(
				player,
				player.heldItem,
				Items.emerald,
				25
			) || player.theItemInWorldManager.isCreative
		) {
			if(InsuranceWorldSavedData.get().removeInsuranceInfo(player.uniqueID, mId)) {
				GTUtility.addItemToPlayerInventory(player, ItemStack(GregTechAPI.sBlockMachines, 1, mId))
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

	fun hasExplodedMachine(uuid: UUID, metaId: Int): Boolean {
		return metaId in InsuranceWorldSavedData.get().getInsuranceInfo(uuid)
	}

	fun refundExplodedMachine(uuid: UUID, metaId: Int): Boolean {
		return InsuranceWorldSavedData.get().removeInsuranceInfo(uuid, metaId)
	}

}
