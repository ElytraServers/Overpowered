# Overpowered

Yes, they are Overpowered as intended.

This mod is designed for GTNH the modpack.

### Dev Tips

Since I love Kotlin so much, so this project is mainly based on Kotlin.
And you may wonder, what's wrong with my IDEA, which the vanilla classes are not accessible?

So it is something wrong with RetroFuturaGradle (RFG). But somehow the K2 Mode fixes it!

Check out [here](https://github.com/GTNewHorizons/RetroFuturaGradle/issues/51#issuecomment-2067429880).

### FAQ

#### 中文翻译有点问题

因为我主要用英文玩GTNH，所以对中文语境下的各种东西都不熟悉，而且也不熟悉格雷的本地化API，还请您忍耐一下。

你可以来帮助我翻译各种内容，这些本地化的字符串都在代码中，`lang` 下的文件都是从代码中生成的，所以不要改动。
他的结构是：

```
// #tr 本地化键
// #en 英文内容
// #zh 中文内容
```

不能有换行。

你可以使用 `{颜色}` 的结构来给文本添加颜色，例如 `{GRAY}`。支持的颜色如下：

- `BLACK`
- `DARK_BLUE`
- `DARK_GREEN`
- `DARK_AQUA`
- `DARK_RED`
- `DARK_PURPLE`
- `GOLD`
- `GRAY`
- `DARK_GRAY`
- `BLUE`
- `GREEN`
- `AQUA`
- `RED`
- `LIGHT_PURPLE`
- `YELLOW`
- `WHITE`
- `OBFUSCATED`
- `BOLD`
- `STRIKETHROUGH`
- `UNDERLINE`
- `ITALIC`
- `RESET`

### Credits

- Texture of Programmed Upgrade is from [Neeve's AE2: Extended Life Additions (NAE2)](https://github.com/AE2-UEL/NAE2)
