package com.hyzs.onekeyhelp.mine.bean;

import com.google.gson.annotations.SerializedName;

public class WeiXinDataBean {
	/**
	 * appid : wx4aa889feeb9d8dcb noncestr : dADHlDrcUR4MwZuW package :
	 * Sign=WXPay partnerid : 1316533701 prepayid :
	 * wx20161102140137bad9c827640203244630 sign :
	 * 5137EB46B903EC4BE731CFC9161C7DF5 timestamp : 1478066377
	 */

	private String appid;
	private String noncestr;
	@SerializedName("package")
	private String packageX;
	private String partnerid;
	private String prepayid;
	private String sign;
	private String timestamp;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getPackageX() {
		return packageX;
	}

	public void setPackageX(String packageX) {
		this.packageX = packageX;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
