package org.elsa.valord.common.utils;

import com.sun.net.ssl.internal.ssl.Provider;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * @author valord577
 * @date 18-8-30 下午1:20
 */
public class Emails {

    /**
     * 接收账户 多账户以 split 分隔
     */
    private static final String TO = "m13095944753@163.com";

    /**
     * 账户分隔符
     */
    private static final String SPLIT = "|";

    /**
     * 发送账户
     */
    private static final String FROM = "wu491607371@qq.com";

    /**
     * 发生账户密码
     */
    private static final String PASSWORD = "hhuyzadjbgcwcade";

    /**
     * 发送账户署名
     */
    private static final String SIGN = "吴凌兮";

    /**
     * 邮件服务器 domain ip
     */
    private static final String MAILHOST = "smtp.qq.com";

    /**
     * 邮件服务器 port
     */
    private static final Integer SMTPPORT = null;

    /**
     * 是否 ssl 加密
     */
    private static final boolean SSL = false;

    /**
     * 发送邮件 默认配置
     *
     * @param subject 标题
     * @param content 内容
     * @return 返回boolean类型 是否发生成功
     */
    public static boolean send(String subject, String content) {
        return mail(TO, SPLIT, subject, content, null, null, FROM, PASSWORD, SIGN, MAILHOST, SMTPPORT, SSL);
    }

    /**
     * 发送邮件 默认配置
     *
     * @param subject    标题
     * @param content    内容
     * @param attachment 附件
     * @param fileName   重命名附件
     * @return 返回boolean类型 是否发生成功
     */
    public static boolean send(String subject, String content, File attachment, String fileName) {
        return mail(TO, SPLIT, subject, content, attachment, fileName, FROM, PASSWORD, SIGN, MAILHOST, SMTPPORT, SSL);
    }

    /**
     * 发送邮件 自由配置
     *
     * @param to         接收账户 多账户以 split 分隔
     * @param split      账户分隔符
     * @param subject    标题
     * @param content    内容
     * @param attachment 附件
     * @param fileName   重命名附件
     * @param from       发送账户
     * @param password   发生账户密码
     * @param sign       发送账户署名
     * @param mailHost   邮件服务器 domain ip
     * @param smtpPort   邮件服务器 port
     * @param ssl        是否 ssl 加密
     * @return 返回boolean类型 是否发生成功
     */
    public static boolean send(String to, String split, String subject, String content, File attachment, String fileName, String from, String password, String sign, String mailHost, Integer smtpPort, boolean ssl) {
        return mail(to, split, subject, content, attachment, fileName, from, password, sign, mailHost, smtpPort, ssl);
    }

    /**
     * 发送邮件 具体实现
     *
     * @param to         接收账户 多账户以 split 分隔
     * @param split      账户分隔符
     * @param subject    标题
     * @param content    内容
     * @param attachment 附件
     * @param fileName   重命名附件
     * @param from       发送账户
     * @param password   发生账户密码
     * @param sign       发送账户署名
     * @param mailHost   邮件服务器 domain ip
     * @param smtpPort   邮件服务器 port
     * @param ssl        是否 ssl 加密
     * @return 返回boolean类型 是否发生成功
     */
    private static boolean mail(String to, String split, String subject, String content, File attachment, String fileName, String from, String password, String sign, String mailHost, Integer smtpPort, boolean ssl) {

        // 判断是否有收件人
        if (StringUtils.isBlank(to)) {
            return false;
        }

        // 创建一个程序与邮件服务器会话对象 Session
        Properties properties = new Properties();
        properties.setProperty("mail.host", mailHost);
        properties.setProperty("mail.smtp.auth", "true");
        if (null != smtpPort) {
            properties.setProperty("mail.smtp.port", smtpPort + "");
        }
        properties.setProperty("mail.transport.protocol", "smtp");
//            properties.setProperty("mail.smtp.timeout", "1000");

        if (ssl) {
            Security.addProvider(new Provider());
            String sslFactory = "javax.net.ssl.SSLSocketFactory";
            properties.setProperty("mail.smtp.socketFactory.class", sslFactory);
            properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        }

        // 验证账号及密码，密码需要是第三方授权码
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        try {
            // 创建一个Message，它相当于是邮件内容
            MimeMessage msg = new MimeMessage(session);
            // 设置发件人的昵称和邮件地址
            String sender = String.format("%s<%s>", MimeUtility.encodeText(sign, "utf-8", "B"), from);
            // 设置发件人
            msg.setFrom(new InternetAddress(sender));
            String[] toList = to.split(split);
            InternetAddress[] toAddress = new InternetAddress[toList.length];
            for (int i = 0; i < toList.length; i++) {
                toAddress[i] = new InternetAddress(toList[i]);
            }
            // 设置发送方式与接收者
            msg.setRecipients(MimeMessage.RecipientType.TO, toAddress);
            // 设置邮件主题
            msg.setSubject(subject, "utf-8");
            // 构造Multipart
            MimeMultipart mp = new MimeMultipart();
            // 向Multipart添加正文
            MimeBodyPart mbpContent = new MimeBodyPart();
            // 设置为utf-8字体
            mbpContent.setText(content + "\n<br/>", "utf-8", "html");
            // 将BodyPart添加到MultiPart中
            mp.addBodyPart(mbpContent);
            // attachment
            if (attachment != null) {
                FileDataSource fileDS = new FileDataSource(attachment);
                DataHandler fileDH = new DataHandler(fileDS);
                MimeBodyPart fileAttachment = new MimeBodyPart();
                fileAttachment.setDataHandler(fileDH);
                fileAttachment.setFileName(MimeUtility.encodeText(fileName));
                mp.addBodyPart(fileAttachment);
            }
            // 向Multipart添加MimeMessage
            msg.setContent(mp);
            // 设置发送日期
            msg.setSentDate(new Date());

            // 3.创建 Transport用于将邮件发送
            Transport.send(msg);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
