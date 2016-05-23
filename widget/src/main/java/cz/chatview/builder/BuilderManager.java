package cz.chatview.builder;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import cz.chatview.message.bean.Content;
import cz.chatview.uitls.ViewHolder;
import cz.chatview.uitls.ViewHolder.EmptyViewHolder;

/**
 * Created by haozhou on 2016/5/20.
 */
public class BuilderManager {
    private final Map<Class, Pair<ViewBuilder, ViewBuilder>> mBuilders = new HashMap<>();
    private final Map<Class, Integer> mTypesForBuilder = new HashMap<>();

    private int viewTypeCount = 0;

    public BuilderManager() {
        // 用于处理空数据的默认builder
        ViewBuilder emptyViewBuilder = new ViewBuilder() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new EmptyViewHolder(new View(parent.getContext()));
            }
        };
        registerMessageViewBuilder(Content.class, emptyViewBuilder);
    }

    public void registerMessageViewBuilder(Class<? extends Content> contentClass, ViewBuilder contentBuilder) {
        registerMessageViewBuilder(contentClass, contentBuilder, null);
    }

    public void registerMessageViewBuilder(Class<? extends Content> contentClass, ViewBuilder left, ViewBuilder right) {
        if (contentClass != null && (left != null || right != null)) {
            // 若只有一个builder，则存在Pair的first中
            if (left == null) {
                left = right;
                right = null;
            }
            Pair<ViewBuilder, ViewBuilder> pair = new Pair<>(left, right);
            mBuilders.put(contentClass, pair);
            mTypesForBuilder.put(contentClass, viewTypeCount);
            viewTypeCount += left != null && right != null ? 2 : 1;
        }
    }

    public int getViewTypeCount() {
        return viewTypeCount > 0 ? viewTypeCount : 1;
    }

    public int getItemViewType(Class<? extends Content> contentClass) {
        return getItemViewType(contentClass, false);
    }

    public int getItemViewType(Class<? extends Content> contentClass, boolean fromMe) {
        Integer i = mTypesForBuilder.get(contentClass);
        int typeForClass = i == null ? 0 : i;
        Pair pair = mBuilders.get(contentClass);
        if (pair != null) {
            // 验证content类型是否同时对应两个builder
            if (pair.first != null && pair.second != null) {
                // leftType = type, rightType = type + 1
                return !fromMe ? typeForClass : typeForClass + 1;
            } else {
                return typeForClass;
            }
        }
        return 0;
    }

    public ViewBuilder getBuilder(Class<? extends Content> contentClass) {
        return getBuilder(contentClass, false);
    }

    public ViewBuilder getBuilder(Class<? extends Content> contentClass, boolean fromMe) {
        if (contentClass != null) {
            Pair<ViewBuilder, ViewBuilder> pair = mBuilders.get(contentClass);
            if (pair != null) {
                if (pair.first != null && pair.second != null) {
                    return !fromMe ? pair.first : pair.second;
                } else {
                    return pair.first;
                }
            }
        }
        return null;
    }

    /**
     * 用于构建聊天消息列表的MessageView，应该为每个Content类型创建一个对应的ViewBuilder。
     */
    public static abstract class ViewBuilder {
        /**
         * 创建一个Content对应的MessageView，并将这个View绑定到对应的ViewHolder
         *
         * @param parent
         * @return
         */
        public abstract ViewHolder onCreateViewHolder(ViewGroup parent);
    }
}
