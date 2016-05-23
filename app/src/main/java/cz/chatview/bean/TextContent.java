package cz.chatview.bean;

import cz.chatview.message.bean.Content;

/**
 * Created by Administrator on 2016/5/21.
 */
public class TextContent extends Content {
    public String text;

    public TextContent(String t) {
        text = t;
    }
}
