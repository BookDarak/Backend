package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public BaseResponse<UserRes.SignupRes> signup(UserReq.SignupReq request){
        if (userRepository.existsByEmail(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.DUPLICATED_EMAIL);
        }
        User user = userRepository.save(
                new User(request.getEmail(),request.getPassword(),request.getName(),request.getAge(),request.getIntroduction(),request.getProfile_url()));
        return new BaseResponse<>(new UserRes.SignupRes(user.getId()));
    }
}
