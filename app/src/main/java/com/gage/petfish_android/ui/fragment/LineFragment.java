package com.gage.petfish_android.ui.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.base.BaseFragment;
import com.gage.petfish_android.base.contract.fragmentC.LineContract;
import com.gage.petfish_android.component.ImageLoader;
import com.gage.petfish_android.model.bean.ContactUsBean;
import com.gage.petfish_android.presenter.fragmentP.LinePresenter;
import com.gage.petfish_android.util.FontUtil;
import com.gage.petfish_android.util.SystemUtil;
import com.gage.petfish_android.util.ToastUtil;

import butterknife.BindView;

/**
 * ---日期----------维护人-----------
 * 2017/9/12       zuoyouming
 * 联系我们页面
 */

public class LineFragment extends BaseFragment<LinePresenter> implements LineContract.View {


    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_title_tel)
    TextView tvTitleTel;
    @BindView(R.id.tv_title_email)
    TextView tvTitleEmail;
    @BindView(R.id.tv_title_aaddress)
    TextView tvTitleAaddress;
    @BindView(R.id.tv_title_intro)
    TextView tvTitleIntro;
    @BindView(R.id.ll_line)
    LinearLayout llLine;
    @BindView(R.id.iv_line)
    ImageView ivLine;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_line;
    }

    @Override
    protected void initEventAndData() {
        if (SystemUtil.isNetworkConnected()) {
            mPresenter.getContactUs();
        } else {
            ToastUtil.show(getString(R.string.interneterror));
        }
    }

    @Override
    public void showContactUs(ContactUsBean mContactUsBean) {
        if (mContactUsBean != null) {
            if (tvTitleName != null && tvTitleEmail != null && tvTitleAaddress != null && tvTitleIntro != null && tvTitleTel != null && ivLine != null) {
                tvTitleName.setText(mContactUsBean.getName());
                tvTitleEmail.setText(mContactUsBean.getEmail());
                tvTitleAaddress.setText(mContactUsBean.getAddress());
                String str = getString(R.string.intro) + mContactUsBean.getEnterpriseIntroduction();
                tvTitleIntro.setText(FontUtil.changeTextColor(str, 0, 2, "#323232"));
                tvTitleTel.setText(mContactUsBean.getTel());
                ImageLoader.loadImage(getActivity(), Contact.HOST + mContactUsBean.getImageUrl(), ivLine);
            }
        }
    }
}
