package com.gage.petfish_android.util;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * ---日期----------维护人-----------
 * 2017/9/5       zuoyouming
 */

public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())//切换子线程
                        .observeOn(AndroidSchedulers.mainThread());//传递主线程更新iu操作
            }
        };
    }
//    /**
//     * 统一线程处理
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
//
//    /**
//     * 统一返回结果处理
//     *
//     * @param <T>
//     * @return
//     */
////    public static <T> Observable.Transformer<HttpResult<T>, T> handleMyResult() {   //compose判断结果
////        return new Observable.Transformer<HttpResult<T>, T>() {
////            @Override
////            public Observable<T> call(Observable<HttpResult<T>> httpResponseObservable) {
////                return httpResponseObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
////                    @Override
////                    public Observable<T> call(HttpResult<T> tHttpResult) {
////                        if (tHttpResult.getCode() == 200) {
////                            return createData(tHttpResult.getData());
////                        } else {
////                            return Observable.error(new ApiException("服务器返回error"));
////                        }
////                    }
////                });
////            }
////        };
////    }
//
//    /**
//     * 生成Observable
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable<T> createData(final T t) {
//        return Observable.create(new Observable.OnSubscribe<T>() {
//            @Override
//            public void call(Subscriber<? super T> subscriber) {
//                try {
//                    subscriber.onNext(t);
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }

}
