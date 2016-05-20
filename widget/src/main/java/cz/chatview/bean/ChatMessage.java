package cz.chatview.bean;

/**
 * Created by haozhou on 2016/5/20.
 */
public class ChatMessage {
    public enum State {
        SENDING, SUCCESS, FAIL
    }

    private final long mId;
    private final String mFrom;
    private final String mTo;
    private final long mTimeStamp;
    private final Content mContent;
    private final boolean mFromMe;

    private State mState;

    public ChatMessage(long id, String from, String to, long timeStamp, Content content, boolean fromme) {
        mId = id;
        mFrom = from;
        mTo = to;
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

    public String getTo() {
        return mTo;
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
        if (o == null || !(o instanceof ChatMessage))
            return false;

        ChatMessage other = (ChatMessage) o;
        if (mId != other.mId
                || mFrom != null ? !mFrom.equals(other.mFrom) : other.mFrom != null
                || mTo != null ? !mTo.equals(other.mTo) : other.mTo != null
                || mTimeStamp != other.mTimeStamp
                || !mContent.equals(mContent))
            return false;

        return true;
    }
}
