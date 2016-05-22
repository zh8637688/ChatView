package cz.chatview.holder;

import android.view.View;
import android.widget.TextView;

import cz.chatview.R;
import cz.chatview.builder.ViewHolder;

/**
 * Created by Administrator on 2016/5/22.
 */
public class TimestampViewHolder extends ViewHolder {
    public TextView text;
    public TimestampViewHolder(View view) {
        super(view);
        text = (TextView) view.findViewById(R.id.content);
    }
}
