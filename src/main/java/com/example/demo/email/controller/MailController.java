package com.example.demo.email.controller;

import com.example.demo.email.dto.EmailCheckDto;
import com.example.demo.email.dto.EmailRequestDto;
import com.example.demo.email.service.MailSendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailController {

  private final MailSendService mailSendService;

  @PostMapping("/sendJoinMail")
  public ResponseEntity<String> sendJoinMail(@RequestBody @Valid EmailRequestDto emailRequestDto) {
    String authNum = mailSendService.joinEmail(emailRequestDto.getEmail());
    if (authNum.isEmpty()) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail to send email.");
    }
    return ResponseEntity.ok(authNum);
  }

  @PostMapping("/authCheck")
  public ResponseEntity<String> authenticateEmail(@RequestBody @Valid EmailCheckDto emailCheckDto) {
    if (mailSendService.checkAuthNum(emailCheckDto)) {
      return ResponseEntity.ok("Email authentication success.");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email authentication failed.");
  }
}
