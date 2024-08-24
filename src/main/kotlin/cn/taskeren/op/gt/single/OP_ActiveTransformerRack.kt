package cn.taskeren.op.gt.single

import cn.taskeren.op.OP
import cn.taskeren.op.gt.utils.OP_Text
import cn.taskeren.op.gt.utils.buildInfoData
import cn.taskeren.op.gt.utils.extension.drawable
import cn.taskeren.op.gt.utils.extension.slot
import cn.taskeren.op.gt.utils.extension.text
import cn.taskeren.op.translated
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DynamoMulti
import com.gtnewhorizons.modularui.api.forge.ItemStackHandler
import com.gtnewhorizons.modularui.api.screen.ModularWindow
import com.gtnewhorizons.modularui.api.screen.UIBuildContext
import gregtech.api.GregTech_API
import gregtech.api.gui.modularui.GT_UIInfos
import gregtech.api.gui.modularui.GT_UITextures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo
import gregtech.api.util.GT_Utility
import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

/**
 * A hatch for Active Transformer to enable hot-plug Dynamos.
 *
 * @see com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_transformer
 * @see com.github.technus.tectech.thing.metaTileEntity.multi.GT_MetaTileEntity_EM_transformer.powerPass
 */
class OP_ActiveTransformerRack : GT_MetaTileEntity_Hatch_DynamoMulti {

	companion object {
		private val DESC = arrayOf(
			// #tr ActiveTransformerRack_Tooltip_1
			// #en Generating Electric Energy from Multiblocks
			// #zh 能量的多方块输出
			translated("ActiveTransformerRack_Tooltip_1"),
			// #tr ActiveTransformerRack_Tooltip_2
			// #en Generates 1A ULV by default.
			// #zh 默认输出1安ULV。
			translated("ActiveTransformerRack_Tooltip_2"),
			// #tr ActiveTransformerRack_Tooltip_3
			// #en You can insert higher level {BLUE}Dynamos {GRAY}for better performance.
			// #zh 你可以放入更高级的{BLUE}动力仓{GRAY}取得更高的性能。
			translated("ActiveTransformerRack_Tooltip_3"),
			OP_Text.TOOLTIP_CREDIT,
		)
	}

	private var inventory: Array<ItemStack?> = arrayOfNulls(1)
	private val inventoryHandler = ItemStackHandler(inventory)

	private var dynamoItemStack: ItemStack?
		get() = inventory[0]
		set(value) {
			inventory[0] = value
		}

	private var outEU: Long = 8
	private var outAmp: Long = 1
	private var maxStoreEU: Long = 576
	private var minStoreEU: Long = 512

	constructor(aID: Int, aName: String, aNameRegional: String, aTier: Int, aAmp: Int = 0) : super(
		aID,
		aName,
		aNameRegional,
		aTier,
		aAmp
	)

	constructor(
		aName: String,
		aTier: Int,
		aAmp: Int,
		aDescription: Array<String>,
		aTextures: Array<Array<Array<ITexture>>>
	) : super(aName, aTier, aAmp, aDescription, aTextures)

	override fun newMetaEntity(aTileEntity: IGregTechTileEntity): MetaTileEntity {
		return OP_ActiveTransformerRack(mName, mTier.toInt(), Amperes, mDescriptionArray, mTextures)
	}

	override fun getDescription(): Array<String> {
		return DESC
	}

	fun getDynamoTileEntity(): MetaTileEntity? {
		val dynamoItem = dynamoItemStack
		if(dynamoItem == null || !OP.isMachineBlock(dynamoItem.item)) return null
		val mte = GregTech_API.METATILEENTITIES.getOrNull(dynamoItem.itemDamage) ?: return null
		return if(mte is GT_MetaTileEntity_Hatch_Dynamo || mte is GT_MetaTileEntity_Hatch_DynamoMulti) {
			mte
		} else {
			null
		}
	}

