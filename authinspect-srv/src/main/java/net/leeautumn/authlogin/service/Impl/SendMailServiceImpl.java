package net.leeautumn.authlogin.service.Impl;

import com.autumn.authcheck.authlogin.service.SendMailService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by coolAutumn on 3/10/16.
 */
@Named(value = "sendMailService")
@Component
@Transactional
public class SendMailServiceImpl extends Authenticator implements SendMailService
{
    private transient Properties sendProperties=System.getProperties();
    private transient Properties receiveProperties=System.getProperties();
    private transient Session sendSession;
    private transient Session receiveSession;
    private transient Folder folder;
    private transient Store store;
    private String userName;
    private String password;
    private int formerMailCount;

    //if this init method isn't declared,there would make a mistake
    public SendMailServiceImpl(){}

    public SendMailServiceImpl(String user,String pass)
    {
        // TODO Auto-generated constructor stub
        userName=user;
        password=pass;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName,password);
    }

    @Override
    /**
     * 连接服务器,并且获取收件箱的邮件数量
     * @throws MessagingException
     * @author ALIENWARE
     *
     */
    public void connect() throws MessagingException {
        // TODO Auto-generated method stub
        sendProperties.put("mail.smtp.auth", "true");
        //set smtpHostName
        sendProperties.put("mail.smtp.host", "smtp.126.com");
        //create session
        sendSession= Session.getInstance(sendProperties, new SendMailServiceImpl("rootmanager3376@126.com","llxf111103"));
        //set the pop3HostName
        String pop3HostName="pop.126.com";
        //set the protocol
//        receiveProperties.put("mail.store.protocol", "pop3");
        //set pop3hostname
//        receiveProperties.put("mail.pop3.host", pop3HostName);
        //set receiveSession
//        receiveSession=Session.getInstance(receiveProperties, new SendMailServiceImpl("rootmanager3376@126.com","Isslabproject,."));

//        //set store to get the folder in the mailbox
//        store=receiveSession.getStore();
//        store.connect();
//
//        //get message in the folder
//        folder=store.getFolder("inbox");
//        folder.open(Folder.READ_ONLY);
//
//        formerMailCount=folder.getMessageCount();
    }

    @Override
    /**
     * 根据参数来发送邮件
     * @throws MessagingException
     * @author ALIENWARE
     * @param  recipient 收件人的邮箱地址
     * @param subject 	信件的主题
     * @param content	信件的内容
     */
    public void send(String recipient, String subject, String content)
            throws MessagingException {
        // TODO Auto-generated method stub
        //set type of mail
        final MimeMessage mimeMessage=new MimeMessage(sendSession);
        //set the fromname
        mimeMessage.setFrom(new InternetAddress("rootmanager3376@126.com"));
        //set the receiver
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        //set subject of mail
        mimeMessage.setSubject(subject);
        //set content of mail
        mimeMessage.setContent(content,"text/html;charset=utf-8");
        //then send out
        Transport.send(mimeMessage);
        System.out.println(content);
    }

    /**
     * 判断是邮件是否已到达
     * @throws MessagingException
     *
     */
    @Override
    public boolean listen() throws MessagingException {
        // TODO Auto-generated method stub
        //set the pop3HostName
        String pop3HostName="pop.126.com";
        //set the protocol
        receiveProperties.put("mail.store.protocol", "pop3");
        //set pop3hostname
        receiveProperties.put("mail.pop3.host", pop3HostName);
        //set receiveSession
        //不是专门的邮箱用来发送邮件可能会发生错误
        receiveSession=Session.getInstance(receiveProperties, new SendMailServiceImpl("rootmanager3376@126.com","Isslabproject,."));

        //set store to get the folder in the mailbox
        store=receiveSession.getStore();
        store.connect();

        //get message in the folder
        folder=store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);

        if(folder.getMessageCount()!=formerMailCount)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @param 	sender 发件人
     * @param	subject 要寻找邮件的主题
     * @throws 	MessagingException
     * @throws 	IOException
     */
    @Override
    public String getReplyMessageContent(String sender, String subject)
            throws MessagingException, IOException {
        // TODO Auto-generated method stub


        //store the message
        Message[] messages=folder.getMessages();

        //System.out.println(messages);
        for(Message msg:messages)
        {
            //System.out.println(msg.getFrom()[0].toString());
            //System.out.println(msg.getSubject());
            //System.out.println(subject);
            if(msg.getSubject().contains(subject))
            {
                return (String) msg.getContent();

            }
        }
        folder.close(false);
        store.close();
        return null;
    }
}
