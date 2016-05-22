package cz.chatview.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cz.chatview.bean.Message;
import cz.chatview.bean.Content;
import cz.chatview.builder.ViewBuilderManager;
import cz.chatview.builder.ViewHolder;

/**
 * Created by haozhou on 2016/5/20.
 */
public class ChatView extends LinearLayout {
    private Context mContext;

    private ListView mListView;

    private ViewBuilderManager mBuilderManager;

    private ChatListAdapter mAdapter;

    private List<Message> mMessages = new ArrayList<>();

    public ChatView (Context context) {
        this (context, null);
    }

    public ChatView (Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;

        mBuilderManager = new ViewBuilderManager();

        this.setOrientation(VERTICAL);
        initChatListView();
    }

    private void initChatListView () {
        mListView = new ListView(mContext);
        mListView.setFastScrollEnabled(false);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setCacheColorHint(0);
        mListView.setSelector(new ColorDrawable(0));
        mListView.setDivider(null);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mAdapter = new ChatListAdapter();
        mListView.setAdapter(mAdapter);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        this.addView(mListView, params);
    }

    public void add(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    public void registerMessageViewBuilder(Class<? extends Content> contentClass,
                                           ViewBuilderManager.ViewBuilder builder) {
        mBuilderManager.registerMessageViewBuilder(contentClass, builder);
        mListView.setAdapter(mAdapter);
    }

    public boolean hasReachedBottom() {
        int lastVisible = mListView.getLastVisiblePosition();
        Adapter adapter = mListView.getAdapter();
        return adapter == null ? true : lastVisible == adapter.getCount() - 1;
    }

    public void moveToBottem() {
        Adapter adapter = mListView.getAdapter();
        if (adapter != null) {
            mListView.smoothScrollToPosition(adapter.getCount() - 1);
        }
    }

    class ChatListAdapter extends BaseAdapter {

        @Override
        public int getViewTypeCount() {
            final int viewTypeCount = mBuilderManager.getViewTypeCount();
            return viewTypeCount == 0 ? 1 : viewTypeCount;
        }

        @Override
        public int getItemViewType(int position) {
            Message msg = getItem(position);
            if(msg != null) {
                Content content = msg.getContent();
                if (content != null) {
                    return mBuilderManager.getItemViewType(content.getClass());
                }
            }
            return 0;
        }

        @Override
        public int getCount() {
            return mMessages.size();
        }

        @Override
        public Message getItem(int position) {
            return mMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message message = getItem(position);

            ViewBuilderManager.ViewBuilder builder;
            if (message != null) {
                Content content = message.getContent();
                if (content != null) {
                    builder = mBuilderManager.getBuilder(content.getClass());
                } else {
                    builder = mBuilderManager.getBuilder(Content.class);
                }
            } else {
                builder = mBuilderManager.getBuilder(Content.class);
            }

            ViewHolder holder;
            if (convertView == null) {
                holder = builder.onCreateViewHolder(parent);
                convertView = holder.getView();
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            builder.onBindViewHolder(holder, message);

            return convertView;
        }
    }
}
