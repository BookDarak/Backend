package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.repository.BookmarkRepository;
import com.bookjeok.bookdarak.repository.FollowRepository;
import com.bookjeok.bookdarak.repository.ReviewRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    public BaseResponse<UserRes.Signup> signup(UserReq.Signup request){

        if (userRepository.existsByEmail(request.getEmail())) {
            return new BaseResponse<>(DUPLICATED_USER_EMAIL);
        }
        if (userRepository.existsByName(request.getName())){
            return new BaseResponse<>(DUPLICATED_USER_NAME);
        }
        User user = saveUser(request);

        return new BaseResponse<>(new UserRes.Signup(user.getId()));
    }

    public BaseResponse<UserRes.Login> login(UserReq.Login request){

        if (!userRepository.existsByEmail(request.getEmail())){
            return new BaseResponse<>(NOT_EXIST_EMAIL);
        }

        User user = userRepository.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            return new BaseResponse<>(NOT_CORRECT_PASSWORD);

        // 로그인 성공 시 토큰 값을 DTO에 담아서 응답해줌
        String jwTokenString = tokenService.createToken(user.getId().toString()).getJwTokenString();
        return new BaseResponse<>(new UserRes.Login(user.getId(),jwTokenString));
    }

    public BaseResponse<String> deleteUser(Long id){
        if (!userRepository.existsById(id)){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        //탈퇴 유저와 관련된 리뷰, 북마크, 팔로우 삭제
        deleteRelatedEntity(id);

        userRepository.deleteById(id);
        return new BaseResponse<>("회원탈퇴가 완료되었습니다.");
    }

    public BaseResponse<UserRes.UserInfo> getUserInfo(Long id) {
        User user = findUser(id);
        if (user==null){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        Long reviewCount = reviewRepository.countByUser(user);
        Long bookmarkCount = bookmarkRepository.countByUser(user);
        Long followCount = followRepository.countByFollowerUser(user);

        return new BaseResponse<>(new UserRes.UserInfo(user, reviewCount, bookmarkCount, followCount));
    }

    public BaseResponse<BaseResponseStatus> editUserInfo(Long id, UserReq.UpdateUserInfo userInfo) {
        User user = findUser(id);
        if (user==null){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }

        if (userRepository.existsByName(userInfo.getName())){
            return new BaseResponse<>(DUPLICATED_USER_NAME);
        }
        user.updateUserInfo(userInfo);
        return new BaseResponse<>(UPDATE_SUCCESS);
    }

    public BaseResponse<String> changePassword(UserReq.ChangePw request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user==null){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        if (passwordEncoder.matches(request.getOldPw(), user.getPassword())){
            user.changeUserPw(passwordEncoder.encode(request.getNewPw()));
        } else {
            return new BaseResponse<>(INCORRECT_OLD_PW);
        }
        return new BaseResponse<>(SUCCESS);
    }

    public BaseResponse<String> mailTempPassword(UserReq.MailTmpPw request){
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user==null){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        if (!request.getEmail().equals(user.getEmail())) {
            return new BaseResponse<>(INCORRECT_EMAIL);
        }

        String tmpPassword = getTmpPassword();
        user.changeUserPw(passwordEncoder.encode(tmpPassword));


        sendTmpPwd(user.getEmail(), tmpPassword);
        return new BaseResponse<>(SUCCESS);
    }

    private void deleteRelatedEntity(Long id) {
        //유저가 작성한 리뷰 삭제
        User user = findUser(id);
        reviewRepository.deleteReviewsByUser(user);

        //유저의 북마크 삭제
        bookmarkRepository.deleteBookmarksByUser(user);

        //유저의 팔로우 삭제
        followRepository.deleteFollowByFollowerUser(user);
        followRepository.deleteFollowByFolloweeUser(user);
    }

    private User saveUser(UserReq.Signup request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .introduction(request.getIntroduction())
                .profileUrl(request.getProfile_url()).build());
    }

    public User findUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        StringBuilder pwd = new StringBuilder();

        /* 문자 배열 길이의 값을 랜덤으로 12개를 뽑아 조합 */

        for(int i = 0; i < 12; i++){
            int ind = (int) (charSet.length * Math.random());
            pwd.append(charSet[ind]);
        }
        return pwd.toString();
    }

    private void sendTmpPwd(String email, String tmpPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(from);
        message.setSubject("[북다락] 임시 비밀번호가 발급되었습니다.");
        message.setText("임시 비밀번호는 "+ tmpPassword + "입니다.");

        javaMailSender.send(message);
    }

}