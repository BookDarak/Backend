package com.bookjeok.bookdarak.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserReq {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Signup {
        @NotBlank(message="이메일을 입력해주세요.")
        @Email(message="이메일 형식을 확인해주세요.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[`~!@$!%*#^?&\\(\\)\\-_=+])(?!.*[^a-zA-z0-9`~!@$!%*#^?&\\(\\)\\-_=+]).{8,16}$",
                message = "비밀번호는 숫자, 영문, 특수문자를 포함해서 8자~16자 이내로 설정해주세요.")
        private String password;

        @NotBlank(message="닉네임을 입력해주세요.")
        private String name;

        @NotBlank(message="성별을 입력해주세요.")
        @Pattern(regexp = "^[FM]$", message = "성별 형식이 올바르지 않습니다.")
        private String gender;

        @NotNull(message="나이를 입력해주세요.")
        private Integer age;


        @NotBlank(message="자기소개를 입력해주세요.")
        private String introduction;

        private String profile_url;
    }
    @Getter @AllArgsConstructor
    public static class Login {
        @NotBlank(message="이메일을 입력해주세요.")
        @Email(message="이메일 형식을 확인해주세요.")
        private String email;

        @NotBlank(message="비밀번호를 입력해주세요.")
        private String password;
    }

    @Getter @AllArgsConstructor
    public static class UpdateUserInfo {
        private String name;
        private String introduction;
        private Integer age;
    }

    @Getter @AllArgsConstructor
    public static class ChangePw{
        private Long userId;
        private String oldPw;
        @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[`~!@$!%*#^?&\\(\\)\\-_=+])(?!.*[^a-zA-z0-9`~!@$!%*#^?&\\(\\)\\-_=+]).{8,16}$",
                message = "비밀번호는 숫자, 영문, 특수문자를 포함해서 8자~16자 이내로 설정해주세요.")
        private String newPw;
    }

    @Getter @AllArgsConstructor
    public static class MailTmpPw {
        private Long userId;

        @NotBlank(message="이메일을 입력해주세요.")
        @Email(message="이메일 형식을 확인해주세요.")
        private String email;
    }
}
