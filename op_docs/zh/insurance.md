# 保险

GT机器爆炸时会记录 “保险信息” 到机器绑定的玩家（通常是放置机器的玩家）。

玩家可以使用 `/insurance refund <mId>` 来取得保险的赔付，当然需要一笔资源作为保险金。`mId` 是机器的 MetaTileEntity ID，你可以查看机器物品后的 Damage 来获得。

玩家可以使用 `/insurance list` 来查看 “保险信息”。

机器爆炸时会通知绑定的玩家。
