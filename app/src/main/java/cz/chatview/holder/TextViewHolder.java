package cz.chatview.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.chatview.R;
import cz.chatview.builder.ViewHolder;

/**
 * Created by Administrator on 2016/5/21.
 */
public class TextViewHolder extends ViewHolder {
    public LinearLayout container;
    public TextView textView;

    public TextViewHolder(View view) {
        super(view);
        container = (LinearLayout) view;
        textView = (TextView) view.findViewById(R.id.content);
    }
}
