package com.example.demo.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {

  @Email
  @NotEmpty(message = "이메일을 입력해 주세요")
  private String email;
}
