package com.moonsister.im.home.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moonsister.im.CacheManager;
import com.moonsister.im.R;
import com.moonsister.im.adapter.SearchAdapter;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.bean.SearchReasonBaen;
import com.moonsister.im.home.presenetr.SearchReasonActivityPresenter;
import com.moonsister.im.home.presenetr.SearchReasonActivityPresenterImpl;
import com.moonsister.im.home.view.SearchReasonActivityView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.widget.XListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/7/10.
 */
public class SearchReasonActivity extends BaseActivity implements TextWatcher, SearchReasonActivityView {
    @Bind(R.id.et_channel_find)
    EditText etChannelFind;
    @Bind(R.id.btn_search_pager_cancel)
    Button btn_search_pager_cancel;
    @Bind(R.id.xlv)
    XListView xlv;
    @Bind(R.id.tv_not_data)
    TextView tv_not_data;
    private String key;
    private SearchReasonActivityPresenter presenter;

    @Override
    protected View setRootContentView() {
        key = getIntent().getStringExtra("key");
        if (StringUtis.isEmpty(key)) {
            finish();
        }
        return UIUtils.inflateLayout(R.layout.activity_search_reason);
    }

    @Override
    protected void initView() {
        etChannelFind.addTextChangedListener(this);
        xlv.setVerticalLinearLayoutManager();
        xlv.setPullRefreshEnabled(false);
        xlv.setLoadingMoreEnabled(false);
        xlv.setEmptyView(tv_not_data);
        presenter = new SearchReasonActivityPresenterImpl();
        presenter.attachView(this);
        search();
    }

    private void search() {
        etChannelFind.setHint(key);
        saveKey();
        presenter.loadBasicData(key);
    }

    private void saveKey() {
        boolean exist4DataCache = CacheManager.isExist4DataCache(this, CacheManager.CachePath.SEARCH_HISTPRY);
        if (exist4DataCache) {
            ArrayList<String> keys = CacheManager.readObject(this, CacheManager.CachePath.SEARCH_HISTPRY);
            if (keys == null)
                keys = new ArrayList<String>();
            if (keys.contains(key))
                return;
            if (keys.size() == 6) {
                keys.remove(keys.get(5));
            }
            keys.add(0, key);
            CacheManager.saveObject(this, keys, CacheManager.CachePath.SEARCH_HISTPRY);
        } else {
            ArrayList<String> keys = new ArrayList<String>();
            keys.add(key);
            CacheManager.saveObject(this, keys, CacheManager.CachePath.SEARCH_HISTPRY);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String enter_key = etChannelFind.getText().toString();
        if (enter_key.length() == 0) {
            btn_search_pager_cancel.setText(UIUtils.getStringRes(R.string.cancel));
        } else {
            btn_search_pager_cancel.setText(UIUtils.getStringRes(R.string.search));
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void transfePageMsg(String msg) {
        showToast(msg);
    }

    private SearchAdapter adapter;

    @Override
    public void setReasonData(SearchReasonBaen searchReasonBaen) {
        if (searchReasonBaen == null)
            return;
        if (adapter == null) {
            adapter = new SearchAdapter(searchReasonBaen.getData());
            adapter.setSearchReasonActivityView(this);
            xlv.setAdapter(adapter);
        } else {
            adapter.addListData(searchReasonBaen.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.btn_search_pager_cancel)
    public void onClick() {
        String s = btn_search_pager_cancel.getText().toString();
        if (StringUtis.equals(s, UIUtils.getStringRes(R.string.cancel))) {
            finish();
        } else {
            key = etChannelFind.getText().toString();
            if (adapter != null)
                adapter.clean();
            search();
        }
    }
}
