package cn.taskeren.op.gt.utils

import gregtech.api.enums.Textures.BlockIcons.CustomIcon
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
import gregtech.api.render.TextureFactory
import java.util.Locale

object OP_Texture {

	fun getBasicMachineTextures(aOverlays: String) = arrayOf(
		TextureFactory.of(
			TextureFactory.of(
				CustomIcon("basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_SIDE_ACTIVE")
			),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_SIDE_ACTIVE_GLOW"
					))
				)
				.glow()
				.build()
		),
		TextureFactory.of(
			TextureFactory
				.of(CustomIcon("basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_SIDE")),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_SIDE_GLOW"
					))
				)
				.glow()
				.build()
		),
		TextureFactory.of(
			TextureFactory.of(
				CustomIcon("basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_FRONT_ACTIVE")
			),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_FRONT_ACTIVE_GLOW"
					))
				)
				.glow()
				.build()
		),
		TextureFactory.of(
			TextureFactory
				.of(CustomIcon("basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_FRONT")),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_FRONT_GLOW"
					))
				)
				.glow()
				.build()
		),
		TextureFactory.of(
			TextureFactory.of(
				CustomIcon("basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_TOP_ACTIVE")
			),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_TOP_ACTIVE_GLOW"
					))
				)
				.glow()
				.build()
		),
		TextureFactory.of(
			TextureFactory
				.of(CustomIcon("basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_TOP")),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_TOP_GLOW"
					))
				)
				.glow()
				.build()
		),
		TextureFactory.of(
			TextureFactory.of(
				CustomIcon(
					"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_BOTTOM_ACTIVE"
				)
			),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_BOTTOM_ACTIVE_GLOW"
					))
				)
				.glow()
				.build()
		),
		TextureFactory.of(
			TextureFactory
				.of(CustomIcon("basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_BOTTOM")),
			TextureFactory.builder()
				.addIcon(
					(CustomIcon(
						"basicmachines/" + aOverlays.lowercase(Locale.ENGLISH) + "/OVERLAY_BOTTOM_GLOW"
					))
				)
				.glow()
				.build()
		)
	)

	@Deprecated("will cause server error!")
	val SCANNER
		get() = arrayOf(
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
