# ChatView
屏蔽了Adapter实现细节，Data类型绑定对应View，可以方便的设计聊天列表样式。
```
public void registerMessageViewBuilder(Class<? extends Content> contentClass,
                                           BuilderManager.ViewBuilder builder)

public void registerMessageViewBuilder(Class<? extends Content> contentClass,
                                           BuilderManager.ViewBuilder leftBuilder,
                                           BuilderManager.ViewBuilder rightBuilder)
```
使用ChatView.registerMessageViewBuilder方法注册聊天条目的构造器。  
其中参数contentClass表示该条记录的实际内容的类型，继承至Content，比如可以自定义TextContent、ImageContent。  

<img src="https://github.com/zh8637688/ChatView/blob/master/image/screencapture.png" width = "300" height = "480" alt="screencapture" align=center />
