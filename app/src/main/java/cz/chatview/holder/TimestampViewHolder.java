package cz.chatview.holder;

import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.chatview.R;
import cz.chatview.bean.TimestampContent;
import cz.chatview.message.bean.Message;
import cz.chatview.uitls.ViewHolder;

/**
 * Created by Administrator on 2016/5/22.
 */
public class TimestampViewHolder extends ViewHolder {
    public TextView text;
    public TimestampViewHolder(View view) {
        super(view);
        text = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public void onBindViewHolder(Message message) {
        TimestampContent content = (TimestampContent) message.getContent();
        Date date = new Date(content.timestamp);
        SimpleDateFormat df = new SimpleDateFormat();
        text.setText(df.format(date));
    }
}
