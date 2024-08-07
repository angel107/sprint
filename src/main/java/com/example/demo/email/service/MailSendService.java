package com.example.demo.email.service;

import com.example.demo.email.dto.EmailCheckDto;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSendService {

  @Value("${spring.mail.username}")
  private String setFrom;

  private final JavaMailSender mailSender;
  //private final RedisTemplate<String, Object> redisTemplate;

  private int authNumber;
  //private ValueOperations<String, Object> valueOperations;

  @PostConstruct
  public void init() {
    //this.valueOperations = redisTemplate.opsForValue();
  }

  public void makeRandomNumber() {
    Random r = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      sb.append(r.nextInt(10));
    }

    authNumber = Integer.parseInt(sb.toString());
  }

  public String joinEmail(String email) {
    if (Objects.isNull(email)) {
      log.error("[MailSendService joinEmail] email should not be null.");
      return null;
    }

    makeRandomNumber();
    String title = "회원 가입 인증 이메일 입니다.";
    StringBuilder sb = new StringBuilder();
    sb.append("Sprint를 방문해 주셔서 감사합니다.<br><br>인증번호는 ")
      .append(authNumber)
      .append("입니다.<br>인증번호를 제대로 입력해주세요");
    mailSend(setFrom, email, title, sb.toString());

    return Integer.toString(authNumber);
  }

  private void mailSend(String setFrom, String toMail, String title, String content) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
      helper.setFrom(setFrom);
      helper.setTo(toMail);
      helper.setSubject(title);
      helper.setText(content, true);
      mailSender.send(message);
//      valueOperations.set(toMail, Integer.toString(authNumber), 5, TimeUnit.MINUTES);
      log.info("Join email send success. email: {}, authNum: {}", toMail, authNumber);
    } catch (MessagingException e) {
      log.error("[MailSendService] Failed to send email: {}", e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean checkAuthNum(EmailCheckDto emailCheckDto) {
    if (Objects.isNull(emailCheckDto.getEmail())) {
      log.error("[MailSendService checkAuthNum] email should not be null.");
      return false;
    }

    try {
      String email = emailCheckDto.getEmail();
//      String storedAuthNum = (String) valueOperations.get(emailCheckDto.getEmail());
      String requestAuthNum = emailCheckDto.getAuthNum();
//      log.info("email: {}, storedAuthNum: {}, requestAuthNum: {}", email, storedAuthNum, requestAuthNum);
//      if (storedAuthNum.equals(requestAuthNum)) {
        return true;
//      }
    } catch (Exception e) {
      log.error("[MailSendService checkAuthNum] {}", e.getMessage());
    }

    return false;
  }
}
