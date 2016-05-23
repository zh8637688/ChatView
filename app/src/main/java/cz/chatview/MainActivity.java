package cz.chatview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.chatview.bean.TextContent;
import cz.chatview.bean.TimestampContent;
import cz.chatview.builder.BuilderManager;
import cz.chatview.holder.TextViewHolder;
import cz.chatview.holder.TimestampViewHolder;
import cz.chatview.message.bean.Message;
import cz.chatview.uitls.ViewHolder;
import cz.chatview.widget.ChatView;


public class MainActivity extends Activity {
    ChatView chatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatView = (ChatView) findViewById(R.id.chatview);
        chatView.registerMessageViewBuilder(TextContent.class, new BuilderManager.ViewBuilder() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                View view = getLayoutInflater().inflate(R.layout.message_item_text, parent, false);
                return new TextViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, Message message) {
                TextContent tc = (TextContent) message.getContent();
                TextViewHolder tvh = (TextViewHolder) holder;
                tvh.textView.setText(tc.text);
                if (message.fromMe()) {
                    tvh.container.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                } else {
                    tvh.container.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                }
            }
        });
        chatView.registerMessageViewBuilder(TimestampContent.class, new BuilderManager.ViewBuilder() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new TimestampViewHolder(getLayoutInflater().inflate(R.layout.message_item_timestamp,parent,false));
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, Message message) {
                TimestampContent content = (TimestampContent) message.getContent();
                Date date = new Date(content.timestamp);
                SimpleDateFormat df = new SimpleDateFormat();
                ((TimestampViewHolder)holder).text.setText(df.format(date));
            }
        });
        chatView.enableAutoScroll(true);

        chatView.add(new Message(0, "", "", 0, new TextContent("hello"), true));
        chatView.add(new Message(0,"","",0,new TimestampContent(System.currentTimeMillis()),false));
        chatView.add(new Message(0,"","",0,new TextContent("hello"),false));

    }
}
