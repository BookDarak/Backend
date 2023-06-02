package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BaseResponse<UserRes.UserIdRes> signup(UserReq.SignupReq request){

        if (userRepository.existsByEmail(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.DUPLICATED_USER_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //유저 저장
        User user = userRepository.save(
                new User(request.getEmail(),encodedPassword,request.getName(),request.getAge(),request.getIntroduction(),request.getProfile_url()));
        return new BaseResponse<>(new UserRes.UserIdRes(user.getId()));
    }

    public BaseResponse<UserRes.UserIdRes> login(UserReq.LoginReq request){

        if (!userRepository.existsByEmail(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_EMAIL);
        }

        User user = userRepository.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            return new BaseResponse<>(BaseResponseStatus.NOT_CORRECT_PASSWORD);

        return new BaseResponse<>(new UserRes.UserIdRes(user.getId()));
    }

    public BaseResponse<UserRes.UserIdRes> deleteUser(Long id){
        if (!userRepository.existsById(id)){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_USER_ID);
        }
        userRepository.deleteById(id);
        return new BaseResponse<>(new UserRes.UserIdRes(id));
    }

    /**이메일 형식 검사 **/
    public boolean isValidEmailFormat(String email){
        boolean validation = false;

        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches())
            validation = true;

        return validation;
    }
    /**비밀번호 형식 검사 **/
    public boolean isValidPasswordFormat(String password){
        boolean validation = false;
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (m.matches())
            validation = true;

        return validation;
    }
}