	override fun onPreTick(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		super.onPreTick(aBaseMetaTileEntity, aTick)
		if(aTick % 10 == 0L && aBaseMetaTileEntity.isServerSide) {
			val dte = getDynamoTileEntity()
			if(dte != null) {
				outEU = dte.maxEUOutput()
				outAmp = dte.maxAmperesOut()
				maxStoreEU = dte.maxEUStore()
				minStoreEU = dte.minimumStoredEU
			} else {
				outEU = 8
				outAmp = 1
				maxStoreEU = 576
				minStoreEU = 512
			}
		}
	}

	override fun getMinimumStoredEU(): Long {
		return minStoreEU
	}

	override fun maxEUStore(): Long {
		return maxStoreEU
	}

	override fun maxEUOutput(): Long {
		return outEU
	}

	override fun maxAmperesOut(): Long {
		return outAmp
	}

	// data saving

	override fun saveNBTData(aNBT: NBTTagCompound) {
		super.saveNBTData(aNBT)
		GT_Utility.saveItem(aNBT, "dynamo", dynamoItemStack)
	}

	override fun loadNBTData(aNBT: NBTTagCompound) {
		super.loadNBTData(aNBT)
		dynamoItemStack = GT_Utility.loadItem(aNBT, "dynamo")
	}

	// texture

	override fun getTexture(
		aBaseMetaTileEntity: IGregTechTileEntity?,
		side: ForgeDirection?,
		aFacing: ForgeDirection?,
		colorIndex: Int,
		aActive: Boolean,
		redstoneLevel: Boolean
	): Array<out ITexture?>? {
		return super.getTexture(aBaseMetaTileEntity, side, aFacing, colorIndex, aActive, redstoneLevel)
	}

	override fun onRightclick(aBaseMetaTileEntity: IGregTechTileEntity, aPlayer: EntityPlayer): Boolean {
		GT_UIInfos.openGTTileEntityUI(aBaseMetaTileEntity, aPlayer)
		return true
	}

	// ui

	override fun useModularUI(): Boolean {
		return true
	}

	override fun addUIWidgets(builder: ModularWindow.Builder, buildContext: UIBuildContext) {
		builder.apply {
			drawable {
				setDrawable(GT_UITextures.PICTURE_SCREEN_BLACK)
				setPos(7, 16)
				setSize(138, 45)
			}
			slot(inventoryHandler, 0) {
				setAccess(true, true)
				setBackground(guiTextureSet.itemSlot, GT_UITextures.OVERLAY_SLOT_IN)
				setPos(150, 16)
			}
			text("Possibly Routing") {
				setDefaultColor(COLOR_TEXT_WHITE.get())
				setPos(10, 20)
			}
		}
	}

	// ui data

	override fun isGivingInformation(): Boolean {
		return true
	}

	override fun getInfoData(): Array<String> {
		return buildInfoData {
			add(super.infoData)
			text("${blue}Dynamo Stack ${gray}= ${green}${dynamoItemStack}")
			text("${blue}Dynamo Max EU Output ${gray}= ${green}${outEU}")
			text("${blue}Dynamo Max Amp Output ${gray}= ${green}${outAmp}")
			text("${blue}Dynamo Max Stored EU ${gray}= ${green}${maxStoreEU}")
			text("${blue}Dynamo Min Stored EU ${gray}= ${green}${minStoreEU}")
		}
	}

	override fun getWailaBody(
		itemStack: ItemStack?,
		currenttip: MutableList<String>,
		accessor: IWailaDataAccessor,
		config: IWailaConfigHandler?
	) {
		super.getWailaBody(itemStack, currenttip, accessor, config)
		val nbt = accessor.nbtData
		currenttip.add("Output EU: ${nbt.getLong("OutEU")}")
		currenttip.add("Output Amperes: ${nbt.getLong("OutAmp")}")
	}

	override fun getWailaNBTData(
		player: EntityPlayerMP?,
		tile: TileEntity?,
		tag: NBTTagCompound,
		world: World?,
		x: Int,
		y: Int,
		z: Int
	) {
		super.getWailaNBTData(player, tile, tag, world, x, y, z)
		tag.setLong("OutEU", this.outEU)
		tag.setLong("OutAmp", this.outAmp)
	}

}
