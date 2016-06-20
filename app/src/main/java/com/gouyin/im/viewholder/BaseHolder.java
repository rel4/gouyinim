package com.gouyin.im.viewholder;

import android.view.View;

public abstract class BaseHolder<T> {
	protected View contentView;

	public BaseHolder() {
		contentView = initView();
		contentView.setTag(this);
	}

	/**
	 * 初始化每个控件
	 * 
	 * @return
	 */
	protected abstract View initView();

	/**
	 * 给每个控件赋值
	 * 
	 * @param data
	 */
	public abstract void refreshView(T data);

	/**
	 * 获取布局
	 * 
	 * @return
	 */
	public View getContentView() {
		return contentView;
	}
}
