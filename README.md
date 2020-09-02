# AndroidABTool
AndroidABTool是一个Android项目开发常用的工具类封装的开源库。
添加如下配置将AndroidABTool引入到你的项目中:
```groovy
dependencies{
    implementation 'com.callhh.abtool:abtool:1.0.3'
}
```

然后就可以调用诸多API来便捷开发
```kotlin
如EditText监听事件进行封装处理 简单使用
etInputAccount.onChange {
    if (it.isNotEmpty()) {
       //TODU Something
    }
}
```