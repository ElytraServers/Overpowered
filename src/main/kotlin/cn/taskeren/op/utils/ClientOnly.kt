package cn.taskeren.op.utils

/**
 * This annotation is only used to mark an element that should only be available or called in client side.
 *
 * It is different from [cpw.mods.fml.relauncher.SideOnly], which it will ignore that element in compilation in wrong target,
 * where you cannot even get the reflection object to it.
 */
annotation class ClientOnly(val value: String = "")
