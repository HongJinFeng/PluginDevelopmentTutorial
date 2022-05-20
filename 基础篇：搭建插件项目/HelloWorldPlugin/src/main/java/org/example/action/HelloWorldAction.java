package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class HelloWorldAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        /**
         * Notifications.Bus.notify 是IDE中发送通知提醒的一个API，我们熟悉它即可。
         * Notification中有几个核心字段，该构造方法中：
         *   第一个参数是通知消息的分组
         *   第二个参数是通知框的标题，第三个参数是通知框的内容。
         *   第四个参数是通知信息级别，主要的有Info，Waring，Error 3个级别。
         *
         * e.getProject() 是一个获取到当前IDE所打开项目在代码中的对象抽象表示的API。
         *  本处表示在我们当前IDE中所打开的项目进行提示，若我们同时打开多个IDE，其他IDE项目中不会收到改消息。
         */
        Notifications.Bus.notify(new Notification("HelloWorld", "我的第一个插件", "Hello, World, " + System.currentTimeMillis(), NotificationType.INFORMATION), e.getProject());
    }
}
