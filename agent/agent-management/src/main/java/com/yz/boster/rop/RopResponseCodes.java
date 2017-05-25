package com.yz.boster.rop;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RopResponseCodes implements Serializable {
	
	 public static final int SUCCESS = 0;
     public static final int USER_NOT_LOGIN = 100000;
	 public static final int LOGIN_PASSWORD_ERROR = 100001;
	 public static final int LOGIN_USER_NOT_EXIST = 100002;
	 public static final int LOGIN_USER_NOT_ACTIVE = 100003;
	 
	 
	 
}
