package cz.chatview.builder;

import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import cz.chatview.bean.ChatMessage;
import cz.chatview.bean.Content;

/**
 * Created by haozhou on 2016/5/20.
 */
public class ChatViewBuilder {
    private final Map<Class, ViewBuilder> mBuilders = new HashMap<>();

    public void registerBuilder(Class<? extends Content> contentClass, ViewBuilder contentBuilder) {
        if (contentClass != null && contentBuilder != null) {
            mBuilders.put(contentClass, contentBuilder);
        }
    }

    public int getViewTypeCount() {
        int size = mBuilders.size();
        return size == 0 ? 1 : size;
    }

    public  abstract class ViewBuilder {
        public abstract ViewHolder onCreateViewHolder(ViewGroup parent);
        public abstract void onBindViewHolder(ViewHolder holder, ChatMessage message);
    }
}
