package cz.chatview;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

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
        chatView.registerMessageViewBuilder(TextContent.class,
                new BuilderManager.ViewBuilder() {
                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent) {
                        return new TextViewHolder(getLayoutInflater().inflate(R.layout.message_item_text_left, parent, false));
                    }
                },
                new BuilderManager.ViewBuilder() {
                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent) {
                        return new TextViewHolder(getLayoutInflater().inflate(R.layout.message_item_text_right, parent, false));
                    }
                }
        );
        chatView.registerMessageViewBuilder(TimestampContent.class, new BuilderManager.ViewBuilder() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new TimestampViewHolder(getLayoutInflater().inflate(R.layout.message_item_timestamp,parent,false));
            }
        });
        chatView.enableAutoScroll(true);

        chatView.add(new Message(0, "A", "", 0, new TextContent("i am left"), false));
        chatView.add(new Message(0,"","",0,new TimestampContent(System.currentTimeMillis()),false));
        chatView.add(new Message(0,"Me","",0,new TextContent("i am right"),true));

    }
}
