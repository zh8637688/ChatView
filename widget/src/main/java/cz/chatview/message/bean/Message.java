package cz.chatview.message.bean;

/**
 * Created by haozhou on 2016/5/20.
 */
public class Message {
    public enum State {
        SENDING, SUCCESS, FAIL
    }

    private final long mId;
    private final String mFrom;
    private final String mAvatar;
    private final long mTimeStamp;
    private final Content mContent;
    private final boolean mFromMe;

    private State mState;

    public Message(long id, String from, String avatar, long timeStamp, Content content, boolean fromme) {
        mId = id;
        mFrom = from;
        mAvatar = avatar;
        mTimeStamp = timeStamp;
        mContent = content;
        mFromMe = fromme;
    }

    public long getId() {
        return mId;
    }

    public String getFrom() {
        return mFrom;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public Content getContent() {
        return mContent;
    }

    public boolean fromMe() {
        return mFromMe;
    }

    public void setState(State state) {
        mState = state;
    }

    public State getState() {
        return mState;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || !(o instanceof Message))
            return false;

        Message other = (Message) o;
        if (mId != other.mId
                || mFrom != null ? !mFrom.equals(other.mFrom) : other.mFrom != null
                || mTimeStamp != other.mTimeStamp
                || !mContent.equals(mContent))
            return false;

        return true;
    }
}
