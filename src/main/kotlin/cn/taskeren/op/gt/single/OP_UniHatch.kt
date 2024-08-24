package cn.taskeren.op.gt.single

import cn.taskeren.op.gt.utils.InfoDataBuilder
import cn.taskeren.op.gt.utils.OP_Text
import cn.taskeren.op.gt.utils.extension.tier
import cn.taskeren.op.gt.utils.extension.widget
import cn.taskeren.op.mc.util.forEachCompoundIndexed
import cn.taskeren.op.translated
import com.gtnewhorizons.modularui.api.screen.ModularWindow
import com.gtnewhorizons.modularui.api.screen.UIBuildContext
import com.gtnewhorizons.modularui.common.fluid.FluidStackTank
import com.gtnewhorizons.modularui.common.widget.SlotGroup
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_Utility
import gregtech.common.tileentities.machines.IDualInputHatch
import gregtech.common.tileentities.machines.IDualInputInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTankInfo
import java.util.Optional
import java.util.function.Consumer
import java.util.function.Supplier
import kotlin.math.min

class OP_UniHatch : GT_MetaTileEntity_Hatch_InputBus, IDualInputHatch {

	companion object {
		private val ITEM_SLOT_SIZE = arrayOf(1, 2, 6, 8, 18)
		private val FLUID_SLOT_SIZE = arrayOf(1, 2, 3, 8, 18)
		private val FLUID_CAPACITY_PER_SLOT = arrayOf(8_000, 16_000, 64_000, 128_000, 1_024_000)

		private val UI_RENDER_SLOTS_PER_ROW = arrayOf(1, 2, 3, 4, 9)

		fun getItemSlotSize(tier: Int) = ITEM_SLOT_SIZE[tier.coerceAtMost(ITEM_SLOT_SIZE.size - 1)]
		fun getFluidSlotSize(tier: Int) = FLUID_SLOT_SIZE[tier.coerceAtMost(FLUID_SLOT_SIZE.size - 1)]
		fun getFluidCapacityPerSlot(tier: Int) =
			FLUID_CAPACITY_PER_SLOT[tier.coerceAtMost(FLUID_CAPACITY_PER_SLOT.size - 1)]
	}

	val fluidStacks: Array<FluidStack?>
	val fluidStackTanks: Array<FluidStackTank?>

	val uniInventory = UniInventory()

	constructor(aID: Int, aName: String, aNameRegional: String, aTier: Int) : super(
		aID,
		aName,
		aNameRegional,
		aTier,
		getItemSlotSize(aTier) + 1, // additional 1 is for the virtual circuit
		arrayOf(
			// #tr UniHatch_Tooltip_1
			// #en {GOLD}Item {GRAY}and {BLUE}Fluid {GRAY}Input for Multiblocks
			// #zh {GOLD}物品{GRAY}和{BLUE}流体{GRAY}的多方块结构输入
			translated("UniHatch_Tooltip_1"),
			// #tr UniHatch_Tooltip_2
			// #en Item Slots: %s
			// #zh 物品栏位：%s
			translated("UniHatch_Tooltip_2", "${InfoDataBuilder.tiered(aTier)}${getItemSlotSize(aTier)}"),
			// #tr UniHatch_Tooltip_3
			// #en Fluid Slots: %s
			// #zh 流体栏位：%s
			translated("UniHatch_Tooltip_3", "${InfoDataBuilder.tiered(aTier)}${getFluidSlotSize(aTier)}"),
			// #tr UniHatch_Tooltip_4
			// #en Fluid Capacity: %s
			// #zh 流体容量：%s
			translated("UniHatch_Tooltip_4", "${InfoDataBuilder.tiered(aTier)}${InfoDataBuilder.numberFormatted(getFluidCapacityPerSlot(aTier))}L"),
			OP_Text.TOOLTIP_CREDIT_NOVELTY,
		),
	) {
		fluidStacks = arrayOfNulls(getFluidSlotSize(aTier))
		fluidStackTanks = arrayOfNulls(getFluidSlotSize(aTier))
	}

	constructor(
		aName: String,
		aTier: Int,
		aDescription: Array<String>,
		aTextures: Array<Array<Array<ITexture>>>,
	) : super(aName, aTier, getItemSlotSize(aTier) + 1, aDescription, aTextures) {
		fluidStacks = arrayOfNulls(getFluidSlotSize(aTier))
		fluidStackTanks = arrayOfNulls(getFluidSlotSize(aTier))

		for((index, _) in fluidStackTanks.withIndex()) {
			fluidStackTanks[index] = FluidStackTank(
				Supplier<FluidStack?> { fluidStacks[index] },
				Consumer<FluidStack?> { fluidStacks[index] = it },
				getFluidCapacityPerSlot(aTier)
			)
		}
	}

