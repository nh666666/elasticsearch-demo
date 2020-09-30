//package com.cloud.example.clouddemo;
//
//import com.cloud.example.clouddemo.mail.service.MailService;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class ClouddemoApplicationTests {
//
//	@Autowired
//	MailService mailService;
//
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
//
//	//@Test
//	public void contextLoads() {
//		System.out.println("wjk i love you");
//	}
//
//	//@Test
//	public void testSimpleMail() throws Exception {
//		System.out.println("开始");
//		//1301658629@qq.com 15730235278@163.com
//		mailService.sendSimpleMail("331105094@qq.com","欢迎新用户(请不要回复此邮件)。","您正在申请注册新用户，您的注册验证码为1576");
//		//mailService.doSendEmailCaptcha("15730235278@163.com", 1578, 1);
//		System.out.println("结束");
//	}
//
//	//@Test
//	public void toHash(){
//		String password = "123";
//		Md5Hash hash = new Md5Hash(password,null,2);
//		password = String.valueOf(hash);
//		System.out.println("加密之后的密码为："+password);
//	}
//}
