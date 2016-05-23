package cz.chatview.uitls;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <先进先出列表>
 *
 * @author haozhou
 */
public class FIFOList<T> {
    /**
     * 同步锁
     */
    private Object mLock = new Object();

    private ArrayList<T> mDatas = new ArrayList<T>();

    /**
     * 最大限度
     */
    private int mLimit = 0;

    /**
     * <设置列表的最大限度>
     *
     * @param limit <=0，不设限
     */
    public void setLimit(int limit) {
        mLimit = limit;
    }

    public int getLimit() {
        return mLimit;
    }

    /**
     * <获得列表的大小>
     *
     * @return
     */
    public int size() {
        synchronized (mLock) {
            return mDatas.size();
        }
    }

    /**
     * <在列表的最后添加一个元素>
     *
     * @param data
     */
    public void add(T data) {
        synchronized (mLock) {
            if (size() < mLimit || mLimit <= 0) {
                mDatas.add(data);
            } else {
                mDatas.remove(0);
                mDatas.add(data);
            }
        }
    }

    /**
     * <将集合的所有元素添加到列表末尾>
     * <若集合大小超过Limit限制，则头部元素将被抛弃>
     *
     * @param datas
     */
    public void add(Collection<T> datas) {
        synchronized (mLock) {
            int s = datas.size();
            int oldSize = mDatas.size();
            if (size() + s <= mLimit || mLimit <= 0) {
                mDatas.addAll(datas);
            } else {
                mDatas.addAll(datas);
                removeRange(0, s + oldSize - mLimit);
            }
        }
    }

    /**
     * <从头部删除元素>
     *
     * @return
     */
    public T remove() {
        synchronized (mLock) {
            if (size() == 0) {
                return null;
            }

            return mDatas.remove(0);
        }
    }

    /**
     * 若大小超过最大容量的3/4，删除头1/4
     */
    public boolean removeIfThreeQuarters() {
        synchronized (mLock) {
            if (mLimit > 0 && mDatas.size() > mLimit / 4 * 3) {
                removeRange(0, mLimit / 4);
                return true;
            } else {
                return false;
            }
        }
    }

    private void removeRange(int from, int to) {
        Class<?> c = mDatas.getClass();
        try {
            Method m = c.getDeclaredMethod("removeRange", int.class, int.class);
            m.setAccessible(true);
            m.invoke(mDatas, from, to);
        } catch (Exception e) {
            Log.e(FIFOList.class.getCanonicalName(), e.toString());
        }
    }

    public void clear() {
        synchronized (mLock) {
            mDatas.clear();
        }
    }

    /**
     * <获取指定位置的元素>
     *
     * @param index
     * @return
     */
    public T get(int index) {
        if (index > size()) {
            return null;
        }

        synchronized (mLock) {
            return mDatas.get(index);
        }
    }

}
