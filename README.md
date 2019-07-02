# ZKSDK

[![](https://jitpack.io/v/ZhuoKeTeam/ZKSDK.svg)](https://jitpack.io/#ZhuoKeTeam/ZKSDK)


## 使用说明：

优先在 Application 中添加：

```
ZKManager.instance.init(this) // 默认
ZKbase.init(this, true) // 设置 debug 的状态，默认不需要
```

然后在项目中的任何地方直接使用如下方法：

- `ZKBase.isDebug`  判断是否处于 Debug 的状态
- `ZKBase.context()`  获取上下文