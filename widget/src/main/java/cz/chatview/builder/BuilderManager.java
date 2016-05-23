package cz.chatview.builder;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import cz.chatview.message.bean.Message;
import cz.chatview.message.bean.Content;
import cz.chatview.uitls.ViewHolder;
import cz.chatview.uitls.ViewHolder.EmptyViewHolder;

/**
 * Created by haozhou on 2016/5/20.
 */
public class BuilderManager {
    private final Map<Class, ViewBuilder> mBuilders = new HashMap<>();
    private final Map<Class, Integer> mTypesForView = new HashMap<>();

    public BuilderManager() {
        // 用于处理空数据的默认builder
        ViewBuilder<EmptyViewHolder> emptyViewBuilder = new ViewBuilder<EmptyViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new EmptyViewHolder(new View(parent.getContext()));
            }

            @Override
            public void onBindViewHolder(EmptyViewHolder holder, Message message) {

            }
        };
        mBuilders.put(Content.class, emptyViewBuilder);
        mTypesForView.put(Content.class, 0);
    }

    public void registerMessageViewBuilder(Class<? extends Content> contentClass, ViewBuilder contentBuilder) {
        if (contentClass != null && contentBuilder != null) {
            mBuilders.put(contentClass, contentBuilder);
            int type = mTypesForView.size();
            mTypesForView.put(contentClass, type);
        }
    }

    public int getViewTypeCount() {
        int size = mBuilders.size();
        return size == 0 ? 1 : size;
    }

    public int getItemViewType(Class<? extends Content> contentClass) {
        Integer type = mTypesForView.get(contentClass);
        return type == null ? 0 : type;
    }

    public ViewBuilder getBuilder(Class<? extends Content> contentClass) {
        return mBuilders.get(contentClass);
    }

    /**
     * 用于构建聊天消息列表的MessageView，应该为每个Content类型创建一个对应的ViewBuilder。
     * @param <T> MessageView的ViewHolder
     */
    public static abstract class ViewBuilder<T extends ViewHolder> {
        /**
         * 创建一个Content对应的MessageView，并将这个View绑定到对应的ViewHolder
         * @param parent
         * @return
         */
        public abstract ViewHolder onCreateViewHolder(ViewGroup parent);

        /**
         * 使用Message更新MessageView中的数据
         * @param holder
         * @param message
         */
        public abstract void onBindViewHolder(T holder, Message message);
    }
}
