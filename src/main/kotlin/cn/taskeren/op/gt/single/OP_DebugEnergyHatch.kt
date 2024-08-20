package cn.taskeren.op.gt.single

import cn.taskeren.op.gt.utils.InfoDataBuilder
import cn.taskeren.op.gt.utils.OP_Text
import cn.taskeren.op.gt.utils.extension.button
import cn.taskeren.op.gt.utils.extension.drawable
import cn.taskeren.op.gt.utils.extension.numeric
import cn.taskeren.op.gt.utils.extension.text
import com.github.technus.tectech.util.TT_Utility
import com.gtnewhorizons.modularui.api.drawable.IDrawable
import com.gtnewhorizons.modularui.api.screen.ModularWindow
import com.gtnewhorizons.modularui.api.screen.UIBuildContext
import gregtech.api.enums.GT_Values
import gregtech.api.gui.modularui.GT_UIInfos
import gregtech.api.gui.modularui.GT_UITextures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import java.util.function.DoubleConsumer
import java.util.function.DoubleSupplier
import java.util.function.IntConsumer
import kotlin.math.abs

class OP_DebugEnergyHatch : GT_MetaTileEntity_Hatch_Energy, IWirelessEnergyHatchInformation {

	private var eu: Long = 0
	private var amp: Long = 0

	companion object {
		val DESC = arrayOf(
			"Energy Injector for Multiblocks",
			"Infinite and Customizable EU and Amp",
			InfoDataBuilder.RED + "CREATIVE EXCLUSIVE",
			OP_Text.TOOLTIP_CREDIT_NOVELTY,
		)
	}

	constructor(aID: Int, aName: String, aNameRegional: String) : super(aID, aName, aNameRegional, 12, DESC)

	constructor(aName: String, aDescription: Array<String>, aTextures: Array<Array<Array<ITexture>>>) : super(
		aName,
		12,
		aDescription,
		aTextures
	)

	override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): MetaTileEntity? {
		return OP_DebugEnergyHatch(mName, mDescriptionArray, mTextures)
	}

	override fun saveNBTData(aNBT: NBTTagCompound) {
		super.saveNBTData(aNBT)
		aNBT.setLong("EUT", eu)
		aNBT.setLong("AMP", amp)
	}

	override fun loadNBTData(aNBT: NBTTagCompound) {
		super.loadNBTData(aNBT)
		eu = aNBT.getLong("EUT")
		amp = aNBT.getLong("AMP")
	}

	override fun getMinimumStoredEU(): Long {
		return 2 * eu
	}

	override fun maxEUInput(): Long {
		return eu
	}

	override fun maxEUStore(): Long {
		return totalStorage(eu)
	}

	override fun maxAmperesIn(): Long {
		return amp
	}

	override fun getConnectionType(): ConnectionType {
		return ConnectionType.WIRELESS
	}

	override fun onFirstTick(aBaseMetaTileEntity: IGregTechTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity)

		if(aBaseMetaTileEntity.isServerSide) {
			euVar = Long.MAX_VALUE
		}
	}

	override fun onPreTick(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		super.onPreTick(aBaseMetaTileEntity, aTick)

		if(aBaseMetaTileEntity.isServerSide) {
			if(aTick % IWirelessEnergyHatchInformation.ticks_between_energy_addition == 0L) {
				euVar = Long.MAX_VALUE
			}
		}
	}

	override fun onRightclick(aBaseMetaTileEntity: IGregTechTileEntity, aPlayer: EntityPlayer): Boolean {
		GT_UIInfos.openGTTileEntityUI(aBaseMetaTileEntity, aPlayer)
		return true
	}

	override fun useModularUI(): Boolean {
		return true
	}

	override fun addUIWidgets(builder: ModularWindow.Builder, buildContext: UIBuildContext) {
		builder.apply {
			drawable {
				setDrawable(GT_UITextures.PICTURE_SCREEN_BLACK)
				setSize(90, 72)
				setPos(43, 4)
			}
			text {
				setStringSupplier { "TIER: " + GT_Values.VN[TT_Utility.getTier(abs(eu)).toInt()] }
				setDefaultColor(COLOR_TEXT_WHITE.get())
				setPos(46, 22)
			}
			text {
				setStringSupplier { "SUM: " + numberFormat.format(amp * eu) }
				setDefaultColor(COLOR_TEXT_WHITE.get())
				setPos(46, 46)
			}

			addLabelledIntegerTextField(
				"EUT: ",
				24,
				DoubleSupplier { eu.toDouble() },
				DoubleConsumer { eu = it.toLong() },
				46,
				8
			)
			addLabelledIntegerTextField(
				"AMP: ",
				24,
				DoubleSupplier { amp.toDouble() },
				DoubleConsumer { amp = it.toLong() },
				46,
				34
			)

			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_LARGE, IntConsumer { eu -= it }, 512, 64, 7, 4)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_LARGE, IntConsumer { eu /= it }, 512, 64, 7, 22)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_LARGE, IntConsumer { amp -= it }, 512, 64, 7, 40)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_LARGE, IntConsumer { amp /= it }, 512, 64, 7, 58)

			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_SMALL, IntConsumer { eu -= it }, 16, 1, 25, 4)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_SMALL, IntConsumer { eu /= it }, 16, 2, 25, 22)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_SMALL, IntConsumer { amp -= it }, 16, 1, 25, 40)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_MINUS_SMALL, IntConsumer { amp /= it }, 16, 2, 25, 58)

			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_SMALL, IntConsumer { eu += it }, 16, 1, 133, 4)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_SMALL, IntConsumer { eu *= it }, 16, 2, 133, 22)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_SMALL, IntConsumer { amp += it }, 16, 1, 133, 40)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_SMALL, IntConsumer { amp *= it }, 16, 2, 133, 58)

			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_LARGE, IntConsumer { eu += it }, 512, 64, 151, 4)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_LARGE, IntConsumer { eu *= it }, 512, 64, 151, 22)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_LARGE, IntConsumer { amp += it }, 512, 64, 151, 40)
			addChangeNumberButton(GT_UITextures.OVERLAY_BUTTON_PLUS_LARGE, IntConsumer { amp *= it }, 512, 64, 151, 58)
		}
	}

	fun ModularWindow.Builder.addLabelledIntegerTextField(
		label: String,
		labelWidth: Int,
		getter: DoubleSupplier,
		setter: DoubleConsumer,
		xPos: Int,
		yPos: Int
	) {
		text(label) {
			setDefaultColor(COLOR_TEXT_WHITE.get())
			setPos(xPos, yPos)
		}
		numeric {
			setGetter(getter)
			setSetter(setter)
			setTextColor(COLOR_TEXT_WHITE.get())
			setBackground(GT_UITextures.BACKGROUND_TEXT_FIELD.withOffset(-1F, -1F, 2F, 2F))
			setPos(xPos + labelWidth, yPos - 1)
			setSize(56, 10)
		}
	}

	fun ModularWindow.Builder.addChangeNumberButton(
		overlay: IDrawable,
		setter: IntConsumer,
		changeNumberShift: Int,
		changeNumber: Int,
		xPos: Int,
		yPos: Int
	) {
		button {
			setOnClick { clickData, widget ->
				setter.accept(if(clickData.shift) changeNumberShift else changeNumber)
			}
			setBackground(GT_UITextures.BUTTON_STANDARD, overlay)
			setSize(18, 18)
			setPos(xPos, yPos)
		}
	}

}
