package com.matrix.spring.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	private String userId;
	private String pw;
	private String phoneNum;
	private String name;
	private String birthYear;
	private String birthMonth;
	private String birthDay;
	private String gender;
	private String emailAccount;
	private String emailDomain;
	private String addressCity;
	private String addressGu;
	private String addressDong;
	private String profilePhoto;
	
	/**profilePhoto가 null일 때 회원가입*/
	public UserDTO(String userId, String pw, String phoneNum, String name, String birthYear, String birthMonth, String birthDay, String gender, String emailAccount, String emailDomain, String addressCity, String addressGu, String addressDong) {
		setUserId(userId);
		setPw(pw);
		setPhoneNum(phoneNum);
		setName(name);
		setBirthYear(birthYear);
		setBirthMonth(birthMonth);
		setBirthDay(birthDay);
		setGender(gender);
		setEmailAccount(emailAccount);
		setEmailDomain(emailDomain);
		setAddressCity(addressCity);
		setAddressGu(addressGu);
		setAddressDong(addressDong);
		setProfilePhoto(null);
	}
	
	/**회원정보 변경용*/
	public UserDTO(String userId, String emailAccount, String emailDomain, String addressCity, String addressGu, String addressDong,  String phoneNum, String profilePhoto){
		setUserId(userId);
		setEmailAccount(emailAccount);
		setEmailDomain(emailDomain);
		setAddressCity(addressCity);
		setAddressGu(addressGu);
		setAddressDong(addressDong);
		setPhoneNum(phoneNum);
		setProfilePhoto(profilePhoto);
	}
	

}
