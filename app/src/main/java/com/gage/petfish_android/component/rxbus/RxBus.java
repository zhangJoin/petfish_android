package com.gage.petfish_android.component.rxbus;

import com.gage.petfish_android.util.RxUtil;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * ---日期----------维护人-----------
 * 2017/9/5       zuoyouming
 */

public class RxBus {

    private final FlowableProcessor<Object> bus;

    private RxBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        return RxBusHolder.instance;
    }

    private static class RxBusHolder {
        public static RxBus instance = new RxBus();
    }

    //提供一个新事物
    public void post(Object o) {
        bus.onNext(o);
    }

    //
    public void defaultPost(int code, Object o) {
        bus.onNext(new RxBusBaseMessage(code,o));
    }


    //根据传递的eventType类型返回特定类型(eventType)的被观察者
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType);

    }
    //根据类型,筛选post()发送的数据,过滤获取指定的上游事件
    public Flowable<RxBusBaseMessage> toDefaultFlowable(Class<RxBusBaseMessage> eventType, final int code) {
        return bus.ofType(eventType)
                .filter(new Predicate<RxBusBaseMessage>() {
                    @Override
                    public boolean test(@NonNull RxBusBaseMessage rxBusBaseMessage) throws Exception {
                        //过滤code和eventType都相同的事件
                        return rxBusBaseMessage.getCode() == code;
                    }
                });
    }


    //封装默认订阅
    public <T> Disposable toRxSchedulerFlowable(Class<T> eventType, Consumer<T> act) {
        return bus.ofType(eventType).compose(RxUtil.<T>rxSchedulerHelper()).subscribe(act);
    }

}
