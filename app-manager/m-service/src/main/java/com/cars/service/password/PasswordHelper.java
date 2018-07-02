package com.cars.service.password;

import com.cars.model.sys.SysUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordHelper {
	//CredentialsMatcher需要和此处加密的算法一样
	private String algorithmName = "md5";
	private int hashIterations = 2;

	/**
	 * 新生成加密密码
	 * @param user
	 */
	public void encryptPassword(SysUser user) {
		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		String salt=randomNumberGenerator.nextBytes().toHex();
		String newPassword = new SimpleHash(algorithmName, user.getPassWord(), salt, hashIterations).toHex();
		user.setSalt(salt);
		user.setPassWord(newPassword);
	}

	/**
	 * 根据已有盐值对密码进行加密
	 * @param password
	 * @param salt
	 * @return
	 */
	public String encryptPasswordWithSalt(String password,String salt) {
		String passWord = new SimpleHash(algorithmName, password, salt, hashIterations).toHex();
		return passWord;
	}

	public static void main(String[] args) {
		PasswordHelper passwordHelper = new PasswordHelper();
		SysUser user = new SysUser();
		user.setUserName("admin");
			user.setPassWord("123456");
		passwordHelper.encryptPassword(user);
		System.out.println(user);

		RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		String salt=randomNumberGenerator.nextBytes().toHex();
		System.out.println(salt);
	}
}
