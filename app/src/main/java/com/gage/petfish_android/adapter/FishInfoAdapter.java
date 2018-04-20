package com.gage.petfish_android.adapter;

import android.support.v7.widget.RecyclerView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.model.bean.FishInfo;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * ---日期----------维护人-----------
 * 2017/9/28       zuoyouming
 */
public class FishInfoAdapter extends BGARecyclerViewAdapter<FishInfo> {

    public FishInfoAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_fish_info);
    }

    @Override
    protected void fillData(final BGAViewHolderHelper helper, int position, final FishInfo model) {
//        helper.setText(R.id.tv_title_info, model.());
//        helper.setText(R.id.tv_date_info, model.getDate());
//        Disposable subscribe = Flowable.create(new FlowableOnSubscribe<Bitmap>() {
//
//            @Override
//            public void subscribe(FlowableEmitter<Bitmap> e) throws Exception {
////                e.onNext(Glide.with(mContext).load("http://ojyz0c8un.bkt.clouddn.com/b_6.jpg").asBitmap().into(50, 50).get());
//            }
//        }, BackpressureStrategy.BUFFER)
//                .compose(RxUtil.<Bitmap>rxSchedulerHelper()).subscribe(new Consumer<Bitmap>() {
//                    @Override
//                    public void accept(@NonNull Bitmap bitmap) throws Exception {
//                        helper.setImageBitmap(R.id.iv_img_info, bitmap);
//                    }
//                });
//        subscribe.dispose();

    }
}
