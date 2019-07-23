# ZKSDK

[![](https://jitpack.io/v/ZhuoKeTeam/ZKSDK.svg)](https://jitpack.io/#ZhuoKeTeam/ZKSDK)

## 组件快速使用说明：

[组件快速说明](https://github.com/ZhuoKeTeam/ZKComponentDocument)

## 使用说明：

### 1. 优先在 Application 中添加：

```
ZKManager.instance.init(this) // 默认
ZKBase.init(this, true) // 设置 debug 的状态，默认不需要
```

### 2. 然后在项目中的任何地方直接使用如下方法：

- `ZKBase.isDebug`  判断是否处于 Debug 的状态
- `ZKBase.context()`  获取上下文

### 3. 工具类：

- ClipboardUtils：拷贝内容
- L（L.d(msg), L.e(msg)）：默认的 Log 工具
- ShareUtils: 分享工具，调起默认的系统分享（图片，文字）
- ShortCutsCreator：App 自定义 Shortcuts，类似 iOS 的 3D touch。通过在桌面长按 App 弹出 Shortcut 列表，点击某个 Shortcut 快速进入某项操作![shortcut](https://www.trinea.cn/wp-content/uploads/2016/11/android-shortcut.png)

### 4. TestBeautyData 测试数据：
- getNameForHLWHotWomen(): 好莱坞 Top10 女明星名字
- getTop10HotWomenData(): 好莱坞 Top10 女明星信息，包含名字，图片，简介

### 5. getNameFor2019TVName 测试数据：

- getNameFor2019TVName(): 2019年的 20 个电视剧名字
- getNameForNovel(): Top 10 的小说和作家名字
- getSceneryPic(): 美丽的 10 张风景图片
- getBigBeautyPic(): 10 张清纯不做作的美女图片

## ZKRecyclerView

### 1. 直接使用范例：

```
zkRecyclerView.adapter = ZKTextAdapter(ZKTextData.getData())
```

SDK 中默认展示的一个范例，方便测试和使用。

### 2. 设置布局：
```
val zkRecyclerView = contentView.findViewById<ZKRecyclerView>(R.id.zk_rv) //查找到 ZKRecyclerView，kotlin 可以直接用 zk_rv 当做 ZKRecyclerView

zkRecyclerView.setLayoutManager(ZKRecyclerView.ZKRV_GRID_VIEW_V) // 默认是普通列表形式（可以不写），如果指定相关参数，会变成 横向/纵向 的列表，网格，瀑布流。
zkRecyclerView.adapter = ZKTextAdapter(ZKTextData.getData()) // SDK 中默认展示的一个范例，方便测试和使用
```

## Base 相关类

### 1. ZKBaseApplication

继承自 ZKBaseApplication，默认包含 MultiDex， 支持 判断 debug 状态 和 判断是否为主进程。

### 2. ZKBaseActivity 和 ZKBaseFragment 

继承自 ZKBaseActivity 和 ZKBaseFragment，规范代码格式。

### 3. ZKBaseLazyFragment

当 Fragment 可见的时候做一些事情

## SP 文件操作

默认使用：
```
SPUtils.getInstance("spName").put("key", "value")
```
也可以 继承自 ZKSharedPreferences()：
```
class ZKSDKSP : ZKSharedPreferences() {
    override fun sharedPreferencesFileName(): String {
        return SP_NAME
    }
}

```

//使用方式
ZKSDKSP().put("key", "value")
L.d("value= ${ZKSDKSP().get("key", "默认值")}")

## utilcode

- 文档地址：https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-CN.md
- 源码：https://github.com/Blankj/AndroidUtilCode
- 演示 apk 地址：http://zkteam.cc/android/apk/utils.apk

## rvh

- 文档地址：https://www.jianshu.com/p/b343fcff51b0
- 源码地址：https://github.com/CymChad/BaseRecyclerViewAdapterHelper
- 演示 apk 地址：https://fir.im/s91g

