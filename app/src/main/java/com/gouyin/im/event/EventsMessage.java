/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gouyin.im.event;

import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.util.TimeUtils;


/**
 *
 * Defines a EventsMessage containing a description and arbitrary data object that can be
 * sent to a {@link Handler}.  This object contains two extra int fields and an
 * extra object field that allow you to not do allocations in many cases.
 *
 * <p class="note">While the constructor of EventsMessage is public, the best way to get
 * one of these is to call {@link #obtain EventsMessage.obtain()} or one of the
 *  methods, which will pull
 * them from a pool of recycled objects.</p>
 */
public final class EventsMessage  implements Parcelable {
    /**
     * User-defined EventsMessage code so that the recipient can identify
     * what this EventsMessage is about. Each {@link Handler} has its own name-space
     * for EventsMessage codes, so you do not need to worry about yours conflicting
     * with other handlers.
     */
    public int what;

    /**
     * arg1 and arg2 are lower-cost alternatives to using
     * {@link #setData(Bundle) setData()} if you only need to store a
     * few integer values.
     */
    public int arg1;

    /**
     * arg1 and arg2 are lower-cost alternatives to using
     * {@link #setData(Bundle) setData()} if you only need to store a
     * few integer values.
     */
    public int arg2;

    /**
     * An arbitrary object to send to the recipient.  When using
     * {@link Messenger} to send the EventsMessage across processes this can only
     * be non-null if it contains a Parcelable of a framework class (not one
     * implemented by the application).   For other data transfer use
     * {@link #setData}.
     *
     * <p>Note that Parcelable objects here are not supported prior to
     * the {@link android.os.Build.VERSION_CODES#FROYO} release.
     */
    public Object obj;

    /**
     * Optional Messenger where replies to this EventsMessage can be sent.  The
     * semantics of exactly how this is used are up to the sender and
     * receiver.
     */
    public Messenger replyTo;

    /**
     * Optional field indicating the uid that sent the EventsMessage.  This is
     * only valid for EventsMessages posted by a {@link Messenger}; otherwise,
     * it will be -1.
     */
    public int sendingUid = -1;

    /** If set EventsMessage is in use.
     * This flag is set when the EventsMessage is enqueued and remains set while it
     * is delivered and afterwards when it is recycled.  The flag is only cleared
     * when a new EventsMessage is created or obtained since that is the only time that
     * applications are allowed to modify the contents of the EventsMessage.
     *
     * It is an error to attempt to enqueue or recycle a EventsMessage that is already in use.
     */
    /*package*/ static final int FLAG_IN_USE = 1 << 0;

    /** If set EventsMessage is asynchronous */
    /*package*/ static final int FLAG_ASYNCHRONOUS = 1 << 1;

    /** Flags to clear in the copyFrom method */
    /*package*/ static final int FLAGS_TO_CLEAR_ON_COPY_FROM = FLAG_IN_USE;

    /*package*/ int flags;

    /*package*/ long when;

    /*package*/ Bundle data;

    /*package*/ Handler target;

    /*package*/ Runnable callback;

    // sometimes we store linked lists of these things
    /*package*/ EventsMessage next;

    private static final Object sPoolSync = new Object();
    private static EventsMessage sPool;
    private static int sPoolSize = 0;

    private static final int MAX_POOL_SIZE = 50;

    private static boolean gCheckRecycle = true;

