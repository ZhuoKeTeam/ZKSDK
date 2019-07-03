# ZKSDK

[![](https://jitpack.io/v/ZhuoKeTeam/ZKSDK.svg)](https://jitpack.io/#ZhuoKeTeam/ZKSDK)


## 使用说明：

优先在 Application 中添加：

```
ZKManager.instance.init(this) // 默认
ZKBase.init(this, true) // 设置 debug 的状态，默认不需要
```

然后在项目中的任何地方直接使用如下方法：

- `ZKBase.isDebug`  判断是否处于 Debug 的状态
- `ZKBase.context()`  获取上下文


## utilcode

- 文档地址：https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-CN.md
- 源码：https://github.com/Blankj/AndroidUtilCode
- 演示 apk 地址：http://zkteam.cc/android/apk/utils.apk

## rvh

- 文档地址：https://www.jianshu.com/p/b343fcff51b0
- 源码地址：https://github.com/CymChad/BaseRecyclerViewAdapterHelper
- 演示 apk 地址：https://fir.im/s91g
