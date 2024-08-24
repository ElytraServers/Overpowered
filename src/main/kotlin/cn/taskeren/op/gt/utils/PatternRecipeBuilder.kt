package cn.taskeren.op.gt.utils

import gregtech.api.enums.GT_Values
import gregtech.api.enums.ItemList
import gregtech.api.enums.Materials
import gregtech.api.enums.OrePrefixes
import gregtech.api.enums.Tier
import gregtech.api.util.ExternalMaterials
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class PatternRecipeBuilder {

	enum class X {
		PUMP,
		WIRE,
		WIRE4,
		HULL,
		PIPE,
		GLASS,
		PLATE,
		MOTOR,
		ROTOR,
		SENSOR,
		PISTON,
		CIRCUIT,
		EMITTER,
		CONVEYOR,
		ROBOT_ARM,
		COIL_HEATING,
		COIL_ELECTRIC,
		STICK_MAGNETIC,
		STICK_DISTILLATION,
		BETTER_CIRCUIT,
		FIELD_GENERATOR,
		COIL_HEATING_DOUBLE,
		STICK_ELECTROMAGNETIC,
	}

	companion object {
		fun get(x: X, tier: Int): Any {
			return when(x) {
				X.CIRCUIT -> Tier.ELECTRIC[tier].mManagingObject
				X.BETTER_CIRCUIT -> Tier.ELECTRIC[tier].mBetterManagingObject
				X.HULL -> Tier.ELECTRIC[tier].mHullObject
				X.WIRE -> Tier.ELECTRIC[tier].mConductingObject
				X.WIRE4 -> Tier.ELECTRIC[tier].mLargerConductingObject
				X.STICK_DISTILLATION -> OrePrefixes.stick.get(Materials.Blaze)
				X.GLASS -> when(tier) {
					0, 1, 2, 3 -> ItemStack(Blocks.glass, 1, GT_Values.W.toInt())
					4, 5, 6, 7, 8 -> "blockGlass" + GT_Values.VN[tier]
					else -> "blockGlass" + GT_Values.VN[8]
				}

				X.PLATE -> when(tier) {
					0, 1 -> OrePrefixes.plate.get(Materials.Steel)
					2 -> OrePrefixes.plate.get(Materials.Aluminium)
					3 -> OrePrefixes.plate.get(Materials.StainlessSteel)
					4 -> OrePrefixes.plate.get(Materials.Titanium)
					5 -> OrePrefixes.plate.get(Materials.TungstenSteel)
					6 -> OrePrefixes.plate.get(Materials.HSSG)
					7 -> OrePrefixes.plate.get(Materials.HSSE)
					else -> OrePrefixes.plate.get(Materials.Neutronium)
				}

				X.PIPE -> when(tier) {
					0, 1 -> OrePrefixes.pipeMedium.get(Materials.Bronze)
					2 -> OrePrefixes.pipeMedium.get(Materials.Steel)
					3 -> OrePrefixes.pipeMedium.get(Materials.StainlessSteel)
					4 -> OrePrefixes.pipeMedium.get(Materials.Titanium)
					5 -> OrePrefixes.pipeMedium.get(Materials.TungstenSteel)
					6 -> OrePrefixes.pipeSmall.get(Materials.Ultimate)
					7 -> OrePrefixes.pipeMedium.get(Materials.Ultimate)
					8 -> OrePrefixes.pipeLarge.get(Materials.Ultimate)
					else -> OrePrefixes.pipeHuge.get(Materials.Ultimate)
				}

				X.COIL_HEATING -> when(tier) {
					0, 1 -> OrePrefixes.wireGt02.get(Materials.AnyCopper)
					2 -> OrePrefixes.wireGt02.get(Materials.Cupronickel)
					3 -> OrePrefixes.wireGt02.get(Materials.Kanthal)
					4 -> OrePrefixes.wireGt02.get(Materials.Nichrome)
					5 -> OrePrefixes.wireGt02.get(Materials.TPV)
					6 -> OrePrefixes.wireGt02.get(Materials.HSSG)
					7 -> OrePrefixes.wireGt02.get(Materials.Naquadah)
					8 -> OrePrefixes.wireGt02.get(Materials.NaquadahAlloy)
					9 -> OrePrefixes.wireGt04.get(Materials.NaquadahAlloy)
					else -> OrePrefixes.wireGt08.get(Materials.NaquadahAlloy)
				}

				X.COIL_HEATING_DOUBLE -> when(tier) {
					0, 1 -> OrePrefixes.wireGt04.get(Materials.AnyCopper)
					2 -> OrePrefixes.wireGt04.get(Materials.Cupronickel)
					3 -> OrePrefixes.wireGt04.get(Materials.Kanthal)
					4 -> OrePrefixes.wireGt04.get(Materials.Nichrome)
					5 -> OrePrefixes.wireGt04.get(Materials.TPV)
					6 -> OrePrefixes.wireGt04.get(Materials.HSSG)
					7 -> OrePrefixes.wireGt04.get(Materials.Naquadah)
					8 -> OrePrefixes.wireGt04.get(Materials.NaquadahAlloy)
					9 -> OrePrefixes.wireGt08.get(Materials.NaquadahAlloy)
					else -> OrePrefixes.wireGt16.get(Materials.NaquadahAlloy)
				}

				X.STICK_MAGNETIC -> when(tier) {
					0, 1 -> OrePrefixes.stick.get(Materials.IronMagnetic)
					2, 3 -> OrePrefixes.stick.get(Materials.SteelMagnetic)
					4, 5 -> OrePrefixes.stick.get(Materials.NeodymiumMagnetic)
					6, 7, 8, 9 -> OrePrefixes.stick.get(Materials.SamariumMagnetic)
					else -> OrePrefixes.stick.get(Materials.TengamAttuned)
				}

				X.STICK_ELECTROMAGNETIC -> when(tier) {
					0, 1 -> OrePrefixes.stick.get(Materials.AnyIron)
					2, 3 -> OrePrefixes.stick.get(Materials.Steel)
					4 -> OrePrefixes.stick.get(Materials.Neodymium)
					else -> OrePrefixes.stick.get(Materials.VanadiumGallium)
				}

				X.COIL_ELECTRIC -> when(tier) {
					0 -> OrePrefixes.wireGt01.get(Materials.Lead)
					1 -> OrePrefixes.wireGt02.get(Materials.Tin)
					2 -> OrePrefixes.wireGt02.get(Materials.AnyCopper)
					3 -> OrePrefixes.wireGt04.get(Materials.AnyCopper)
					4 -> OrePrefixes.wireGt08.get(Materials.AnnealedCopper)
					5 -> OrePrefixes.wireGt16.get(Materials.AnnealedCopper)
					6 -> OrePrefixes.wireGt04.get(Materials.YttriumBariumCuprate)
					7 -> OrePrefixes.wireGt08.get(Materials.Iridium)
					else -> OrePrefixes.wireGt16.get(Materials.Osmium)
				}

				X.ROBOT_ARM -> when(tier) {
					0, 1 -> ItemList.Robot_Arm_LV
					2 -> ItemList.Robot_Arm_MV
					3 -> ItemList.Robot_Arm_HV
					4 -> ItemList.Robot_Arm_EV
					5 -> ItemList.Robot_Arm_IV
					6 -> ItemList.Robot_Arm_LuV
					7 -> ItemList.Robot_Arm_ZPM
					8 -> ItemList.Robot_Arm_UV
					9 -> ItemList.Robot_Arm_UHV
					10 -> ItemList.Robot_Arm_UEV
					11 -> ItemList.Robot_Arm_UIV
					12 -> ItemList.Robot_Arm_UMV
					13 -> ItemList.Robot_Arm_UXV
					else -> ItemList.Robot_Arm_MAX
				}

				X.PUMP -> when(tier) {
					0, 1 -> ItemList.Electric_Pump_LV
					2 -> ItemList.Electric_Pump_MV
					3 -> ItemList.Electric_Pump_HV
					4 -> ItemList.Electric_Pump_EV
					5 -> ItemList.Electric_Pump_IV
					6 -> ItemList.Electric_Pump_LuV
					7 -> ItemList.Electric_Pump_ZPM
					8 -> ItemList.Electric_Pump_UV
					9 -> ItemList.Electric_Pump_UHV
					10 -> ItemList.Electric_Pump_UEV
					11 -> ItemList.Electric_Pump_UIV
					12 -> ItemList.Electric_Pump_UMV
					13 -> ItemList.Electric_Pump_UXV
					else -> ItemList.Electric_Pump_MAX
				}

				X.MOTOR -> when(tier) {
					0, 1 -> ItemList.Electric_Motor_LV
					2 -> ItemList.Electric_Motor_MV
					3 -> ItemList.Electric_Motor_HV
					4 -> ItemList.Electric_Motor_EV
					5 -> ItemList.Electric_Motor_IV
					6 -> ItemList.Electric_Motor_LuV
					7 -> ItemList.Electric_Motor_ZPM
					8 -> ItemList.Electric_Motor_UV
					9 -> ItemList.Electric_Motor_UHV
					10 -> ItemList.Electric_Motor_UEV
					11 -> ItemList.Electric_Motor_UIV
					12 -> ItemList.Electric_Motor_UMV
					13 -> ItemList.Electric_Motor_UXV
					else -> ItemList.Electric_Motor_MAX
				}

				X.PISTON -> when(tier) {
					0, 1 -> ItemList.Electric_Piston_LV
					2 -> ItemList.Electric_Piston_MV
					3 -> ItemList.Electric_Piston_HV
					4 -> ItemList.Electric_Piston_EV
					5 -> ItemList.Electric_Piston_IV
					6 -> ItemList.Electric_Piston_LuV
					7 -> ItemList.Electric_Piston_ZPM
					8 -> ItemList.Electric_Piston_UV
					9 -> ItemList.Electric_Piston_UHV
					10 -> ItemList.Electric_Piston_UEV
					11 -> ItemList.Electric_Piston_UIV
					12 -> ItemList.Electric_Piston_UMV
					13 -> ItemList.Electric_Piston_UXV
					else -> ItemList.Electric_Piston_MAX
				}

				X.CONVEYOR -> when(tier) {
					0, 1 -> ItemList.Conveyor_Module_LV
					2 -> ItemList.Conveyor_Module_MV
					3 -> ItemList.Conveyor_Module_HV
					4 -> ItemList.Conveyor_Module_EV
					5 -> ItemList.Conveyor_Module_IV
					6 -> ItemList.Conveyor_Module_LuV
					7 -> ItemList.Conveyor_Module_ZPM
					8 -> ItemList.Conveyor_Module_UV
					9 -> ItemList.Conveyor_Module_UHV
					10 -> ItemList.Conveyor_Module_UEV
					11 -> ItemList.Conveyor_Module_UIV
					12 -> ItemList.Conveyor_Module_UMV
					13 -> ItemList.Conveyor_Module_UXV
					else -> ItemList.Conveyor_Module_MAX
				}

				X.EMITTER -> when(tier) {
					0, 1 -> ItemList.Emitter_LV
					2 -> ItemList.Emitter_MV
					3 -> ItemList.Emitter_HV
					4 -> ItemList.Emitter_EV
					5 -> ItemList.Emitter_IV
					6 -> ItemList.Emitter_LuV
					7 -> ItemList.Emitter_ZPM
					8 -> ItemList.Emitter_UV
					9 -> ItemList.Emitter_UHV
					10 -> ItemList.Emitter_UEV
					11 -> ItemList.Emitter_UIV
					12 -> ItemList.Emitter_UMV
					13 -> ItemList.Emitter_UXV
					else -> ItemList.Emitter_MAX
				}

				X.SENSOR -> when(tier) {
					0, 1 -> ItemList.Sensor_LV
					2 -> ItemList.Sensor_MV
					3 -> ItemList.Sensor_HV
					4 -> ItemList.Sensor_EV
					5 -> ItemList.Sensor_IV
					6 -> ItemList.Sensor_LuV
					7 -> ItemList.Sensor_ZPM
					8 -> ItemList.Sensor_UV
					9 -> ItemList.Sensor_UHV
					10 -> ItemList.Sensor_UEV
					11 -> ItemList.Sensor_UIV
					12 -> ItemList.Sensor_UMV
					13 -> ItemList.Sensor_UXV
					else -> ItemList.Sensor_MAX
				}

				X.FIELD_GENERATOR -> when(tier) {
					0, 1 -> ItemList.Field_Generator_LV
					2 -> ItemList.Field_Generator_MV
					3 -> ItemList.Field_Generator_HV
					4 -> ItemList.Field_Generator_EV
					5 -> ItemList.Field_Generator_IV
					6 -> ItemList.Field_Generator_LuV
					7 -> ItemList.Field_Generator_ZPM
					8 -> ItemList.Field_Generator_UV
					9 -> ItemList.Field_Generator_UHV
					10 -> ItemList.Field_Generator_UEV
					11 -> ItemList.Field_Generator_UIV
					12 -> ItemList.Field_Generator_UMV
					13 -> ItemList.Field_Generator_UXV
					else -> ItemList.Field_Generator_MAX
				}

				X.ROTOR -> when(tier) {
					0, 1 -> OrePrefixes.rotor.get(Materials.Tin)
					2 -> OrePrefixes.rotor.get(Materials.Bronze)
					3 -> OrePrefixes.rotor.get(Materials.Steel)
					4 -> OrePrefixes.rotor.get(Materials.StainlessSteel)
					5 -> OrePrefixes.rotor.get(Materials.TungstenSteel)
					6 -> OrePrefixes.rotor.get(ExternalMaterials.getRhodiumPlatedPalladium())
					7 -> OrePrefixes.rotor.get(Materials.Iridium)
					else -> OrePrefixes.rotor.get(Materials.Osmium)
				}
			}
		}

		private fun format(tier: Int, recipeObject: Array<Any>): Array<Any> {
			val copy = recipeObject.copyOf()
			recipeObject.forEachIndexed { index, obj ->
				if(obj is X) {
					copy[index] = get(obj, tier)
				}
			}
			return copy
		}

		fun Array<Any>.withTier(tier: Int): Array<Any> {
			return format(tier, this)
		}
	}

}
