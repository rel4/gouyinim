package com.gouyin.im.event;

/**
 * @author thanatos
 * @create 2016-01-05
 **/
public class Events<T> {

    public enum EventEnum {
       GET_PHOTO,REGITER_CODE, PIC_DESTROY, GET_PHOTO_LIST, LOGIN, GET_RONGYUN_KEY, GO_TO_HOME;
    }

    public EventEnum what;
    public T message;

    public static <O> Events<O> just(O t) {
        Events<O> events = new Events<>();
        events.message = t;
        return events;
    }

    public <T> T getMessage() {
        return (T) message;
    }

}
