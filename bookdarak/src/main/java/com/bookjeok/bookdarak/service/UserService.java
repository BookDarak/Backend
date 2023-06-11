package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    public BaseResponse<UserRes.UserId> signup(UserReq.Signup request){

        if (userRepository.existsByEmail(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.DUPLICATED_USER_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = userRepository.save(User.builder()
                            .email(request.getEmail())
                            .password(encodedPassword)
                            .name(request.getName())
                            .age(request.getAge())
                            .introduction(request.getIntroduction())
                            .profileUrl(request.getProfile_url()).build());


        return new BaseResponse<>(new UserRes.UserId(user.getId()));
    }

    public BaseResponse<UserRes.UserId> login(UserReq.Login request){

        if (!userRepository.existsByEmail(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_EMAIL);
        }

        User user = userRepository.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            return new BaseResponse<>(BaseResponseStatus.NOT_CORRECT_PASSWORD);

        return new BaseResponse<>(new UserRes.UserId(user.getId()));
    }

    public BaseResponse<String> deleteUser(Long id){
        if (!userRepository.existsById(id)){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_USER_ID);
        }
        //유저가 작성한 리뷰 삭제
        User user = userRepository.findById(id).orElseThrow();
        if (reviewRepository.existsByUser(user)){
            reviewRepository.deleteReviewsByUser(user);
        }

        userRepository.deleteById(id);
        return new BaseResponse<>("회원탈퇴가 완료되었습니다.");
    }

    /**이메일 형식 검사 **/
    public boolean isInValidEmailFormat(String email){
        boolean validation = false;

        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches())
            validation = true;

        return !validation;
    }
    /**비밀번호 형식 검사 **/
    public boolean isInValidPasswordFormat(String password){
        boolean validation = false;
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (m.matches())
            validation = true;

        return !validation;
    }
}
