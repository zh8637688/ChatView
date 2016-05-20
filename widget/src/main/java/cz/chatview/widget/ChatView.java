package cz.chatview.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by haozhou on 2016/5/20.
 */
public class ChatView extends LinearLayout {
    private Context mContext;

    private ListView mListView;

    public ChatView (Context context) {
        this (context, null);
    }

    public ChatView (Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;

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
    }

    class ChatListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
