# 关于 Overpowered

Overpowered 是 Taskeren 开发的一款为 GTNH 模组包设计的模组，集合了他以前的多种设计，旨在优化 GTNH 的产线流程和游戏体验。

你也可以去看看 Overpowered 的姊妹作品 [gtnh-no-nerf](https://github.com/Taskeren/gtnh-no-nerf)。

## 我有提案

我们欢迎新的想法，你可以[与我取得联系](#与我联系)，或者在项目的 Issue 中发布你的看法。

## 遇到问题

程序难免会出现问题，当你遇到了问题，[与我联系](#与我联系)，或者在项目的 Issue 中填入问题的具体内容。

信息需要至少包含 **GTNH 版本**，**Overpowered 版本**，以及其他的不在原版 GTNH 中安装的模组以及版本。

此外\
遇到加载卡住，你需要至少附上游戏的完整日志（`logs/latest.log`）；\
遇到游戏崩溃，你需要至少附上游戏的崩溃日志（`crash-reports/crash-xxx-client.txt`）。

如果提交的信息不够，将会需要你补充。如果长时间没有补充足够的信息，将会关闭你的 Issue。

## 与我联系

你可以通过以下方式与我取得联系，来帮助改善 Overpowered：

- QQ群 639927833
- B站 [Taskeren-3](https://space.bilibili.com/27656565)
- Discord `taskeren`

## 贡献代码

我们也欢迎直接贡献代码的大佬！

但是，在提交 PR 之前，建议你先在 Issue 中与我们讨论你的想法，避免浪费你宝贵的时间。

同时你的代码需要遵守我们的代码规范：

1. 以 IDEA 默认的 Java 和 Kotlin 代码样式为基础。
1. 使用 Tab 缩进。
1. 在 `if`，`try`，`for` 等关键字和后面的括号 `(` 之间不保留空格。
1. 【Kotlin】使用[尾后逗号（trailing commas）](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Trailing_commas)。
1. 【Java，Mixin】命名 Mixin 类遵循 `功能_被注入的类名_Mixin`，功能部分不能包含下划线（`_`），例如 `Insurance_GregTech_API_Mixin`，`AECompat_DualityInterface_Mixin`。
1. 【GregTech】格雷机器或相似类型的类名以 `OP_` 开头，这部分可以无视 IDEA 的命名规范。
1. 慎用警告抑制，你可以调整 IDEA 里警告等级来避免这些烦人的警告，例如未使用变量（unused）。