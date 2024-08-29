# 电压概率加成

你可以使用 `Overpowered 侦错物品` 来查看机器是否支持电压概率加成及其数值。

这个机制取决于 *电压等级*，*基础电压等级（最低概率加成等级，不包含）*，*每级概率加成*。

多方块的电压等级为结构内最低等级能源仓的电压等级。

默认公式为：

- C：电压概率加成
- T：机器电压等级
- T<sub>0</sub>：基础电压等级
- C<sub>0</sub>：每级概率加成

$$ C = (T - T_{0}) * C_{0} $$

这项功能目前引用于 GT++ 的大型机器上（继承 `GregtechMeta_MultiBlockBase` 的机器），基础电压等级为 HV（3），每级概率加成为 10%。
例如 UHV（12） 大筛选机，可以获得 90% （$(12 - 3) * 10\% = 90\%$） 的额外概率加成。

## 开发

你可以为你的机器实现 [`IVoltageChanceBonus`](https://github.com/ElytraServers/Overpowered/blob/master/src/main/java/cn/taskeren/op/api/IVoltageChanceBonus.java) 接口。

如果你的机器使用 `GT_ParallelHelper`，Overpowered 的 [Mixin](https://github.com/ElytraServers/Overpowered/blob/master/src/main/java/cn/taskeren/op/mixin/late/VoltageChanceBonus_GT_ParallelHelper_Mixin.java) 会自动读取概率加成，并注入到配方计算中。