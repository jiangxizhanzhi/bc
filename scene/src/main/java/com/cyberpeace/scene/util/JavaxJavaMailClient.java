package com.cyberpeace.scene.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import com.cyberpeace.common.util.ConfigUtil;

public class JavaxJavaMailClient {

	public static String emailHost = ConfigUtil.getContextProperty("ojj.mail.host");

	public static String transportType = "smtp";

	public static String fromUser = "gp-game";

	public static String fromEmail = "gp-game@qq.com";

	public static String authCode = "sec123";

	public static String toEmail = "lihua@qq.com";

	public static String subject = "";

	public static void sendMail(Integer port, String text) throws IOException, MessagingException {

		// 1：初始化默认参数
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", transportType);
		props.setProperty("mail.host", emailHost);
		props.setProperty("mail.user", fromUser);
		props.setProperty("mail.from", fromEmail);
		props.setProperty("mail.smtp.ssl.enable", "true");
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.setProperty("mail.smtp.socketFactory", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.ssl.trust", emailHost);
		// 2：获取Session对象
		Session session = Session.getInstance(props, null);
		session.setDebug(true);

		// 3：创建MimeMessage对象
		MimeMessage message = new MimeMessage(session);

		// 4：设置发件人、收件人、主题、（内容后面设置）
		String formName = MimeUtility.encodeWord("蚂蚁小哥") + " <" + fromEmail + ">";
		InternetAddress from = new InternetAddress(formName);
		message.setFrom(from);
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
		MimeBodyPart file_body = new MimeBodyPart();
		DataHandler dhFile = new DataHandler(JavaxJavaMailClient.class
				.getResource("/cpms/upload/images/00bcb98c11684a02ba2373837aabe56c.png"));
		// 设置dhFile附件处理
		file_body.setDataHandler(dhFile);
		// 设置资源附件名称ID
		file_body.setContentID("fileA");
		file_body.setContent("dadjajsdjjasdasdsa", "text/html;charset=UTF-8");
		// 设置中文附件名称
		file_body.setFileName(MimeUtility.encodeText("一个附件.zip"));

		// 5.2：先把附件资源混合到 mixed多资源邮件模块里
		mixed.addBodyPart(file_body);

		// 5.3：创建主体内容资源存储对象
		MimeBodyPart content = new MimeBodyPart();
		// 把主体内容混合到资源存储对象里
		mixed.addBodyPart(content);
		// 5.4：设置多资源内容
		// =============== 构建邮件内容：多信息片段邮件 使用Content-Type:multipart/related
		// ===============//
		// 5.4.1：构建一个多资源的邮件块 用来把 文本内容资源 和 图片资源合并；；；related代表多资源关联
		MimeMultipart text_img_related = new MimeMultipart("related");
		// text_img_related.setSubType("related");
		// 5.4.2：把关联的把多资源邮件块 混合到mixed多资源邮件模块里
		content.setContent(text_img_related);

		// 5.4.4：创建文本资源，文本资源并引用上面的图片ID（因为这两个资源我做了关联）
		MimeBodyPart text_body = new MimeBodyPart();
		text_body.setContent(text, "text/html;charset=UTF-8");

		// 5.4.5：把创建出来的两个资源合并到多资源模块了
		text_img_related.addBodyPart(text_body);
		// 6：设置我们处理好的资源（存放到Message）
		message.setContent(mixed);

		// 7：保存上面设置的邮件内容
		message.saveChanges();
		// 8：获取Transport对象
		Transport transport = session.getTransport();
		// 9：smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
		// System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		transport.connect(null, port, null, authCode);
		// 10：发送邮件
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

}