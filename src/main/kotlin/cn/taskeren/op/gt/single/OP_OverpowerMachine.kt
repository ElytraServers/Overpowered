package cn.taskeren.op.gt.single

import cn.taskeren.op.gt.OP_AbstractMachine
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER_ACTIVE
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER_ACTIVE_GLOW
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER_GLOW
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_SCANNER
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_SCANNER_ACTIVE
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_SCANNER_ACTIVE_GLOW
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_SCANNER_GLOW
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_SIDE_SCANNER
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_SIDE_SCANNER_ACTIVE
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_SIDE_SCANNER_ACTIVE_GLOW
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_SIDE_SCANNER_GLOW
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_TOP_SCANNER
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_TOP_SCANNER_ACTIVE
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_TOP_SCANNER_ACTIVE_GLOW
import gregtech.api.enums.Textures.BlockIcons.OVERLAY_TOP_SCANNER_GLOW
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory

class OP_OverpowerMachine : OP_AbstractMachine {

	companion object {
		private val textures = arrayOf(
			TextureFactory.of(
				TextureFactory.of(OVERLAY_SIDE_SCANNER_ACTIVE),
				TextureFactory.builder()
					.addIcon(OVERLAY_SIDE_SCANNER_ACTIVE_GLOW)
					.glow()
					.build()
			),
			TextureFactory.of(
				TextureFactory.of(OVERLAY_SIDE_SCANNER),
				TextureFactory.builder()
					.addIcon(OVERLAY_SIDE_SCANNER_GLOW)
					.glow()
					.build()
			),
			TextureFactory.of(
				TextureFactory.of(OVERLAY_FRONT_SCANNER_ACTIVE),
				TextureFactory.builder()
					.addIcon(OVERLAY_FRONT_SCANNER_ACTIVE_GLOW)
					.glow()
					.build()
			),
			TextureFactory.of(
				TextureFactory.of(OVERLAY_FRONT_SCANNER),
				TextureFactory.builder()
					.addIcon(OVERLAY_FRONT_SCANNER_GLOW)
					.glow()
					.build()
			),
			TextureFactory.of(
				TextureFactory.of(OVERLAY_TOP_SCANNER_ACTIVE),
				TextureFactory.builder()
					.addIcon(OVERLAY_TOP_SCANNER_ACTIVE_GLOW)
					.glow()
					.build()
			),
			TextureFactory.of(
				TextureFactory.of(OVERLAY_TOP_SCANNER),
				TextureFactory.builder()
					.addIcon(OVERLAY_TOP_SCANNER_GLOW)
					.glow()
					.build()
			),
			TextureFactory.of(
				TextureFactory.of(OVERLAY_BOTTOM_SCANNER_ACTIVE),
				TextureFactory.builder()
					.addIcon(OVERLAY_BOTTOM_SCANNER_ACTIVE_GLOW)
					.glow()
					.build()
			),
			TextureFactory.of(
				TextureFactory.of(OVERLAY_BOTTOM_SCANNER),
				TextureFactory.builder()
					.addIcon(OVERLAY_BOTTOM_SCANNER_GLOW)
					.glow()
					.build()
			)
		)
	}

	constructor(aID: Int, aName: String, aNameRegional: String, aTier: Int) : super(
		aID,
		aName,
		aNameRegional,
		aTier,
		2,
		arrayOf("Awaken!"),
		1 + 1,
		1,
		*textures
	)

	constructor(
		aName: String,
		aTier: Int,
		aDescription: Array<String>,
		aTextures: Array<Array<Array<ITexture>>>,
	) : super(aName, aTier, 1, aDescription, aTextures, 1 + 1, 1)

	override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity {
		return OP_OverpowerMachine(mName, mTier.toInt(), mDescriptionArray, mTextures)
	}

}
