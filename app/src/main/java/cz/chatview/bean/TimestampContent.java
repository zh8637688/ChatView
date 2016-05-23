package cz.chatview.bean;

import cz.chatview.message.bean.Content;

/**
 * Created by Administrator on 2016/5/22.
 */
public class TimestampContent extends Content {
    public long timestamp;
    public TimestampContent(long timestamp) {
        this.timestamp = timestamp;
    }
}
