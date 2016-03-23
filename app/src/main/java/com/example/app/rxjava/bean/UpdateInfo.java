package com.example.app.rxjava.bean;

/**
 * <p>Class       : com.xueka.mobile.model.UpdateInfo
 * <p>Description: 版本信息实体
 *
 * @author  周朝阳-zhouchy@xueka.com
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2015-10-14，zhouchy@xueka.com，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public class UpdateInfo {
	/**int versionCode: 版本号*/
	private int versionCode;
	/**String versionName: 版本名称*/
	private String versionName;
	/**String fileSize: 文件大小*/
	private String fileSize;
	/**String url: apk文件地址*/
	private String url;
	/**String description: 版本更新提示信息*/
	private String description;
	/**boolean forceUpdate: 是否强制更新*/
	private boolean isForceUpdate;
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isForceUpdate() {
		return isForceUpdate;
	}
	public void setForceUpdate(boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}
}