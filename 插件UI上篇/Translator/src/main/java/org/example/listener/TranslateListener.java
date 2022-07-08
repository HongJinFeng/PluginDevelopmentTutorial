package org.example.listener;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

public interface TranslateListener {
    Topic<TranslateListener> TRANSLATE_TOPIC = Topic.create("translate", TranslateListener.class);

    default void beforeTranslated(Project project) {
    }

    default void afterTranslated(Project project) {
    }
}