	override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): MetaTileEntity? {
		return OP_UniHatch(mName, mTier.toInt(), mDescriptionArray, mTextures)
	}

	override fun saveNBTData(aNBT: NBTTagCompound) {
		super.saveNBTData(aNBT)

		val fluids = NBTTagList().apply {
			fluidStacks.filterNotNull().forEach { fluid ->
				appendTag(NBTTagCompound().apply { fluid.writeToNBT(this) })
			}
		}
		aNBT.setTag("Fluids", fluids)
	}

	override fun loadNBTData(aNBT: NBTTagCompound) {
		super.loadNBTData(aNBT)

		val fluids = aNBT.getTagList("Fluids", 10)
		fluids.forEachCompoundIndexed { index, tag ->
			fluidStacks[index] = FluidStack.loadFluidStackFromNBT(tag)
		}
	}


	override fun getTexturesActive(aBaseTexture: ITexture?): Array<out ITexture?>? {
		return getTexturesInactive(aBaseTexture)
	}

	override fun getTexturesInactive(aBaseTexture: ITexture?): Array<ITexture?> {
		return arrayOf(aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_ME_CRAFTING_INPUT_BUFFER))
	}

	override fun onPreTick(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		onPreTickFluid(aBaseMetaTileEntity, aTick)

		super.onPreTick(aBaseMetaTileEntity, aTick)
	}

	override fun onPostTick(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		onPostTickFluid(aBaseMetaTileEntity, aTick)

		super.onPostTick(aBaseMetaTileEntity, aTick)
	}

	private fun onPreTickFluid(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		if(aBaseMetaTileEntity.isServerSide) {
			mFluid = fluid
		}
	}

	private fun onPostTickFluid(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		if(aBaseMetaTileEntity.isServerSide) {
			fluidStacks.forEachIndexed { index, fluid ->
				if(fluid != null && fluid.amount <= 0) {
					fluidStacks[index] = null
				}
			}
		}
	}

	override fun getCircuitSlot(): Int {
		return mInventory.size - 1
	}

	// region dual hatch

	override fun justUpdated(): Boolean {
		return true
	}

	override fun inventories(): Iterator<IDualInputInventory> {
		return arrayOf(uniInventory).iterator()
	}

	override fun getFirstNonEmptyInventory(): Optional<IDualInputInventory> {
		return if(uniInventory.isEmpty()) {
			Optional.empty()
		} else {
			Optional.of(uniInventory)
		}
	}

	override fun supportsFluids(): Boolean {
		return true
	}

	// endregion

	// region fluid

	override fun doesEmptyContainers(): Boolean {
		return true
	}

	override fun canTankBeFilled(): Boolean {
		return true
	}

	override fun canTankBeEmptied(): Boolean {
		return true
	}

	override fun isFluidInputAllowed(aFluid: FluidStack?): Boolean {
		return mRecipeMap == null || mRecipeMap.containsInput(aFluid)
	}

	override fun getTankPressure(): Int {
		return -100
	}

	override fun displaysItemStack(): Boolean {
		return true
	}

	override fun displaysStackSize(): Boolean {
		return true
	}

	fun getStoredFluid(): Array<FluidStack?> {
		return fluidStacks
	}

	fun getMaxFluidTypes(): Int {
		return fluidStacks.size
	}

	override fun getFluid(): FluidStack? = fluidStacks.firstOrNull { it != null }

	override fun getFluidAmount(): Int = fluid?.amount ?: 0

	override fun getCapacity(): Int = getFluidCapacityPerSlot(mTier.toInt())

	fun getFluidAt(index: Int): FluidStack? = fluidStacks[index]

	fun getFirstEmptyFluidSlot(): Int = fluidStacks.indexOfFirst { it == null }

	fun hasFluid(aFluid: FluidStack?): Boolean {
		if(aFluid == null) return false
		return fluidStacks.any { aFluid.isFluidEqual(it) }
	}

	fun getFluidSlot(tFluid: FluidStack?): Int {
		if(tFluid == null) return -1
		return fluidStacks.indexOfFirst { tFluid.isFluidEqual(it) }
	}

	fun getFluidAmount(tFluid: FluidStack?): Int {
		if(tFluid == null) return 0
		return fluidStacks.firstNotNullOfOrNull { it?.amount } ?: 0
	}

	/**
	 * @return the fluid stack with the same fluid. Return `null` is the fluid does not exist.
	 */
	fun getFluidBy(aFluid: FluidStack): FluidStack? {
		return fluidStacks.firstOrNull { aFluid.isFluidEqual(it) }
	}

	fun setFluid(aFluid: FluidStack?, aSlot: Int) {
		if(aSlot in fluidStacks.indices) {
			fluidStacks[aSlot] = aFluid
		}
	}

	fun addFluid(aFluid: FluidStack?, aSlot: Int) {
		if(aSlot !in fluidStacks.indices) return
		if(aFluid == null) return
		val fluidInSlot = fluidStacks[aSlot]
		if(fluidInSlot == null) {
			fluidStacks[aSlot] = aFluid
		} else if(aFluid == fluidInSlot) {
			fluidInSlot.amount += aFluid.amount
		}
	}

	override fun fill(aFluid: FluidStack?, doFill: Boolean): Int {
		if(aFluid == null ||
			aFluid.fluidID <= 0 ||
			aFluid.amount <= 0 ||
			!canTankBeFilled() ||
			!isFluidInputAllowed(aFluid)
		) return 0

		if(!hasFluid(aFluid) && getFirstEmptyFluidSlot() != -1) {
			val tFilled = min(aFluid.amount, capacity)
			if(doFill) {
				val tFluid = aFluid.copy()
				tFluid.amount = tFilled
				addFluid(tFluid, getFirstEmptyFluidSlot())
				baseMetaTileEntity.markDirty()
			}
			return tFilled
		}

		if(hasFluid(aFluid)) {
			val tLeft = capacity - getFluidAmount(aFluid)
			val tFilled = min(tLeft, aFluid.amount)
			if(doFill) {
				val tFluid = aFluid.copy()
				tFluid.amount = tFilled
				addFluid(tFluid, getFluidSlot(tFluid))
				baseMetaTileEntity.markDirty()
			}
			return tFilled
		}

		return 0
	}

	override fun drain(maxDrain: Int, doDrain: Boolean): FluidStack? {
		val fluid = fluid
		if(fluid == null || !canTankBeEmptied()) return null
		if(fluid.amount <= 0 && isFluidChangingAllowed) {
			setFluid(null, getFluidSlot(fluid))
			baseMetaTileEntity.markDirty()
			return null
		}

		val tRemove = fluid.copy()
		tRemove.amount = min(maxDrain, tRemove.amount)
		if(doDrain) {
			fluid.amount -= tRemove.amount
			baseMetaTileEntity.markDirty()
		}
		if(fluid.amount <= 0 && isFluidChangingAllowed) {
			setFluid(null, getFluidSlot(fluid))
			baseMetaTileEntity.markDirty()
		}
		return tRemove
	}

	override fun fill(side: ForgeDirection?, aFluid: FluidStack?, doFill: Boolean): Int {
		return fill(aFluid, doFill)
	}

	override fun drain(side: ForgeDirection?, aFluid: FluidStack?, doDrain: Boolean): FluidStack? {
		if(aFluid == null) return null
		val tStored = getFluidBy(aFluid) ?: return null

		if(tStored.amount <= 0 && isFluidChangingAllowed) {
			setFluid(null, getFluidSlot(aFluid))
			baseMetaTileEntity.markDirty()
			return null
		}

		val tRemove = tStored.copy()
		tRemove.amount = min(aFluid.amount, tRemove.amount)
		if(doDrain) {
			tStored.amount -= tRemove.amount
			baseMetaTileEntity.markDirty()
		}
		if(tStored.amount <= 0 && isFluidChangingAllowed) {
			setFluid(null, getFluidSlot(aFluid))
			baseMetaTileEntity.markDirty()
		}
		return tRemove
	}

	override fun getTankInfo(side: ForgeDirection?): Array<out FluidTankInfo> {
		return fluidStacks.map { fluid -> FluidTankInfo(fluid, capacity) }.toTypedArray()
	}

	// endregion

	// region GUI

	override fun getGUIWidth(): Int {
		return 176 + 18
	}

	override fun getCircuitSlotX(): Int {
		return 171
	}

	override fun getCircuitSlotY(): Int {
		return 60
	}

	override fun addUIWidgets(builder: ModularWindow.Builder, buildContext: UIBuildContext) {
		builder.apply {
			widget {
				SlotGroup
					.ofItemHandler(inventoryHandler, UI_RENDER_SLOTS_PER_ROW[tier])
					.endAtSlot(getItemSlotSize(tier) - 1)
					.background(guiTextureSet.itemSlot)
					.build()
					.setPos(7, 5)
			}
			widget {
				SlotGroup
					.ofFluidTanks(fluidStackTanks.toList(), UI_RENDER_SLOTS_PER_ROW[tier])
					.background(guiTextureSet.fluidSlot)
					.build()
					.setPos(7, if(tier > 1) 41 else 23)
			}
		}
	}

	// endregion

	inner class UniInventory : IDualInputInventory {

		private fun updateItem() {
			mInventory.forEachIndexed { index, item ->
				if(GT_Utility.isStackInvalid(item)) {
					mInventory[index] = null
				} else if(item.stackSize <= 0 && index != circuitSlot) {
					mInventory[index] = null
				}
			}
		}

		private fun updateFluid() {
			fluidStacks.forEachIndexed { index, item ->
				if(item != null && item.amount <= 0) {
					fluidStacks[index] = null
				}
			}
		}

		fun isEmpty(): Boolean {
			updateItem()
			updateFluid()
			return mInventory.all { it == null } && fluidStacks.all{ it == null }
		}

		override fun getItemInputs(): Array<ItemStack>? {
			if(isEmpty()) return null
			return mInventory.filterNotNull().toTypedArray()
		}

		override fun getFluidInputs(): Array<out FluidStack?>? {
			if(isEmpty()) return null
			return fluidStacks.filterNotNull().toTypedArray()
		}
	}

}
