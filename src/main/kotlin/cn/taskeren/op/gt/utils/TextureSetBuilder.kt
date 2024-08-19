package cn.taskeren.op.gt.utils

import gregtech.api.interfaces.ITexture

interface TextureSetBuilder {
	fun build(): Array<Array<Array<ITexture>?>>

	companion object {
		fun basicMachine(block: TextureSetBuilderBasicMachine.() -> Unit) = TextureSetBuilderBasicMachine().apply(block).build()
	}
}

class TextureSetBuilderBasicMachine(
	private val textureSet: Array<Array<Array<ITexture>?>> = Array(14) { arrayOfNulls(17) },
) : TextureSetBuilder {

	private fun putValue(i: Int, textures: (colorIndex: Int) -> Array<ITexture>) {
		for(c in -1..16) {
			textureSet[i][c + 1] = textures(c)
		}
	}

	fun sideFacingActive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(0, textures)
	fun sideFacingInactive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(1, textures)
	fun frontFacingActive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(2, textures)
	fun frontFacingInactive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(3, textures)
	fun topFacingActive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(4, textures)
	fun topFacingInactive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(5, textures)
	fun bottomFacingActive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(6, textures)
	fun bottomFacingInactive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(7, textures)
	fun bottomFacingPipeActive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(8, textures)
	fun bottomFacingPipeInactive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(9, textures)
	fun topFacingPipeActive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(10, textures)
	fun topFacingPipeInactive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(11, textures)
	fun sideFacingPipeActive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(12, textures)
	fun sideFacingPipeInactive(textures: (colorIndex: Int) -> Array<ITexture>) = putValue(13, textures)

	override fun build(): Array<Array<Array<ITexture>?>> {
		return textureSet
	}
}
