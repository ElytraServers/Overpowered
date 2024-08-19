@file:Suppress("unused")

package cn.taskeren.op.gt

import appeng.api.util.AECableType
import appeng.me.helpers.AENetworkProxy
import cn.taskeren.op.OP
import cn.taskeren.op.utils.ClientOnly
import com.gtnewhorizons.modularui.api.drawable.IDrawable
import com.gtnewhorizons.modularui.api.forge.ItemStackHandler
import com.gtnewhorizons.modularui.api.math.Pos2d
import com.gtnewhorizons.modularui.api.screen.ModularWindow
import com.gtnewhorizons.modularui.api.screen.UIBuildContext
import com.gtnewhorizons.modularui.api.widget.Widget
import com.gtnewhorizons.modularui.common.widget.CycleButtonWidget
import com.gtnewhorizons.modularui.common.widget.DrawableWidget
import com.gtnewhorizons.modularui.common.widget.FluidSlotWidget
import com.gtnewhorizons.modularui.common.widget.SlotWidget
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import gregtech.api.enums.SteamVariant
import gregtech.api.gui.modularui.GUITextureSet
import gregtech.api.interfaces.ICleanroom
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.BaseMetaTileEntity
import gregtech.api.metatileentity.MetaTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank
import gregtech.api.objects.GT_ItemStack
import gregtech.api.objects.overclockdescriber.OverclockDescriber
import gregtech.api.recipe.BasicUIProperties
import gregtech.api.recipe.RecipeMap
import gregtech.api.util.GT_Config
import gregtech.api.util.GT_Recipe
import gregtech.api.util.GT_TooltipDataCache
import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTankInfo
import org.intellij.lang.annotations.MagicConstant
import java.io.File
import java.util.ArrayList
import net.minecraft.util.EnumChatFormatting as ECF

/**
 * This class adds documents to [GT_MetaTileEntity_BasicMachine], in my (Taskeren) style.
 *
 * You are not recommended to `extends` this class, but use this class as documentation.
 * If you do `extends` this class, you should check if this class overwrites something that differs from [GT_MetaTileEntity_BasicMachine].
 *
 * If you find some "keywords" in uppercase, like "MUST", "MUST NOT", "SHOULD", "SHOULD NOT", the meaning of it follows
 * [RFC-2119](https://datatracker.ietf.org/doc/html/rfc2119).
 * And it also follows the [RFC-8147](https://datatracker.ietf.org/doc/html/rfc8174), which defines that ONLY uppercased
 * letters have special meanings.
 *
 * @author Taskeren
 */
abstract class OP_AbstractMachine : GT_MetaTileEntity_BasicMachine {

	// region Constructors

	constructor(
		aID: Int,
		aName: String,
		aNameRegional: String,
		aTier: Int,
		aAmperage: Int,
		aDescription: Array<out String>,
		aInputSlotCount: Int,
		aOutputSlotCount: Int,
		vararg aOverlays: ITexture,
	) : super(aID, aName, aNameRegional, aTier, aAmperage, aDescription, aInputSlotCount, aOutputSlotCount, *aOverlays)

	constructor(
		aName: String,
		aTier: Int,
		aAmperage: Int,
		aDescription: Array<out String>,
		aTextures: Array<out Array<Array<ITexture>>>,
		aInputSlotCount: Int,
		aOutputSlotCount: Int,
	) : super(aName, aTier, aAmperage, aDescription, aTextures, aInputSlotCount, aOutputSlotCount)

	// endregion

	// region Java Basics

	override fun equals(other: Any?): Boolean {
		return super.equals(other)
	}

	override fun hashCode(): Int {
		return super.hashCode()
	}

	override fun toString(): String {
		return super.toString()
	}

	// endregion

	// region Rotational Energy

	override fun acceptsRotationalEnergy(side: ForgeDirection?): Boolean {
		return false
	}

	override fun injectRotationalEnergy(side: ForgeDirection?, aSpeed: Long, aEnergy: Long): Boolean {
		return false
	}

	// endregion

	override fun getBaseMetaTileEntity(): IGregTechTileEntity? {
		return super.baseMetaTileEntity
	}

	/**
	 * @return the ItemStack to represent this machine in AE2 network.
	 * @see IMetaTileEntity.getMachineCraftingIcon
	 */
	override fun getMachineCraftingIcon(): ItemStack? {
		return baseMetaTileEntity?.drops?.firstOrNull()
	}

	/**
	 * @return an ItemStack of [aAmount] this machine (meta block).
	 * @see IMetaTileEntity.getStackForm
	 */
	override fun getStackForm(aAmount: Long): ItemStack {
		return super.getStackForm(aAmount)
	}

	/**
	 * Set the [mBaseMetaTileEntity] to [aBaseMetaTileEntity].
	 *
	 * Calls [inValidate] if either [mBaseMetaTileEntity] or [aBaseMetaTileEntity] is null.
	 *
	 * @see inValidate
	 * @see IMetaTileEntity.setBaseMetaTileEntity
	 */
	override fun setBaseMetaTileEntity(aBaseMetaTileEntity: IGregTechTileEntity?) {
		super.setBaseMetaTileEntity(aBaseMetaTileEntity)
	}

	/**
	 * Write machine data to ItemStack NBT [aNBT].
	 *
	 * Called in [BaseMetaTileEntity.getDrops] to get item drops.
	 *
	 * @see IMetaTileEntity.setItemNBT
	 */
	override fun setItemNBT(aNBT: NBTTagCompound) {
		super.setItemNBT(aNBT)
	}

	// region Lifecycle Events

	/**
	 * Called when the server starting (before started).
	 *
	 * @see IMetaTileEntity.onServerStart
	 */
	override fun onServerStart() {
		super.onServerStart()
	}


	/**
	 * Called on the first tick of the world.
	 *
	 * @param aSaveDirectory the world save directory
	 * @see IMetaTileEntity.onWorldLoad
	 */
	override fun onWorldLoad(aSaveDirectory: File) {
		super.onWorldLoad(aSaveDirectory)
	}

	/**
	 * Called when the server stopping.
	 *
	 * @param aSaveDirectory the world save directory
	 * @see IMetaTileEntity.onWorldSave
	 */
	override fun onWorldSave(aSaveDirectory: File) {
		super.onWorldSave(aSaveDirectory)
	}

	/**
	 * Called on post-initialization.
	 *
	 * Used to read the configuration values.
	 *
	 * @see IMetaTileEntity.onConfigLoad
	 */
	override fun onConfigLoad(aConfig: GT_Config) {
		super.onConfigLoad(aConfig)
	}

	// endregion

	// region Interactions by Tools

	override fun onScrewdriverRightClick(
		side: ForgeDirection?,
		aPlayer: EntityPlayer?,
		aX: Float,
		aY: Float,
		aZ: Float,
		aTool: ItemStack?,
	) {
		super.onScrewdriverRightClick(side, aPlayer, aX, aY, aZ, aTool)
	}

	override fun onWrenchRightClick(
		side: ForgeDirection,
		wrenchingSide: ForgeDirection,
		entityPlayer: EntityPlayer,
		aX: Float,
		aY: Float,
		aZ: Float,
		aTool: ItemStack,
	): Boolean {
		return super.onWrenchRightClick(side, wrenchingSide, entityPlayer, aX, aY, aZ, aTool)
	}

	override fun onWireCutterRightClick(
		side: ForgeDirection,
		wrenchingSide: ForgeDirection,
		aPlayer: EntityPlayer,
		aX: Float,
		aY: Float,
		aZ: Float,
		aTool: ItemStack,
	): Boolean {
		return super.onWireCutterRightClick(side, wrenchingSide, aPlayer, aX, aY, aZ, aTool)
	}

	override fun onSolderingToolRightClick(
		side: ForgeDirection,
		wrenchingSide: ForgeDirection,
		aPlayer: EntityPlayer,
		aX: Float,
		aY: Float,
		aZ: Float,
		aTool: ItemStack,
	): Boolean {
		return super.onSolderingToolRightClick(side, wrenchingSide, aPlayer, aX, aY, aZ, aTool)
	}

