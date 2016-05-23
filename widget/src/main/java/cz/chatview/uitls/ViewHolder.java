package cz.chatview.uitls;

import android.view.View;

import cz.chatview.message.bean.Message;

/**
 * Created by haozhou on 2016/5/20.
 */
public abstract class ViewHolder {

    private View mView;

    public ViewHolder(View view) {
        mView = view;
        if (view != null) {
            view.setTag(this);
        }
    }

    public View getView() {
        return mView;
    }

    public abstract void onBindViewHolder(Message message);

    public static final class EmptyViewHolder extends ViewHolder {
        public EmptyViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(Message message) {

        }
    }
}
