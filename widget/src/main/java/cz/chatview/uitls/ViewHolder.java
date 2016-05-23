package cz.chatview.uitls;

import android.view.View;

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

    public static final class EmptyViewHolder extends ViewHolder {
        public EmptyViewHolder(View view) {
            super(view);
        }
    }
}