	override fun onScrewdriverRightClick(
		side: ForgeDirection,
		aPlayer: EntityPlayer,
		aX: Float,
		aY: Float,
		aZ: Float,
	) {
		super.onScrewdriverRightClick(side, aPlayer, aX, aY, aZ)
	}

	override fun onSolderingToolRightClick(
		side: ForgeDirection,
		wrenchingSide: ForgeDirection,
		entityPlayer: EntityPlayer?,
		aX: Float,
		aY: Float,
		aZ: Float,
	): Boolean {
		return super.onSolderingToolRightClick(side, wrenchingSide, entityPlayer, aX, aY, aZ)
	}

	/**
	 * @return true if [aPlayer] can access this machine.
	 */
	override fun isAccessAllowed(aPlayer: EntityPlayer): Boolean {
		return super.isAccessAllowed(aPlayer)
	}

	// endregion

	// region Machine Events

	override fun onMachineBlockUpdate() {
		super.onMachineBlockUpdate()
	}

	override fun onExplosion() {
		super.onExplosion() // dump exploded info
	}

	override fun onFirstTick(aBaseMetaTileEntity: IGregTechTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity)
	}

	/**
	 * Called when the machine gets invalidated.
	 *
	 * @see IMetaTileEntity.onRemoval
	 * @see TileEntity.invalidate
	 */
	override fun onRemoval() {
		super.onRemoval()
	}

	/**
	 * Called when the [mBaseMetaTileEntity] is set to `null`.
	 *
	 * @see IMetaTileEntity.inValidate
	 */
	override fun inValidate() {
		super.inValidate()
	}

	/**
	 * Called when uncaught exception occurred on processing this tick.
	 */
	override fun onTickFail(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		super.onTickFail(aBaseMetaTileEntity, aTick)
	}

	override fun onSetActive(active: Boolean) {
		super.onSetActive(active)
	}

	override fun onDisableWorking() {
		super.onDisableWorking()
	}

	override fun onFacingChange() {
		super.onFacingChange()
	}

	// endregion

	/**
	 * Called when the block is broken.
	 *
	 * @return true if the item in slot [index] should be dropped.
	 * @see IMetaTileEntity.shouldDropItemAt
	 */
	override fun shouldDropItemAt(index: Int): Boolean {
		return super.shouldDropItemAt(index)
	}

	override fun getMetaName(): String {
		return super.metaName
	}

	// region Vanilla Interactions

	override fun onRightclick(aBaseMetaTileEntity: IGregTechTileEntity, aPlayer: EntityPlayer): Boolean {
		return super.onRightclick(aBaseMetaTileEntity, aPlayer)
	}

	override fun onRightclick(
		aBaseMetaTileEntity: IGregTechTileEntity,
		aPlayer: EntityPlayer,
		side: ForgeDirection,
		aX: Float,
		aY: Float,
		aZ: Float,
	): Boolean {
		return super.onRightclick(aBaseMetaTileEntity, aPlayer, side, aX, aY, aZ)
	}

	override fun onLeftclick(aBaseMetaTileEntity: IGregTechTileEntity, aPlayer: EntityPlayer) {
		super.onLeftclick(aBaseMetaTileEntity, aPlayer)
	}

	// endregion

	override fun receiveClientEvent(aEventID: Byte, aValue: Byte) {
		super.receiveClientEvent(aEventID, aValue)
	}

	// region Sound

	override fun startSoundLoop(aIndex: Byte, aX: Double, aY: Double, aZ: Double) {
		super.startSoundLoop(aIndex, aX, aY, aZ)
	}

	override fun stopSoundLoop(aValue: Byte, aX: Double, aY: Double, aZ: Double) {
		super.stopSoundLoop(aValue, aX, aY, aZ)
	}

	// endregion

	override fun doExplosion(aExplosionPower: Long) {
		super.doExplosion(aExplosionPower)
	}

	/**
	 * @return true to output a warning message if this tick has some laggy operations.
	 * @see IMetaTileEntity.doTickProfilingMessageDuringThisTick
	 * @see doTickProfilingInThisTick
	 */
	override fun doTickProfilingMessageDuringThisTick(): Boolean {
		return super.doTickProfilingMessageDuringThisTick()
	}

	/**
	 * Adds Machine specific debug information to [aList].
	 *
	 * @see IMetaTileEntity.getSpecialDebugInfo
	 */
	override fun getSpecialDebugInfo(
		aBaseMetaTileEntity: IGregTechTileEntity,
		aPlayer: EntityPlayer,
		aLogLevel: Int,
		aList: ArrayList<String>,
	): ArrayList<String> {
		aList.add("${ECF.BLUE}Presented by Overpowered!")
		return super.getSpecialDebugInfo(aBaseMetaTileEntity, aPlayer, aLogLevel, aList)
	}

	// unknown usage
	override fun getSpecialVoltageToolTip(): String {
		return super.specialVoltageToolTip
	}

	/**
	 * Register the textures of the block.
	 *
	 * @see IMetaTileEntity.registerIcons
	 */
	@SideOnly(Side.CLIENT)
	override fun registerIcons(aBlockIconRegister: IIconRegister) {
		super.registerIcons(aBlockIconRegister)
	}

	/**
	 * @return true if rewriting rendering.
	 * @see IMetaTileEntity.renderInInventory
	 */
	@SideOnly(Side.CLIENT)
	override fun renderInInventory(aBlock: Block?, aMeta: Int, aRenderer: RenderBlocks?): Boolean {
		return super.renderInInventory(aBlock, aMeta, aRenderer)
	}

	/**
	 * @return true if rewriting rendering.
	 * @see IMetaTileEntity.renderInWorld
	 */
	@SideOnly(Side.CLIENT)
	override fun renderInWorld(
		aWorld: IBlockAccess?,
		aX: Int,
		aY: Int,
		aZ: Int,
		aBlock: Block?,
		aRenderer: RenderBlocks?,
	): Boolean {
		return super.renderInWorld(aWorld, aX, aY, aZ, aBlock, aRenderer)
	}


	/**
	 * @return the explosion resistance
	 */
	override fun getExplosionResistance(side: ForgeDirection?): Float {
		return super.getExplosionResistance(side)
	}

	/**
	 * @return the "real" inventory to store the data.
	 */
	override fun getRealInventory(): Array<ItemStack> {
		return super.realInventory
	}

	// unknown usage
	override fun connectsToItemPipe(side: ForgeDirection): Boolean {
		return super.connectsToItemPipe(side)
	}

	/**
	 * Called when the color is changed on the server side.
	 *
	 * Used to notify the neighbor blocks that my color is changed.
	 * It should only be useful for color-able blocks like pipe.
	 *
	 * @see gregtech.api.interfaces.tileentity.IColoredTileEntity.setColorization
	 */
	override fun onColorChangeServer(aColor: Byte) {
		super.onColorChangeServer(aColor)
	}

	/**
	 * Called when the color is changed on the client side, by color-changing packets from server.
	 *
	 * It should only be useful for color-able blocks like pipe.
	 */
	override fun onColorChangeClient(aColor: Byte) {
		super.onColorChangeClient(aColor)
	}

	// region GUI

	override fun getGUITextureSet(): GUITextureSet {
		return GUITextureSet.DEFAULT
	}

	override fun getGUIColorization(): Int {
		return super.getGUIColorization()
	}

	override fun onOpenGUI() {
		super.onOpenGUI()
	}

	override fun onCloseGUI() {
		super.onCloseGUI()
	}

	/**
	 * Called when constructing Modular UI.
	 *
	 * @return the charger slot widget.
	 */
	override fun createChargerSlot(x: Int, y: Int): SlotWidget {
		return super.createChargerSlot(x, y)
	}

	/**
	 * Called when constructing Modular UI.
	 */
	override fun addUIWidgets(builder: ModularWindow.Builder, buildContext: UIBuildContext) {
		super.addUIWidgets(builder, buildContext)
	}

	// endregion

	// region Vanilla Properties

	override fun getLightOpacity(): Int {
		return super.lightOpacity
	}

	override fun allowGeneralRedstoneOutput(): Boolean {
		return super.allowGeneralRedstoneOutput()
	}

	/**
	 * @return the strength of the redstone power from redstone comparator.
	 * @see IMetaTileEntity.getComparatorValue
	 */
	override fun getComparatorValue(side: ForgeDirection): Byte {
		return super.getComparatorValue(side)
	}

	override fun addCollisionBoxesToList(
		aWorld: World?,
		aX: Int,
		aY: Int,
		aZ: Int,
		inputAABB: AxisAlignedBB?,
		outputAABB: MutableList<AxisAlignedBB>?,
		collider: Entity?,
	) {
		super.addCollisionBoxesToList(aWorld, aX, aY, aZ, inputAABB, outputAABB, collider)
	}

	override fun getCollisionBoundingBoxFromPool(aWorld: World?, aX: Int, aY: Int, aZ: Int): AxisAlignedBB {
		return super.getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ)
	}

	override fun onEntityCollidedWithBlock(aWorld: World?, aX: Int, aY: Int, aZ: Int, collider: Entity?) {
		super.onEntityCollidedWithBlock(aWorld, aX, aY, aZ, collider)
	}

	/**
	 * Called when the ItemStack is crafted/smelted.
	 *
	 * @see IMetaTileEntity.onCreated
	 */
	override fun onCreated(aStack: ItemStack, aWorld: World, aPlayer: EntityPlayer) {
		super.onCreated(aStack, aWorld, aPlayer)
	}

	// endregion

	/**
	 * @return true if this machine has an alternative text for its running mode.
	 * @see getAlternativeModeText
	 */
	override fun hasAlternativeModeText(): Boolean {
		return super.hasAlternativeModeText()
	}

	/**
	 * Returns the alternative text for the running mode, when the Soft Mallet switches the running mode.
	 *
	 * The default text should be either: `Machine Processing: Enabled` or `Machine Processing: Disabled`.
	 *
	 * To enable this, you need override [hasAlternativeModeText].
	 *
	 * @return the alternative text for the running mode.
	 * @see hasAlternativeModeText
	 * @see BaseMetaTileEntity.onRightclick
	 */
	override fun getAlternativeModeText(): String {
		return super.alternativeModeText
	}

	override fun shouldJoinIc2Enet(): Boolean {
		return super.shouldJoinIc2Enet()
	}

	override fun getInventoryHandler(): ItemStackHandler {
		return super.getInventoryHandler()
	}

	/**
	 * @return the localized name.
	 */
	override fun getLocalName(): String {
		return super.localName
	}

	override fun getTextColorOrDefault(textType: String?, defaultColor: Int): Int {
		return super.getTextColorOrDefault(textType, defaultColor)
	}

	override fun getCleanroom(): ICleanroom? {
		return super.cleanroom
	}

	override fun setCleanroom(cleanroom: ICleanroom?) {
		super.cleanroom = cleanroom
	}

	/**
	 * @return true if this MetaTileEntity is not null or marked as dead.
	 */
	override fun isValid(): Boolean {
		return super.isValid
	}

	// region Machine Type

	override fun isElectric(): Boolean {
		return super.isElectric
	}

	override fun isPneumatic(): Boolean {
		return super.isPneumatic
	}

	override fun isSteampowered(): Boolean {
		return super.isSteampowered
	}

	override fun getSteamVariant(): SteamVariant {
		return super.steamVariant
	}

	// endregion

	// region EU Generating

	override fun isEnetOutput(): Boolean {
		return super.isEnetOutput
	}

	override fun maxEUOutput(): Long {
		return super.maxEUOutput()
	}

	override fun maxAmperesOut(): Long {
		return super.maxAmperesOut()
	}

	override fun isTransformingLowEnergy(): Boolean {
		return super.isTransformingLowEnergy
	}

	// endregion

	// region Machine Related Variable

	// region Energy

	override fun getEUVar(): Long {
		return super.euVar
	}

	override fun setEUVar(aEnergy: Long) {
		super.euVar = aEnergy
	}

	override fun getSteamVar(): Long {
		return super.steamVar
	}

	override fun setSteamVar(aSteam: Long) {
		super.steamVar = aSteam
	}

	override fun maxSteamStore(): Long {
		return super.maxSteamStore()
	}

	// unknown usage
	override fun isEnetInput(): Boolean {
		return super.isEnetInput
	}

	override fun maxEUStore(): Long {
		return super.maxEUStore()
	}

	override fun maxEUInput(): Long {
		return super.maxEUInput()
	}

	override fun getMinimumStoredEU(): Long {
		return super.minimumStoredEU
	}

	override fun maxAmperesIn(): Long {
		return super.maxAmperesIn()
	}

	// endregion

	// region Fluids

	override fun fill(side: ForgeDirection, aFluid: FluidStack?, doFill: Boolean): Int {
		return super.fill(side, aFluid, doFill)
	}

	override fun drain(side: ForgeDirection, aFluid: FluidStack?, doDrain: Boolean): FluidStack {
		return super.drain(side, aFluid, doDrain)
	}

	override fun drain(side: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack {
		return super.drain(side, maxDrain, doDrain)
	}

	override fun getTankPressure(): Int {
		return super.tankPressure
	}

	override fun getCapacity(): Int {
		return super.capacity
	}

	override fun getRealCapacity(): Int {
		return super.realCapacity
	}

	// endregion

	// region Slot Indices

	override fun rechargerSlotStartIndex(): Int {
		return super.rechargerSlotStartIndex()
	}

	override fun dechargerSlotStartIndex(): Int {
		return super.dechargerSlotStartIndex()
	}

	override fun rechargerSlotCount(): Int {
		return super.rechargerSlotCount()
	}

	override fun dechargerSlotCount(): Int {
		return super.dechargerSlotCount()
	}

	/**
	 * @return the first index of input slots in [mInventory].
	 */
	override fun getInputSlot(): Int {
		return super.inputSlot
	}

	/**
	 * @return the first index of output slots in [mInventory].
	 */
	override fun getOutputSlot(): Int {
		return super.outputSlot
	}

	/**
	 * @return the index of the circuit slot.
	 */
	override fun getCircuitSlot(): Int {
		return super.circuitSlot
	}

	// endregion

	override fun isOutputFacing(side: ForgeDirection): Boolean {
		return super.isOutputFacing(side)
	}

	override fun isInputFacing(side: ForgeDirection): Boolean {
		return super.isInputFacing(side)
	}

	// endregion

	/**
	 * @return true if this machine restricts the accessibility to players.
	 * @see BaseMetaTileEntity.privateAccess
	 * @see BaseMetaTileEntity.playerOwnsThis
	 */
	override fun ownerControl(): Boolean {
		return super.ownerControl()
	}

	override fun hasSidedRedstoneOutputBehavior(): Boolean {
		return super.hasSidedRedstoneOutputBehavior()
	}

	override fun willExplodeInRain(): Boolean {        // OP: nobody wants his machines to explode when testing.
		if(OP.dev) return false

		return super.willExplodeInRain()
	}

	// region Digital Chest / Quantum Chest

	// unknown usage.
	override fun isDigitalChest(): Boolean {
		return super.isDigitalChest
	}

	override fun getStoredItemData(): Array<ItemStack>? {
		return super.storedItemData
	}

	override fun setItemCount(aCount: Int) {
		super.setItemCount(aCount)
	}

	override fun getMaxItemCount(): Int {
		return super.maxItemCount
	}

	// endregion

	/**
	 * @return the size of the [mInventory].
	 */
	override fun getSizeInventory(): Int {
		return super.sizeInventory
	}

	/**
	 * It will do the boundary check for you. If out of bound, it returns `null`.
	 *
	 * @return the ItemStack in the [aIndex] of [mInventory].
	 */
	override fun getStackInSlot(aIndex: Int): ItemStack? {
		return super.getStackInSlot(aIndex)
	}

	/**
	 * @return true if the item in slot [aIndex] can be set to zero sized; should not perform any operation.
	 * @see IMetaTileEntity.setStackToZeroInsteadOfNull
	 */
	override fun setStackToZeroInsteadOfNull(aIndex: Int): Boolean {
		return super.setStackToZeroInsteadOfNull(aIndex)
	}

	/**
	 * Set the item in the [aIndex] of [mInventory] to [aStack].
	 */
	override fun setInventorySlotContents(aIndex: Int, aStack: ItemStack?) {
		super.setInventorySlotContents(aIndex, aStack)
	}

	/**
	 * @see getInventoryName
	 */
	override fun hasCustomInventoryName(): Boolean {
		return super.hasCustomInventoryName()
	}

	/**
	 * The inventory name should be used in the GUIs.
	 *
	 * This should only be used when [hasCustomInventoryName] is true.
	 *
	 * @return the inventory name.
	 * @see net.minecraft.inventory.IInventory.getInventoryName
	 * @see net.minecraft.client.gui.GuiHopper.drawGuiContainerForegroundLayer
	 */
	override fun getInventoryName(): String {
		return super.getInventoryName()
	}

	/**
	 * @return the max stack size of items in [mInventory].
	 * @see net.minecraft.inventory.IInventory.getInventoryStackLimit
	 */
	override fun getInventoryStackLimit(): Int {
		return super.inventoryStackLimit
	}

	/**
	 * Check if the [aStack] is valid to interact with slot [aIndex] in [mInventory].
	 *
	 * @see isValidSlot
	 * @sample gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_IndustrialApiary.isItemValidForSlot
	 */
	override fun isItemValidForSlot(aIndex: Int, aStack: ItemStack?): Boolean {
		return super.isItemValidForSlot(aIndex, aStack)
	}

	/**
	 * Take out [aAmount] item in the [aIndex] in [mInventory].
	 *
	 * @return the taken-out ItemStack.
	 * @see IMetaTileEntity.decrStackSize
	 */
	override fun decrStackSize(aIndex: Int, aAmount: Int): ItemStack? {
		return super.decrStackSize(aIndex, aAmount)
	}

	/**
	 * @return the array of indices that can be accessed from the side [ordinalSide].
	 * @see ForgeDirection.getOrientation
	 */
	override fun getAccessibleSlotsFromSide(ordinalSide: Int): IntArray {
		return super.getAccessibleSlotsFromSide(ordinalSide)
	}

	/**
	 * @return true if the automation can insert the [aStack] to the [aIndex] in [mInventory] from [ordinalSide] side.
	 * @see ForgeDirection.getOrientation
	 * @see isValidSlot
	 * @see allowPutStack
	 */
	override fun canInsertItem(aIndex: Int, aStack: ItemStack?, ordinalSide: Int): Boolean {
		return super.canInsertItem(aIndex, aStack, ordinalSide)
	}

	/**
	 * @return true if the automation can extract the [aStack] from the [aIndex] in [mInventory] from [ordinalSide] side.
	 * @see isValidSlot
	 * @see allowPullStack
	 */
	override fun canExtractItem(aIndex: Int, aStack: ItemStack?, ordinalSide: Int): Boolean {
		return super.canExtractItem(aIndex, aStack, ordinalSide)
	}

	/**
	 * *Note that the original logic is try to [fill] 1L [aFluid] with simulation enabled, so the actual checking is in the [fill] method.*
	 *
	 * @return true if the [aFluid] can be inserted from [side].
	 * @see net.minecraftforge.fluids.IFluidHandler.canFill
	 */
	override fun canFill(side: ForgeDirection, aFluid: Fluid): Boolean {
		return super.canFill(side, aFluid)
	}

	/**
	 * *Note that the original logic is try to [drain] 1L [aFluid] with simulation enabled, so the actual checking is in the [drain] method.*
	 *
	 * @return true if the [aFluid] can be extracted from [side].
	 * @see net.minecraftforge.fluids.IFluidHandler.canDrain
	 */
	override fun canDrain(side: ForgeDirection?, aFluid: Fluid?): Boolean {
		return super.canDrain(side, aFluid)
	}

	/**
	 * @return the amount of fluid that was filled to this machine.
	 */
	override fun fill_default(side: ForgeDirection?, aFluid: FluidStack?, doFill: Boolean): Int {
		return super.fill_default(side, aFluid, doFill)
	}

	/**
	 * @return the FluidTankInfo object wrapping the tank data (fluidStack, capacity).
	 * @see FluidTankInfo
	 * @see net.minecraftforge.fluids.IFluidTank.getInfo
	 */
	override fun getInfo(): FluidTankInfo {
		return super.info
	}

	/**
	 * Return ItemStack to drop into the world as EntityItem, if it is expected to, like Crafting Table, or `null`.
	 * The caller should iterate the inventory by [aIndex].
	 *
	 * @return the ItemStack to drop into the world as EntityItem.
	 * @sample net.minecraft.inventory.InventoryCrafting.getStackInSlotOnClosing
	 * @see net.minecraft.inventory.ContainerPlayer.onContainerClosed
	 */
	override fun getStackInSlotOnClosing(aIndex: Int): ItemStack {
		return super.getStackInSlotOnClosing(aIndex)
	}

	/**
	 * Mark this tile as dirty, so that it will be saved when the world or chunk is saved.
	 * @see net.minecraft.inventory.IInventory.markDirty
	 */
	override fun markDirty() {
		super.markDirty()
	}

	/**
	 * @return true if usable to the player.
	 */
	override fun isUseableByPlayer(entityplayer: EntityPlayer): Boolean {
		return super.isUseableByPlayer(entityplayer)
	}

	// unknown usage, looks like called on inventory opening.
	/**
	 * @see net.minecraft.inventory.IInventory.openInventory
	 */
	override fun openInventory() {
		super.openInventory()
	}

	// unknown usage, looks like called on inventory closing.
	/**
	 * @see net.minecraft.inventory.IInventory.closeInventory
	 */
	override fun closeInventory() {
		super.closeInventory()
	}

	/**
	 * @return true if it should trigger block update.
	 * @see BaseMetaTileEntity.setFrontFacing
	 */
	override fun shouldTriggerBlockUpdate(): Boolean {
		return super.shouldTriggerBlockUpdate()
	}

	/**
	 * @return the AE2 cable connection rendering type.
	 */
	override fun getCableConnectionType(forgeDirection: ForgeDirection?): AECableType {
		return super.getCableConnectionType(forgeDirection)
	}

	/**
	 * @return the AE2 network proxy.
	 */
	override fun getProxy(): AENetworkProxy? {
		return super.proxy
	}

	// unknown usage, looks like called on grid changed.
	override fun gridChanged() {
		super.gridChanged()
	}

	/**
	 * @return the AE2 network status string.
	 */
	override fun getAEDiagnostics(): String {
		return super.getAEDiagnostics()
	}

	// unknown usage
	override fun isMachineBlockUpdateRecursive(): Boolean {
		return super.isMachineBlockUpdateRecursive
	}

	abstract override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity

	/**
	 * Called when the chunk is unloading
	 */
	override fun onUnload() {
		super.onUnload()
	}

	/**
	 * Randomly called to display particles.
	 *
	 * @see IMetaTileEntity.onRandomDisplayTick
	 * @see Block.randomDisplayTick
	 */
	override fun onRandomDisplayTick(aBaseMetaTileEntity: IGregTechTileEntity) {
		super.onRandomDisplayTick(aBaseMetaTileEntity)
	}

	override fun getGUIWidth(): Int {
		return super.guiWidth
	}

	override fun getGUIHeight(): Int {
		return super.guiHeight
	}

	/**
	 * @return true if the player inventory is rendered in the GUI.
	 */
	override fun doesBindPlayerInventory(): Boolean {
		return super.doesBindPlayerInventory()
	}

	/**
	 * Called before the block is destroyed, before inventory dropping code has executed.
	 */
	override fun onBlockDestroyed() {
		super.onBlockDestroyed()
	}

	/**
	 * Used to add additional [tooltip] for the [stack] of this machine. This is instance-specific.
	 *
	 * @see IMetaTileEntity.addAdditionalTooltipInformation
	 */
	override fun addAdditionalTooltipInformation(stack: ItemStack, tooltip: MutableList<String>) {
		super.addAdditionalTooltipInformation(stack, tooltip)
	}

	/**
	 * @return the ItemStacks to be viewed by HoloGlasses, or `null` for default.
	 * @see IMetaTileEntity.getItemsForHoloGlasses
	 */
	override fun getItemsForHoloGlasses(): MutableList<ItemStack>? {
		return super.itemsForHoloGlasses
	}

	/**
	 * Used to add information to waila.
	 *
	 * @see gregtech.api.interfaces.tileentity.IGregtechWailaProvider.getWailaBody
	 * @see mcp.mobius.waila.api.IWailaDataProvider.getWailaBody
	 */
	override fun getWailaBody(
		itemStack: ItemStack,
		currenttip: MutableList<String>,
		accessor: IWailaDataAccessor,
		config: IWailaConfigHandler,
	) {
		super.getWailaBody(itemStack, currenttip, accessor, config)
	}

	/**
	 * Used to write information to NBT.
	 *
	 * @see gregtech.api.interfaces.tileentity.IGregtechWailaProvider.getWailaNBTData
	 * @see mcp.mobius.waila.api.IWailaDataProvider.getNBTData
	 */
	override fun getWailaNBTData(
		player: EntityPlayerMP,
		tile: TileEntity,
		tag: NBTTagCompound,
		world: World,
		x: Int,
		y: Int,
		z: Int,
	) {
		super.getWailaNBTData(player, tile, tag, world, x, y, z)
	}

	/**
	 * Initialize the machine when it is placed to the world.
	 * [aNBT] can be null.
	 */
	override fun initDefaultModes(aNBT: NBTTagCompound?) {
		super.initDefaultModes(aNBT)
	}

	/**
	 * Save the data to [aNBT].
	 */
	override fun saveNBTData(aNBT: NBTTagCompound) {
		super.saveNBTData(aNBT)
	}

	/**
	 * Read the data from [aNBT]
	 */
	override fun loadNBTData(aNBT: NBTTagCompound) {
		super.loadNBTData(aNBT)
	}

	/**
	 * @return true if the [aCoverID] cover can be placed at the [side]
	 */
	override fun allowCoverOnSide(side: ForgeDirection, aCoverID: GT_ItemStack): Boolean {
		return super.allowCoverOnSide(side, aCoverID)
	}

	/**
	 * @see IMetaTileEntity.onPostTick
	 */
	override fun onPostTick(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		super.onPostTick(aBaseMetaTileEntity, aTick)
	}

	/**
	 * @return true if facing to [facing] is allowed.
	 */
	override fun isFacingValid(facing: ForgeDirection): Boolean {
		return super.isFacingValid(facing)
	}

	/**
	 * @return true if the machine uses Modular UI.
	 */
	override fun useModularUI(): Boolean {
		return super.useModularUI()
	}

	/**
	 * [aStack] may not be `null` because it has already been checked in [canExtractItem].
	 *
	 * The default checks:
	 * 1. [side] MUST NOT be the [mMainFacing]
	 * 1. [aIndex] MUST be greater than or equals to (>=) [getOutputSlot]
	 * 1. [aIndex] MUST be smaller than (<) [getOutputSlot] + length of [mOutputItems]
	 *
	 * @return true if [aStack] can be extracted from [aIndex] in [mInventory] from [side].
	 */
	override fun allowPullStack(
		aBaseMetaTileEntity: IGregTechTileEntity,
		aIndex: Int,
		side: ForgeDirection,
		aStack: ItemStack,
	): Boolean {
		return super.allowPullStack(aBaseMetaTileEntity, aIndex, side, aStack)
	}

	/**
	 * [aStack] may not be `null` because it has already been checked in [canInsertItem].
	 *
	 * The default checks:
	 * 1. [side] MUST NOT be the [mMainFacing]
	 * 1. [aIndex] MUST be greater than or equals to (>=) [getInputSlot]
	 * 1. [aIndex] MUST be smaller than [getInputSlot] + [mInputSlotCount]
	 * 1. Unless [mAllowInputFromOutputSide], [side] MUST NOT be front facing
	 * 1. The first slot in [mInventory] that "stack-equals" to [aStack] is [aIndex]
	 * 1. Unless [mDisableFilter], the [aStack] MUST pass [allowPutStackValidated] check
	 *
	 * @return true if [aStack] can be inserted to [aIndex] in [mInventory] from [side].
	 */
	override fun allowPutStack(
		aBaseMetaTileEntity: IGregTechTileEntity,
		aIndex: Int,
		side: ForgeDirection,
		aStack: ItemStack,
	): Boolean {
		return super.allowPutStack(aBaseMetaTileEntity, aIndex, side, aStack)
	}

	/**
	 * The default checks:
	 * 1. *(in [GT_MetaTileEntity_BasicTank])* [aIndex] MUST NOT be [getStackDisplaySlot]
	 * 1. [aIndex] MUST be greater than zero (0)
	 * 1. [aIndex] MUST NOT be [getCircuitSlot]
	 * 1. [aIndex] MUST NOT be [GT_MetaTileEntity_BasicMachine.OTHER_SLOT_COUNT] (5) + [mInputSlotCount] + length of [mOutputItems]
	 *
	 * @return true if [aIndex] in [mInventory] is a valid slot to store items.
	 */
	override fun isValidSlot(aIndex: Int): Boolean {
		return super.isValidSlot(aIndex)
	}

	/**
	 * The default checks:
	 * - [side] MUST NOT be [mMainFacing]
	 * - Unless [mAllowInputFromOutputSide], [side] MUST NOT be front facing
	 *
	 * @return true if fluids can be inserted from [side].
	 */
	override fun isLiquidInput(side: ForgeDirection): Boolean {
		return super.isLiquidInput(side)
	}

	/**
	 * The default check:
	 * 1. [side] MUST NOT be [mMainFacing]
	 *
	 * @return true if the fluids can be extracted from [side]
	 */
	override fun isLiquidOutput(side: ForgeDirection): Boolean {
		return super.isLiquidOutput(side)
	}

	/**
	 * Used to update certain data.
	 *
	 * For machines, it rotates the machine and changing the [mMainFacing] by default.
	 *
	 * @see GT_MetaTileEntity_BasicMachine.onValueUpdate
	 * @see BaseMetaTileEntity.receiveClientEvent
	 */
	@ClientOnly("called on clientside only")
	override fun onValueUpdate(aValue: Byte) {
		super.onValueUpdate(aValue)
	}

	/**
	 * Provide the certain data for [onValueUpdate].
	 *
	 * For machines, it returns ordinal of [mMainFacing] by default.
	 */
	override fun getUpdateData(): Byte {
		return super.updateData
	}

	/**
	 * Play sound [aIndex] at [aX], [aY], [aZ].
	 *
	 * @see gregtech.api.util.GT_Utility.doSoundAtClient
	 */
	@ClientOnly("called on clientside only")
	override fun doSound(aIndex: Byte, aX: Double, aY: Double, aZ: Double) {
		super.doSound(aIndex, aX, aY, aZ)
	}

	// unknown usage
	override fun isSimpleMachine(): Boolean {
		return super.isSimpleMachine
	}

	/**
	 * @return the textures in different layers; the lower indexed texture is earlier to be rendered, like `y-index` in CSS.
	 * @see getTextureSet
	 */
	override fun getTexture(
		baseMetaTileEntity: IGregTechTileEntity,
		sideDirection: ForgeDirection,
		facingDirection: ForgeDirection,
		colorIndex: Int,
		active: Boolean,
		redstoneLevel: Boolean,
	): Array<ITexture> {
		return super.getTexture(baseMetaTileEntity, sideDirection, facingDirection, colorIndex, active, redstoneLevel)
	}

	/**
	 * This is only accessible when [isGivingInformation] is `true`.
	 *
	 * @return the information to print to chat when the machine is interacted by the Scanners.
	 * @see isGivingInformation
	 * @see gregtech.api.interfaces.tileentity.IGregTechDeviceInformation.getInfoData
	 */
	override fun getInfoData(): Array<String> {
		return super.infoData
	}

	/**
	 * @return true if this machine provides info data in [getInfoData].
	 * @see getInfoData
	 * @see gregtech.api.interfaces.tileentity.IGregTechDeviceInformation.isGivingInformation
	 */
	override fun isGivingInformation(): Boolean {
		return super.isGivingInformation
	}

	// unknown usage
	override fun isOverclockerUpgradable(): Boolean {
		return super.isOverclockerUpgradable
	}

	// unknown usage
	override fun isTransformerUpgradable(): Boolean {
		return super.isTransformerUpgradable
	}

	/**
	 * @return the time that this machine has already progressed.
	 */
	override fun getProgresstime(): Int {
		return super.progresstime
	}

	/**
	 * @return the time duration that this machine needs to progress each time
	 */
	override fun maxProgresstime(): Int {
		return super.maxProgresstime()
	}

	/**
	 * Increase the [mProgresstime] and return the remaining time to finish this run.
	 *
	 * @return the remaining time to finish this run of progress
	 */
	override fun increaseProgress(aProgress: Int): Int {
		return super.increaseProgress(aProgress)
	}

	// unknown usage
	override fun isTeleporterCompatible(): Boolean {
		return super.isTeleporterCompatible
	}

	/**
	 * Generate a texture set.
	 *
	 * It will be called in construct by default.
	 * The return value is cached in [mTextures], which is used in [getTexture] by default.
	 *
	 * Using [cn.taskeren.op.gt.utils.TextureSetBuilder] is recommended.
	 * Stop using methods like [getSideFacingActive].
	 *
	 * You can leave this method empty, and replace the logic in [getTexture].
	 *
	 * @param aTextures the default texture of this machine.
	 * @return the texture set of this machine.
	 * @see getTexture
	 * @see com.github.technus.tectech.thing.metaTileEntity.single.GT_MetaTileEntity_DebugPowerGenerator.getTexture
	 */
	override fun getTextureSet(aTextures: Array<out ITexture>): Array<Array<Array<ITexture>>> {
		return super.getTextureSet(aTextures)
	}

	// region Abstract from Basic Tank

	/**
	 * @return true if this machine extract the fluid from the fluid containers (ItemStacks like Empty Cell).
	 * @see GT_MetaTileEntity_BasicTank.onPreTick
	 */
	override fun doesEmptyContainers(): Boolean {
		return super.doesEmptyContainers()
	}

	/**
	 * @return true if this machine fills the fluid containers (ItemStacks like Empty Cell) with fluid in the machine.
	 * @see GT_MetaTileEntity_BasicTank.onPreTick
	 */
	override fun doesFillContainers(): Boolean {
		return super.doesFillContainers()
	}

	/**
	 * If this is false, [fill] will not work!
	 *
	 * @return true if this machine can be filled with fluids. (insert fluids to the internal tanks.)
	 * @see fill
	 */
	override fun canTankBeFilled(): Boolean {
		return super.canTankBeFilled()
	}

	/**
	 * If this is false, [drain] will not work!
	 *
	 * @return true if this machine can be drained fluids. (extract fluids from the internal tanks.)
	 * @see drain
	 */
	override fun canTankBeEmptied(): Boolean {
		return super.canTankBeEmptied()
	}

	// unknown usage
	override fun displaysItemStack(): Boolean {
		return super.displaysItemStack()
	}

	// unknown usage
	override fun displaysStackSize(): Boolean {
		return super.displaysStackSize()
	}

	// endregion

	/**
	 * @return the index of the special slot to display some ItemStacks named like "Generating: xxxEU".
	 * @see gtPlusPlus.xmod.gregtech.common.tileentities.generators.GregtechMetaTileEntity_RTG.getStackDisplaySlot
	 */
	override fun getStackDisplaySlot(): Int {
		return super.stackDisplaySlot
	}

	// not pretty sure
	override fun isFluidInputAllowed(aFluid: FluidStack): Boolean {
		return super.isFluidInputAllowed(aFluid)
	}

	/**
	 * @return false if the fluid type is locked, and the fluids with amount <= 0 will not disappear.
	 */
	override fun isFluidChangingAllowed(): Boolean {
		return super.isFluidChangingAllowed
	}

	/**
	 * @return the output FluidStack.
	 */
	override fun getDrainableStack(): FluidStack? {
		return super.drainableStack
	}

	/**
	 * Set the [mOutputFluid] to [aFluid].
	 *
	 * @return the output FluidStack [mOutputFluid].
	 */
	override fun setDrainableStack(aFluid: FluidStack?): FluidStack {
		return super.setDrainableStack(aFluid)
	}

	/**
	 * @return true if the input FluidStack is different from output FluidStack.
	 */
	override fun isDrainableStackSeparate(): Boolean {
		return super.isDrainableStackSeparate
	}

	/**
	 * @return the recipe map of this machine, or `null`.
	 */
	override fun getRecipeMap(): RecipeMap<*>? {
		return super.recipeMap
	}

	/**
	 * @return list of ItemStack of Configuration Circuits (Programmed Circuit, P. Bio Circuit, P. Breakthrough Circuit, etc.) that this machine can provide.
	 * @see gregtech.api.GregTech_API.getConfigurationCircuitList
	 */
	override fun getConfigurationCircuits(): List<ItemStack> {
		return super.configurationCircuits
	}

	/**
	 * @return true to add Configuration Circuit slot to Modular UI.
	 */
	override fun allowSelectCircuit(): Boolean {
		return super.allowSelectCircuit()
	}

	/**
	 * @return (maybe) the index of the Configuration Circuit in the GUI (Container) inventory; equals to [getCircuitSlot] by default.
	 */
	override fun getCircuitGUISlot(): Int {
		return super.circuitGUISlot
	}

	/**
	 * @return the starting x of Circuit Slot in the GUI.
	 */
	override fun getCircuitSlotX(): Int {
		return super.circuitSlotX
	}

	/**
	 * @return the starting y of Circuit Slot in the GUI.
	 */
	override fun getCircuitSlotY(): Int {
		return super.circuitSlotY
	}

	/**
	 * @return the [OverclockDescriber] instance.
	 * @see createOverclockDescriber
	 * @see OverclockDescriber
	 */
	override fun getOverclockDescriber(): OverclockDescriber {
		return super.getOverclockDescriber()
	}

	/**
	 * @return the [OverclockDescriber] instance when constructing.
	 * @see overclockDescriber
	 * @see getOverclockDescriber
	 */
	override fun createOverclockDescriber(): OverclockDescriber {
		return super.createOverclockDescriber()
	}

	/**
	 * Called to add GregTech logo.
	 */
	override fun addGregTechLogo(builder: ModularWindow.Builder) {
		super.addGregTechLogo(builder)
	}

	/**
	 * @return true if the [side] is valid for main facing.
	 */
	override fun isValidMainFacing(side: ForgeDirection): Boolean {
		return super.isValidMainFacing(side)
	}

	/**
	 * Set the [mMainFacing] to [side].
	 */
	override fun setMainFacing(side: ForgeDirection): Boolean {
		return super.setMainFacing(side)
	}

	/**
	 * @return the index of special slot in [mInventory].
	 */
	override fun getSpecialSlotIndex(): Int {
		return super.specialSlotIndex
	}

	// unknown usage
	override fun doDisplayThings() {
		super.doDisplayThings()
	}

	/**
	 * Return true if this machine has half of [getMinimumStoredEU] stored.
	 *
	 * @return true if this machine has enough energy to check recipe.
	 * @see gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.onPostTick
	 * @see BaseMetaTileEntity.isUniversalEnergyStored
	 */
	override fun hasEnoughEnergyToCheckRecipe(): Boolean {
		return super.hasEnoughEnergyToCheckRecipe()
	}

	/**
	 * @return true if [aEUt] has been drained from this machine.
	 */
	override fun drainEnergyForProcess(aEUt: Long): Boolean {
		return super.drainEnergyForProcess(aEUt)
	}

	/**
	 * Calculate the overclock and re-assign [mEUt] and [mMaxProgresstime].
	 */
	override fun calculateCustomOverclock(recipe: GT_Recipe) {
		super.calculateCustomOverclock(recipe)
	}

	/**
	 * Calculate the overclock and re-assign [mEUt] and [mMaxProgresstime].
	 *
	 * It is simpler to the one receive recipes.
	 */
	override fun calculateOverclockedNess(eut: Int, duration: Int) {
		super.calculateOverclockedNess(eut, duration)
	}

	/**
	 * @return the ItemStack in the [getSpecialSlot] in [mInventory].
	 */
	override fun getSpecialSlot(): ItemStack {
		return super.specialSlot
	}

	/**
	 * @return the ItemStack of [aIndex]-offset + [getOutputSlot] in [mInventory].
	 */
	override fun getOutputAt(aIndex: Int): ItemStack {
		return super.getOutputAt(aIndex)
	}

	/**
	 * @return the ItemStacks in range from [getOutputSlot] to [getOutputSlot] + length of [mOutputItems] in [mInventory].
	 */
	override fun getAllOutputs(): Array<ItemStack> {
		return super.getAllOutputs()
	}

	/**
	 * @return true if there is enough space to output the [aRecipe].
	 */
	override fun canOutput(aRecipe: GT_Recipe?): Boolean {
		return super.canOutput(aRecipe)
	}

	/**
	 * @return true if there is enough space to output [aOutputs].
	 */
	override fun canOutput(vararg aOutputs: ItemStack?): Boolean {
		return super.canOutput(*aOutputs)
	}

	/**
	 * @return true if there is enough space to output [aOutput].
	 */
	override fun canOutput(aOutput: FluidStack?): Boolean {
		return super.canOutput(aOutput)
	}

	/**
	 * @return the ItemStack of [aIndex]-offset + [getInputSlot] in [mInventory].
	 */
	override fun getInputAt(aIndex: Int): ItemStack {
		return super.getInputAt(aIndex)
	}

	/**
	 * @return the ItemStacks in range from [getInputSlot] to [getInputSlot] + length of [mInputSlotCount] and [getCircuitSlot] in [mInventory].
	 */
	override fun getAllInputs(): Array<ItemStack> {
		return super.getAllInputs()
	}

	/**
	 * @return true if all output slots are empty (`null`).
	 */
	override fun isOutputEmpty(): Boolean {
		return super.isOutputEmpty()
	}

	/**
	 * @return true if auto-output for item is enabled.
	 */
	override fun doesAutoOutput(): Boolean {
		return super.doesAutoOutput()
	}

	/**
	 * @return true if auto-output for fluid is enabled.
	 */
	override fun doesAutoOutputFluids(): Boolean {
		return super.doesAutoOutputFluids()
	}

	/**
	 * @return true if allow to check recipe.
	 * @see onPostTick
	 */
	override fun allowToCheckRecipe(): Boolean {
		return super.allowToCheckRecipe()
	}

	/**
	 * @return true if use pipe facing textures.
	 * @see getTexture
	 */
	override fun showPipeFacing(): Boolean {
		return super.showPipeFacing()
	}

	/**
	 * Called when this machine starts process. Used to play sounds.
	 */
	override fun startProcess() {
		super.startProcess()
	}

	/**
	 * Called when this machine finishes process. Used to play sounds.
	 */
	override fun endProcess() {
		super.endProcess()
	}

	/**
	 * Called when this machine stops process when aborted. Used to play sounds.
	 */
	override fun abortProcess() {
		super.abortProcess()
	}

	/**
	 * Stutter(跳电)
	 *
	 * Called when this machine pauses process when stuttered. Used to play sounds.
	 */
	override fun stutterProcess() {
		super.stutterProcess()
	}

	/**
	 * If the machine allows insufficient energy, the progress ([mProgresstime]) won't drop to zero at once, but slightly
	 * decrease.
	 *
	 * @return true if this machine allows insufficient energy.
	 * @see gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.onPostTick
	 */
	override fun canHaveInsufficientEnergy(): Boolean {
		return super.canHaveInsufficientEnergy()
	}

	/**
	 * @return true if this machine uses standard stutter sound.
	 * @see GT_MetaTileEntity_BasicMachine.stutterProcess
	 */
	override fun useStandardStutterSound(): Boolean {
		return super.useStandardStutterSound()
	}

	/**
	 * If [mDisableMultiStack], it is assumed that there is no such kind of item inside any input slots already.
	 *
	 * @return true if [aStack] is allowed to be inserted to [aIndex] in [mInventory].
	 */
	override fun allowPutStackValidated(
		aBaseMetaTileEntity: IGregTechTileEntity?,
		aIndex: Int,
		side: ForgeDirection?,
		aStack: ItemStack?,
	): Boolean {
		return super.allowPutStackValidated(aBaseMetaTileEntity, aIndex, side, aStack)
	}

	/**
	 * @return recipe checking result with OC allowed.
	 * @see checkRecipe
	 */
	@MagicConstant(
		intValues = [
			DID_NOT_FIND_RECIPE.toLong(), // 0
			FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS.toLong(), // 1
			FOUND_AND_SUCCESSFULLY_USED_RECIPE.toLong(), // 2
		]
	)
	override fun checkRecipe(): Int {
		return super.checkRecipe()
	}

	/**
	 * Find the recipe from [getRecipeMap] with [getAllInputs] (items), [getFillableStack] (fluids), [getSpecialSlot] (special slot), [mTier] (voltage),
	 * and [mLastRecipe] (last recipe). And put outputs to [mOutputItems] and [mOutputFluid].
	 *
	 * The recipe metadata check should be in this method.
	 *
	 * Meaning of [GT_Recipe.mSpecialValue]:
	 * - `-100` requires low gravity
	 * - `-200` requires cleanroom
	 * - `-300` requires both low gravity and cleanroom
	 *
	 * @return the recipe checking result.
	 */
	@MagicConstant(
		intValues = [
			DID_NOT_FIND_RECIPE.toLong(), // 0
			FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS.toLong(), // 1
			FOUND_AND_SUCCESSFULLY_USED_RECIPE.toLong(), // 2
		]
	)
	override fun checkRecipe(skipOC: Boolean): Int {
		return super.checkRecipe(skipOC)
	}

	// region Getters for TextureSet

	@Deprecated("Use TextureSetBuilder instead")
	override fun getSideFacingActive(aColor: Byte): Array<ITexture> {
		return super.getSideFacingActive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getSideFacingInactive(aColor: Byte): Array<ITexture> {
		return super.getSideFacingInactive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getFrontFacingActive(aColor: Byte): Array<ITexture> {
		return super.getFrontFacingActive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getFrontFacingInactive(aColor: Byte): Array<ITexture> {
		return super.getFrontFacingInactive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getTopFacingActive(aColor: Byte): Array<ITexture> {
		return super.getTopFacingActive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getTopFacingInactive(aColor: Byte): Array<ITexture> {
		return super.getTopFacingInactive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getBottomFacingActive(aColor: Byte): Array<ITexture> {
		return super.getBottomFacingActive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getBottomFacingInactive(aColor: Byte): Array<ITexture> {
		return super.getBottomFacingInactive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getBottomFacingPipeActive(aColor: Byte): Array<ITexture> {
		return super.getBottomFacingPipeActive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getBottomFacingPipeInactive(aColor: Byte): Array<ITexture> {
		return super.getBottomFacingPipeInactive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getTopFacingPipeActive(aColor: Byte): Array<ITexture> {
		return super.getTopFacingPipeActive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getTopFacingPipeInactive(aColor: Byte): Array<ITexture> {
		return super.getTopFacingPipeInactive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getSideFacingPipeActive(aColor: Byte): Array<ITexture> {
		return super.getSideFacingPipeActive(aColor)
	}

	@Deprecated("Use TextureSetBuilder instead")
	override fun getSideFacingPipeInactive(aColor: Byte): Array<ITexture> {
		return super.getSideFacingPipeInactive(aColor)
	}

	// endregion

	/**
	 * @return the UI properties.
	 */
	override fun getUIProperties(): BasicUIProperties {
		return super.getUIProperties()
	}

	/**
	 * Called to add IO slots to the GUI.
	 */
	override fun addIOSlots(builder: ModularWindow.Builder, uiProperties: BasicUIProperties) {
		super.addIOSlots(builder, uiProperties)
	}

	/**
	 * Called to add Progress Bar to the GUI.
	 */
	override fun addProgressBar(builder: ModularWindow.Builder, uiProperties: BasicUIProperties) {
		super.addProgressBar(builder, uiProperties)
	}

	/**
	 * @return the input item slot widget.
	 */
	override fun createItemInputSlot(index: Int, backgrounds: Array<out IDrawable>, pos: Pos2d): SlotWidget {
		return super.createItemInputSlot(index, backgrounds, pos)
	}

	/**
	 * @return the output item slot widget.
	 */
	override fun createItemOutputSlot(index: Int, backgrounds: Array<out IDrawable>, pos: Pos2d): SlotWidget {
		return super.createItemOutputSlot(index, backgrounds, pos)
	}

	/**
	 * @return the special item slot widget.
	 */
	override fun createSpecialSlot(
		backgrounds: Array<out IDrawable>,
		pos: Pos2d,
		uiProperties: BasicUIProperties,
	): SlotWidget {
		return super.createSpecialSlot(backgrounds, pos, uiProperties)
	}

	/**
	 * @return the fluid slot widget.
	 * @see createFluidInputSlot
	 * @see fluidTank
	 */
	override fun createFluidSlot(): FluidSlotWidget {
		return super.createFluidSlot()
	}

	/**
	 * @return the input fluid slot widget.
	 * @see fluidTank
	 */
	override fun createFluidInputSlot(backgrounds: Array<out IDrawable>?, pos: Pos2d?): FluidSlotWidget {
		return super.createFluidInputSlot(backgrounds, pos)
	}

	/**
	 * @return the output fluid slot widget.
	 * @see fluidOutputTank
	 */
	override fun createFluidOutputSlot(backgrounds: Array<out IDrawable>?, pos: Pos2d?): FluidSlotWidget {
		return super.createFluidOutputSlot(backgrounds, pos)
	}

	/**
	 * @return the charger slot widget.
	 */
	override fun createChargerSlot(x: Int, y: Int, tooltipKey: String?, tooltipArgs: Array<out Any>?): SlotWidget {
		return super.createChargerSlot(x, y, tooltipKey, tooltipArgs)
	}

	/**
	 * @return the auto-output for item button widget.
	 */
	override fun createItemAutoOutputButton(): CycleButtonWidget {
		return super.createItemAutoOutputButton()
	}

	/**
	 * @return the auto-output for fluid button widget.
	 */
	override fun createFluidAutoOutputButton(): CycleButtonWidget {
		return super.createFluidAutoOutputButton()
	}

	// unknown usage
	override fun setNEITransferRect(widget: Widget, transferRectID: String): Widget {
		return super.setNEITransferRect(widget, transferRectID)
	}

	/**
	 * Called in [addProgressBar] to add additional part of Progress Bar.
	 */
	override fun addProgressBarSpecialTextures(builder: ModularWindow.Builder, uiProperties: BasicUIProperties) {
		super.addProgressBarSpecialTextures(builder, uiProperties)
	}

	// unknown usage
	override fun createErrorStatusArea(builder: ModularWindow.Builder, picture: IDrawable): DrawableWidget {
		return super.createErrorStatusArea(builder, picture)
	}

	// unknown usage, see getErrorTooltip
	override fun getErrorDescriptions(): MutableList<String> {
		return super.getErrorDescriptions()
	}

	// unknown usage, see getErrorTooltip
	override fun getErrorDescriptionsShift(): MutableList<String> {
		return super.getErrorDescriptionsShift()
	}

	// unknown usage
	override fun getErrorTooltip(): GT_TooltipDataCache.TooltipData {
		return super.getErrorTooltip()
	}

	// unknown usage
	override fun getTileEntityBaseType(): Byte {
		return super.tileEntityBaseType
	}

	/**
	 * @return the description array.
	 */
	override fun getDescription(): Array<String> {
		return super.description
	}

	/**
	 * @return the input voltage tier. [mTier] by default.
	 */
	override fun getInputTier(): Long {
		return super.inputTier
	}

	/**
	 * @return the output voltage tier. [mTier] by default.
	 */
	override fun getOutputTier(): Long {
		return super.outputTier
	}

	/**
	 * Called on pre-tick.
	 */
	override fun onPreTick(aBaseMetaTileEntity: IGregTechTileEntity, aTick: Long) {
		super.onPreTick(aBaseMetaTileEntity, aTick)
	}

	/**
	 * @return the fluid stack in this machine.
	 */
	override fun getFluid(): FluidStack {
		return super.fluid
	}

	/**
	 * Insert [aFluid] to [getFillableStack] of this machine.
	 *
	 * @return the amount of filled fluid.
	 */
	override fun fill(aFluid: FluidStack, doFill: Boolean): Int {
		return super.fill(aFluid, doFill)
	}

	/**
	 * Extract [maxDrain] of [getDrainableStack] from this machine.
	 *
	 * @return the drained fluid stack.
	 */
	override fun drain(maxDrain: Int, doDrain: Boolean): FluidStack {
		return super.drain(maxDrain, doDrain)
	}

	/**
	 * This method gives the ability to provide multiple type of fluids, for each type,
	 * you can create a different [FluidTankInfo].
	 *
	 * @return the array of tank info that are accessible from [side].
	 */
	override fun getTankInfo(side: ForgeDirection): Array<FluidTankInfo> {
		return super.getTankInfo(side)
	}

	/**
	 * @return the fluid amount of [getDrainableStack].
	 */
	override fun getFluidAmount(): Int {
		return super.fluidAmount
	}

	/**
	 * @return the input fluid stack.
	 */
	override fun getFillableStack(): FluidStack? {
		return super.fillableStack
	}

	/**
	 * Insert [aFluid] to [getFillableStack].
	 *
	 * @return the [getFillableStack].
	 */
	override fun setFillableStack(aFluid: FluidStack?): FluidStack? {
		return super.setFillableStack(aFluid)
	}

	/**
	 * Called when fluid is drained from containers.
	 * @see GT_MetaTileEntity_BasicTank.onPreTick
	 */
	override fun onEmptyingContainerWhenEmpty() {
		super.onEmptyingContainerWhenEmpty()
	}

	/**
	 * Return all the recipe maps that this machine supports by somehow like switching modes.
	 *
	 * @return the all available recipe maps of this machine.
	 */
	override fun getAvailableRecipeMaps(): Collection<RecipeMap<*>> {
		return super.getAvailableRecipeMaps()
	}

	// unknown usage
	override fun getRecipeCatalystPriority(): Int {
		return super.recipeCatalystPriority
	}
}
