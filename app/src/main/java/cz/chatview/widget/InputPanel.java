package cz.chatview.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.chatview.R;
import cz.chatview.bean.TextContent;
import cz.chatview.message.bean.Message;

/**
 * Created by haozhou on 2016/5/23.
 */
public class InputPanel extends LinearLayout {
    private Context mContext;

    private EditText mInput;
    private Button mSendBtn;

    public InputPanel(Context context) {
        super(context);

        inflate(context, R.layout.input_control_panel, this);
        mInput = (EditText) findViewById(R.id.input);
        mSendBtn = (Button) findViewById(R.id.send);

        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                }
                return true;
            }
        });

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSendBtn.setEnabled(!TextUtils.isEmpty(s));
            }
        });

        mSendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        CharSequence input = mInput.getText();
        if (!TextUtils.isEmpty(input)) {
            Message message = new Message(0, "Me", "", 0, new TextContent(input.toString()), true);
            if (mChatView != null) {
                mChatView.add(message);
            }
        }
    }

    ChatView mChatView;
    public void setChatView(ChatView chatView) {
        mChatView = chatView;
    }
}
