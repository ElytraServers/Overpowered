package cn.taskeren.op.mc.util

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList

fun NBTTagList.forEachCompound(block: (nbt: NBTTagCompound) -> Unit) {
	for(i in 0 until tagCount()) {
		getCompoundTagAt(i).let(block)
	}
}

fun NBTTagList.getFloatAt(i: Int): Float = func_150308_e(i)
fun NBTTagList.getDoubleAt(i: Int): Double = func_150309_d(i)
fun NBTTagList.getIntArrayAt(i: Int): IntArray = func_150306_c(i)

fun <T> NBTTagList.map(transform: NBTTagList.(Int) -> T): List<T> {
	val list = ArrayList<T>()
	for(i in 0 until tagCount()) {
		list.add(this.transform(i))
	}
	return list
}
