package com.example.wanandroid.base;

import com.example.wanandroid.callback.BaseView;

public abstract class BaseMvpActivity<P extends BasePresenter, M extends BaseModel, V extends BaseView> extends BaseActivity{

    protected P presenter;

    @Override
    protected void initMvp() {
        presenter = initMvpPresenter();
        if (presenter != null) {
            presenter.setModel(initMvpModel());
            presenter.setView(initMvpView());
        }
    }

    protected abstract V initMvpView();

    protected abstract M initMvpModel();

    protected abstract P initMvpPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
