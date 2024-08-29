# 电压概率加成

你可以使用 `Overpowered 侦错物品` 来查看机器是否支持电压概率加成及其数值。

依照 *配方的电压等级* 和 *机器的等级* 差，来获得额外的概率加成。

多方块机器的电压等级为结构内最低等级能源仓的电压等级。

额外概率加成公式为：

$$ C = (T - T_0) * C_0 $$

- T：机器电压等级
- T<sub>0</sub>：配方电压等级
- C：额外概率加成
- C<sub>0</sub>：每级概率加成

这项功能目前应用于 GT++ 的大型机器上（继承 `GregtechMeta_MultiBlockBase` 的机器），默认每级概率加成为 $10\%$。
例如大筛选机有 *LV 等级的筛选沙砾配方*，在 UHV 电压等级的大筛选机中能够获得 $(9 - 1) * 15\% = 120\%$ 的额外概率加成。

::: details 仅 v1.0.3

这个机制取决于 *电压等级*，*基础电压等级（最低概率加成等级，不包含）*，*每级概率加成*。

多方块的电压等级为结构内最低等级能源仓的电压等级。

默认公式为：

- C：电压概率加成
- T：机器电压等级
- T<sub>0</sub>：基础电压等级
- C<sub>0</sub>：每级概率加成

$$ C = (T - T_{0}) * C_{0} $$

这项功能目前应用于 GT++ 的大型机器上（继承 `GregtechMeta_MultiBlockBase` 的机器），基础电压等级为 HV（3），每级概率加成为 10%。
例如 UHV（9） 大筛选机，可以获得 90% （$(9 - 3) * 15\% = 90\%$） 的额外概率加成。

:::

## 开发

### 1. 为机器添加概率加成支持

#### 1.1. 注册 `ChanceBonusProvider`

你可以将 `ChanceBonusProvider` 注册到 [`ChanceBonusManager`](https://github.com/ElytraServers/Overpowered/blob/master/src/main/kotlin/cn/taskeren/op/gt/recipe_chance_bonus/ChanceBonusManager.kt)，来为使用 `GT_ParallelHelper` 的机器启用概率加成。

#### 1.2. 实现 `IVoltageChanceBonus`

或者你可以为你的机器实现 [`IVoltageChanceBonus`](https://github.com/ElytraServers/Overpowered/blob/master/src/main/java/cn/taskeren/op/api/IVoltageChanceBonus.java) 接口。

如果你的机器使用 `GT_ParallelHelper`，Overpowered 的 [Mixin](https://github.com/ElytraServers/Overpowered/blob/master/src/main/java/cn/taskeren/op/mixin/late/VoltageChanceBonus_GT_ParallelHelper_Mixin.java) 会自动读取概率加成，并注入到配方计算中。

## 获取可能支持的机器（实验性）

使用指令 `/op-darkarts listVoltageBonus` 获取可能支持的机器。
