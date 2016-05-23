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

import java.util.List;

import cz.chatview.builder.BuilderManager;
import cz.chatview.message.bean.Content;
import cz.chatview.message.bean.Message;
import cz.chatview.uitls.FIFOList;
import cz.chatview.uitls.ViewHolder;

/**
 * Created by haozhou on 2016/5/20.
 */
public class ChatView extends LinearLayout {
    private final static int DEFAULT_MAX_COUNT = 200;

    private Context mContext;

    private ListView mListView;

    private BuilderManager mBuilderManager;

    private ChatListAdapter mAdapter;

    private FIFOList<Message> mMessages = new FIFOList<>();

    private boolean mAutoScroll = true;
    private boolean mForceToBottom = true;

    public ChatView(Context context) {
        this(context, null);
    }

    public ChatView(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;

        mBuilderManager = new BuilderManager();
        mMessages.setLimit(DEFAULT_MAX_COUNT);

        this.setOrientation(VERTICAL);
        initChatListView();
    }

    private void initChatListView() {
        mListView = new ListView(mContext);
        mListView.setFastScrollEnabled(false);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setCacheColorHint(0);
        mListView.setSelector(new ColorDrawable(0));
        mListView.setDivider(null);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (hasReachedBottom()) {
                        mForceToBottom = mAutoScroll;
                        mMessages.setLimit(DEFAULT_MAX_COUNT);
                    } else {
                        mForceToBottom = false;
                        // 如果没有停留在底部，则不设限，防止之前的消息被冲掉
                        mMessages.setLimit(0);
                    }
                    // 防止达到最大容量后没有scroll动画
                    if (mMessages.removeIfThreeQuarters()) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
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
        if (mForceToBottom) {
            scrollToBottom();
        }
    }

    public void add(List<Message> messages) {
        mMessages.add(messages);
        mAdapter.notifyDataSetChanged();
        if (mForceToBottom) {
            scrollToBottom();
        }
    }

    /**
     * 注册一个对应content类型的builder
     * @param contentClass
     * @param builder
     */
    public void registerMessageViewBuilder(Class<? extends Content> contentClass,
                                           BuilderManager.ViewBuilder builder) {
        mBuilderManager.registerMessageViewBuilder(contentClass, builder);
        mListView.setAdapter(mAdapter);
    }

    public void registerMessageViewBuilder(Class<? extends Content> contentClass,
                                           BuilderManager.ViewBuilder leftBuilder,
                                           BuilderManager.ViewBuilder rightBuilder) {
        mBuilderManager.registerMessageViewBuilder(contentClass, leftBuilder, rightBuilder);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 是否已经滑动到聊天窗的底部
     * @return
     */
    public boolean hasReachedBottom() {
        int lastVisible = mListView.getLastVisiblePosition();
        Adapter adapter = mListView.getAdapter();
        return adapter == null ? true : lastVisible == adapter.getCount() - 1;
    }

    /**
     * 滑动到底部
     */
    public void scrollToBottom() {
        Adapter adapter = mListView.getAdapter();
        if (adapter != null) {
            mListView.smoothScrollToPosition(adapter.getCount() - 1);
        }
    }

    /**
     * 开启自动滑动
     * @param enable true，当新消息加入时滑动到最底部
     */
    public void enableAutoScroll(boolean enable) {
        mAutoScroll = enable;
        mForceToBottom &= mAutoScroll;
    }

    public ListView getChatListView() {
        return mListView;
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
            if (msg != null) {
                Content content = msg.getContent();
                if (content != null) {
                    return mBuilderManager.getItemViewType(content.getClass(), msg.fromMe());
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

            BuilderManager.ViewBuilder builder;
            if (message != null) {
                Content content = message.getContent();
                if (content != null) {
                    builder = mBuilderManager.getBuilder(content.getClass(), message.fromMe());
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
            holder.onBindViewHolder(message);

            return convertView;
        }
    }
}
