package com.jinhwanb.mootie.mail.service;

import com.jinhwanb.mootie.mail.exception.MailErrorCode;
import com.jinhwanb.mootie.mail.exception.MailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static String num; // 인증 번호

    @Value("${spring.mail.sender-email}")
    private String sender; // 메일 보내는 사람 아이디

    /**
     * 이메일 전송
     * @param mail - 받을 사람 이메일 주소
     * @return 전송된 인증 번호
     */
    public String sendMail(String mail){
        MimeMessage message = createMail(mail);
        javaMailSender.send(message);

        return num;
    }

    /**
     * 랜덤한 인증번호 6자리 생성
     */
    private void createNum(){
        num = UUID.randomUUID().toString().substring(0, 6);
    }

    /**
     * 이메일 내용 작성
     * @param mail - 받을 사람 이메일 주소
     * @return 작성한 이메일 내용
     */
    private MimeMessage createMail(String mail){
        createNum();
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            message.setFrom(sender);
            message.setRecipients(RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>요청하신 인증번호입니다.</h3>";
            body += "<h1>" + num + "</h1>";
            body += "<h3>감사합니다.</h3>";
            message.setText(body, "UTF-8", "html");
        }catch (MessagingException e){
            e.printStackTrace();
            throw new MailException(MailErrorCode.FAIL_SEND_MAIL);
        }

        return message;
    }
}
