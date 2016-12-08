package net.leeautumn.authlogin.service;

import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by coolAutumn on 3/10/16.
 */
@Transactional
public interface SendMailService
{
    /**
     * 初始化并连接所有的邮件服务器
     * @throws MessagingException 初始化或连接异常
     */
    public void connect() throws MessagingException;

    /**
     * 发送单封邮件
     * @param recipient 收件人邮箱地址
     * @param subject 邮件主题
     * @param content 邮件正文
     * @throws MessagingException 发送邮件错误
     */
    public void send(String recipient, String subject, String content) throws MessagingException;

    /**
     * 询问服务器是否有新邮件到达
     * @return 布朗值，指示是否有新邮件
     * @throws MessagingException 询问服务器出错
     */
    public boolean listen() throws MessagingException;

    /**
     * 接收自动回复的内容，并转换为字符串
     * 注：用你能想到的任意方法寻找回复邮件均可，并不一定要用到这两个参数
     * @param sender 自动回复的发件人邮箱地址
     * @param subject 自动回复的主题
     * @return 自动回复的内容字符串
     * @throws MessagingException 查询邮件异常
     * @throws IOException 下载邮件异常
     */
    public String getReplyMessageContent(String sender, String subject) throws MessagingException, IOException;
}
