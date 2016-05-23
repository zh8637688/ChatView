package cz.chatview.holder;

import android.view.View;
import android.widget.TextView;

import cz.chatview.R;
import cz.chatview.bean.TextContent;
import cz.chatview.message.bean.Message;
import cz.chatview.uitls.ViewHolder;

/**
 * Created by Administrator on 2016/5/21.
 */
public class TextViewHolder extends ViewHolder {
    public TextView nameView;
    public TextView textView;

    public TextViewHolder(View view) {
        super(view);
        nameView = (TextView) view.findViewById(R.id.name);
        textView = (TextView) view.findViewById(R.id.msg);
    }

    @Override
    public void onBindViewHolder(Message message) {
        TextContent tc = (TextContent) message.getContent();
        nameView.setText(message.getFrom());
        textView.setText(tc.text);
    }
}
