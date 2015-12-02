package com.jph.xibaibai.model.http;

/**
 * 请求接口回调
 */
public interface XRequestCallBack {

	/**
	 * 准备操作
	 * 
	 * @param taskId
	 */
	void onPrepare(int taskId);

	/**
	 * 刷新界面的回调方法
	 * 
	 * @param taskId
	 * @param params
	 */
	void onSuccess(int taskId, Object... params);

	/**
	 * 
	 * 请求结束
	 * 
	 * @Title: onEnd
	 * @param taskId
	 * 
	 */
	void onEnd(int taskId);

	/**
	 * 请求失败
	 * 
	 * @param taskId
	 * @param errorCode
	 * @param errorMsg
	 */
	void onFailed(int taskId, int errorCode, String errorMsg);

	/**
	 * 上传/下载文件进度
	 */
	public void onLoading(int taskId, long count, long current);
	
	/**
	 * 
	 * 是否需要返回数据
	 * 
	 * @Title: isCallBack
	 * @return true 返回数据, false 不返回数据（用户判断Activity、fragment 活动状态）
	 * 
	 */
	boolean isCallBack();

}
