package com.hangon.bean.user;

public class UserInfo {
	private String userName;//用户名
	private String userPass;//密码
	private String nickname;//昵称
	private String sex;//性别
	private int age;//年龄
	private String driverNum;//驾驶证号
	private boolean isSave;//是否保存


	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDriverNum() {
		return driverNum;
	}

	public void setDriverNum(String driverNum) {
		this.driverNum = driverNum;
	}

	public boolean isSave() {
		return isSave;
	}

	public void setIsSave(boolean isSave) {
		this.isSave = isSave;
	}
}