    /**
     * Return a new EventsMessage instance from the global pool. Allows us to
     * avoid allocating new objects in many cases.
     */
    public static EventsMessage obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                EventsMessage m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new EventsMessage();
    }

    /**
     * Same as {@link #obtain()}, but copies the values of an existing
     * EventsMessage (including its target) into the new one.
     * @param orig Original EventsMessage to copy.
     * @return A EventsMessage object from the global pool.
     */
    public static EventsMessage obtain(EventsMessage orig) {
        EventsMessage m = obtain();
        m.what = orig.what;
        m.arg1 = orig.arg1;
        m.arg2 = orig.arg2;
        m.obj = orig.obj;
        m.replyTo = orig.replyTo;
        m.sendingUid = orig.sendingUid;
        if (orig.data != null) {
            m.data = new Bundle(orig.data);
        }
        m.target = orig.target;
        m.callback = orig.callback;

        return m;
    }

    /**
     * Same as {@link #obtain()}, but sets the value for the <em>target</em> member on the EventsMessage returned.
     * @param h  Handler to assign to the returned EventsMessage object's <em>target</em> member.
     * @return A EventsMessage object from the global pool.
     */
    public static EventsMessage obtain(Handler h) {
        EventsMessage m = obtain();
        m.target = h;

        return m;
    }

    /**
     * Same as {@link #obtain(Handler)}, but assigns a callback Runnable on
     * the EventsMessage that is returned.
     * @param h  Handler to assign to the returned EventsMessage object's <em>target</em> member.
     * @param callback Runnable that will execute when the EventsMessage is handled.
     * @return A EventsMessage object from the global pool.
     */
    public static EventsMessage obtain(Handler h, Runnable callback) {
        EventsMessage m = obtain();
        m.target = h;
        m.callback = callback;

        return m;
    }

    /**
     * Same as {@link #obtain()}, but sets the values for both <em>target</em> and
     * <em>what</em> members on the EventsMessage.
     * @param h  Value to assign to the <em>target</em> member.
     * @param what  Value to assign to the <em>what</em> member.
     * @return A EventsMessage object from the global pool.
     */
    public static EventsMessage obtain(Handler h, int what) {
        EventsMessage m = obtain();
        m.target = h;
        m.what = what;

        return m;
    }

    /**
     * Same as {@link #obtain()}, but sets the values of the <em>target</em>, <em>what</em>, and <em>obj</em>
     * members.
     * @param h  The <em>target</em> value to set.
     * @param what  The <em>what</em> value to set.
     * @param obj  The <em>object</em> method to set.
     * @return  A EventsMessage object from the global pool.
     */
    public static EventsMessage obtain(Handler h, int what, Object obj) {
        EventsMessage m = obtain();
        m.target = h;
        m.what = what;
        m.obj = obj;

        return m;
    }

    /**
     * Same as {@link #obtain()}, but sets the values of the <em>target</em>, <em>what</em>,
     * <em>arg1</em>, and <em>arg2</em> members.
     *
     * @param h  The <em>target</em> value to set.
     * @param what  The <em>what</em> value to set.
     * @param arg1  The <em>arg1</em> value to set.
     * @param arg2  The <em>arg2</em> value to set.
     * @return  A EventsMessage object from the global pool.
     */
    public static EventsMessage obtain(Handler h, int what, int arg1, int arg2) {
        EventsMessage m = obtain();
        m.target = h;
        m.what = what;
        m.arg1 = arg1;
        m.arg2 = arg2;

        return m;
    }

    /**
     * Same as {@link #obtain()}, but sets the values of the <em>target</em>, <em>what</em>,
     * <em>arg1</em>, <em>arg2</em>, and <em>obj</em> members.
     *
     * @param h  The <em>target</em> value to set.
     * @param what  The <em>what</em> value to set.
     * @param arg1  The <em>arg1</em> value to set.
     * @param arg2  The <em>arg2</em> value to set.
     * @param obj  The <em>obj</em> value to set.
     * @return  A EventsMessage object from the global pool.
     */
    public static EventsMessage obtain(Handler h, int what,
                                 int arg1, int arg2, Object obj) {
        EventsMessage m = obtain();
        m.target = h;
        m.what = what;
        m.arg1 = arg1;
        m.arg2 = arg2;
        m.obj = obj;

        return m;
    }

    /** @hide */
    public static void updateCheckRecycle(int targetSdkVersion) {
        if (targetSdkVersion < Build.VERSION_CODES.LOLLIPOP) {
            gCheckRecycle = false;
        }
    }

    /**
     * Return a EventsMessage instance to the global pool.
     * <p>
     * You MUST NOT touch the EventsMessage after calling this function because it has
     * effectively been freed.  It is an error to recycle a EventsMessage that is currently
     * enqueued or that is in the process of being delivered to a Handler.
     * </p>
     */
    public void recycle() {
        if (isInUse()) {
            if (gCheckRecycle) {
                throw new IllegalStateException("This EventsMessage cannot be recycled because it "
                        + "is still in use.");
            }
            return;
        }
        recycleUnchecked();
    }

    /**
     * Recycles a EventsMessage that may be in-use.
     * Used internally by the EventsMessageQueue and Looper when disposing of queued EventsMessages.
     */
    void recycleUnchecked() {
        // Mark the EventsMessage as in use while it remains in the recycled object pool.
        // Clear out all other details.
        flags = FLAG_IN_USE;
        what = 0;
        arg1 = 0;
        arg2 = 0;
        obj = null;
        replyTo = null;
        sendingUid = -1;
        when = 0;
        target = null;
        callback = null;
        data = null;

        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    /**
     * Make this EventsMessage like o.  Performs a shallow copy of the data field.
     * Does not copy the linked list fields, nor the timestamp or
     * target/callback of the original EventsMessage.
     */
    public void copyFrom(EventsMessage o) {
        this.flags = o.flags & ~FLAGS_TO_CLEAR_ON_COPY_FROM;
        this.what = o.what;
        this.arg1 = o.arg1;
        this.arg2 = o.arg2;
        this.obj = o.obj;
        this.replyTo = o.replyTo;
        this.sendingUid = o.sendingUid;

        if (o.data != null) {
            this.data = (Bundle) o.data.clone();
        } else {
            this.data = null;
        }
    }

    /**
     * Return the targeted delivery time of this EventsMessage, in milliseconds.
     */
    public long getWhen() {
        return when;
    }

    public void setTarget(Handler target) {
        this.target = target;
    }

    /**
     * Retrieve the a {@link android.os.Handler Handler} implementation that
     * will receive this EventsMessage. The object must implement
     * . Each Handler has its own name-space for
     * EventsMessage codes, so you do not need to
     * worry about yours conflicting with other handlers.
     */
    public Handler getTarget() {
        return target;
    }

    /**
     * Retrieve callback object that will execute when this EventsMessage is handled.
     * This object must implement Runnable. This is called by
     * the <em>target</em> {@link Handler} that is receiving this EventsMessage to
     * dispatch it.  If
     * not set, the EventsMessage will be dispatched to the receiving Handler's
     * .
     */
    public Runnable getCallback() {
        return callback;
    }

    /**
     * Obtains a Bundle of arbitrary data associated with this
     * event, lazily creating it if necessary. Set this value by calling
     * {@link #setData(Bundle)}.  Note that when transferring data across
     * processes via {@link Messenger}, you will need to set your ClassLoader
     * on the Bundle via {@link Bundle#setClassLoader(ClassLoader)
     * Bundle.setClassLoader()} so that it can instantiate your objects when
     * you retrieve them.
     * @see #peekData()
     * @see #setData(Bundle)
     */
    public Bundle getData() {
        if (data == null) {
            data = new Bundle();
        }

        return data;
    }

    /**
     * Like getData(), but does not lazily create the Bundle.  A null
     * is returned if the Bundle does not already exist.  See
     * {@link #getData} for further information on this.
     * @see #getData()
     * @see #setData(Bundle)
     */
    public Bundle peekData() {
        return data;
    }

    /**
     * Sets a Bundle of arbitrary data values. Use arg1 and arg2 members
     * as a lower cost way to send a few simple integer values, if you can.
     * @see #getData()
     * @see #peekData()
     */
    public void setData(Bundle data) {
        this.data = data;
    }

    /**
     * Sends this EventsMessage to the Handler specified by {@link #getTarget}.
     * Throws a null pointer exception if this field has not been set.
     */
//    public void sendToTarget() {
//        target.sendMessage(this);
//    }

    /**
     * Returns true if the EventsMessage is asynchronous, meaning that it is not
     * subject to {@link Looper} synchronization barriers.
     *
     * @return True if the EventsMessage is asynchronous.
     *
     * @see #setAsynchronous(boolean)
     */
    public boolean isAsynchronous() {
        return (flags & FLAG_ASYNCHRONOUS) != 0;
    }

    /**
     * Sets whether the EventsMessage is asynchronous, meaning that it is not
     * subject to {@link Looper} synchronization barriers.
     * <p>
     * Certain operations, such as view invalidation, may introduce synchronization
     * barriers into the {@link Looper}'s EventsMessage queue to prevent subsequent EventsMessages
     * from being delivered until some condition is met.  In the case of view invalidation,
     * EventsMessages which are posted after a call to {@link android.view.View#invalidate}
     * are suspended by means of a synchronization barrier until the next frame is
     * ready to be drawn.  The synchronization barrier ensures that the invalidation
     * request is completely handled before resuming.
     * </p><p>
     * Asynchronous EventsMessages are exempt from synchronization barriers.  They typically
     * represent interrupts, input events, and other signals that must be handled independently
     * even while other work has been suspended.
     * </p><p>
     * Note that asynchronous EventsMessages may be delivered out of order with respect to
     * synchronous EventsMessages although they are always delivered in order among themselves.
     * If the relative order of these EventsMessages matters then they probably should not be
     * asynchronous in the first place.  Use with caution.
     * </p>
     *
     * @param async True if the EventsMessage is asynchronous.
     *
     * @see #isAsynchronous()
     */
    public void setAsynchronous(boolean async) {
        if (async) {
            flags |= FLAG_ASYNCHRONOUS;
        } else {
            flags &= ~FLAG_ASYNCHRONOUS;
        }
    }

    /*package*/ boolean isInUse() {
        return ((flags & FLAG_IN_USE) == FLAG_IN_USE);
    }

    /*package*/ void markInUse() {
        flags |= FLAG_IN_USE;
    }

    /** Constructor (but the preferred way to get a EventsMessage is to call {@link #obtain() EventsMessage.obtain()}).
     */
    public EventsMessage() {
    }

    @Override
    public String toString() {
        return toString(SystemClock.uptimeMillis());
    }

    String toString(long now) {
        StringBuilder b = new StringBuilder();
        b.append("{ when=");
        TimeUtils.formatDuration(when - now, b);

        if (target != null) {
            if (callback != null) {
                b.append(" callback=");
                b.append(callback.getClass().getName());
            } else {
                b.append(" what=");
                b.append(what);
            }

            if (arg1 != 0) {
                b.append(" arg1=");
                b.append(arg1);
            }

            if (arg2 != 0) {
                b.append(" arg2=");
                b.append(arg2);
            }

            if (obj != null) {
                b.append(" obj=");
                b.append(obj);
            }

            b.append(" target=");
            b.append(target.getClass().getName());
        } else {
            b.append(" barrier=");
            b.append(arg1);
        }

        b.append(" }");
        return b.toString();
    }

    public static final Parcelable.Creator<EventsMessage> CREATOR
            = new Parcelable.Creator<EventsMessage>() {
        public EventsMessage createFromParcel(Parcel source) {
            EventsMessage msg = EventsMessage.obtain();
            msg.readFromParcel(source);
            return msg;
        }

        public EventsMessage[] newArray(int size) {
            return new EventsMessage[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (callback != null) {
            throw new RuntimeException(
                    "Can't marshal callbacks across processes.");
        }
        dest.writeInt(what);
        dest.writeInt(arg1);
        dest.writeInt(arg2);
        if (obj != null) {
            try {
                Parcelable p = (Parcelable)obj;
                dest.writeInt(1);
                dest.writeParcelable(p, flags);
            } catch (ClassCastException e) {
                throw new RuntimeException(
                        "Can't marshal non-Parcelable objects across processes.");
            }
        } else {
            dest.writeInt(0);
        }
        dest.writeLong(when);
        dest.writeBundle(data);
        Messenger.writeMessengerOrNullToParcel(replyTo, dest);
        dest.writeInt(sendingUid);
    }

    private void readFromParcel(Parcel source) {
        what = source.readInt();
        arg1 = source.readInt();
        arg2 = source.readInt();
        if (source.readInt() != 0) {
            obj = source.readParcelable(getClass().getClassLoader());
        }
        when = source.readLong();
        data = source.readBundle();
        replyTo = Messenger.readMessengerOrNullFromParcel(source);
        sendingUid = source.readInt();
    }
}