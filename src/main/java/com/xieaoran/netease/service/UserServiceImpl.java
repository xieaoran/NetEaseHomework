package com.xieaoran.netease.service;

import com.xieaoran.netease.data.UserData;
import com.xieaoran.netease.enums.SessionKey;
import com.xieaoran.netease.enums.UserRole;
import com.xieaoran.netease.exception.AppException;
import com.xieaoran.netease.exception.ErrorCode;
import com.xieaoran.netease.persistence.entity.User;
import com.xieaoran.netease.persistence.repository.UserRepository;
import com.xieaoran.netease.request.user.LoginRequest;
import com.xieaoran.netease.translator.UserTranslator;
import com.xieaoran.netease.utils.Md5Helper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserData login(LoginRequest request, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Optional<User> userOptional = userRepository.findByLoginName(request.getLoginName());
        if (!userOptional.isPresent()) throw new AppException(ErrorCode.USER_NOT_EXIST);
        User user = userOptional.get();

        String challenge = Md5Helper.digest(request.getPassword() + user.getSalt());
        if (user.getPasswordHash().equals(challenge)) {
            session.setAttribute(SessionKey.USER_ID.getKey(), user);
            return UserTranslator.translate(user);
        } else throw new AppException(ErrorCode.USER_PASSWORD_INCORRECT);
    }

    @Override
    public UserData logout(HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        session.setAttribute(SessionKey.USER_ID.getKey(), null);
        return UserTranslator.translate(user);
    }

    @Override
    public UserData current(HttpSession session) {
        Object userObj = session.getAttribute(SessionKey.USER_ID.getKey());
        if (null == userObj) throw new AppException(ErrorCode.USER_NOT_LOGGED_IN);
        User user = (User) userObj;
        return UserTranslator.translate(user);
    }

    @Override
    @Transactional
    public Collection<UserData> addUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Optional<User> buyerOptional = userRepository.findByLoginName("buyer");
        if (buyerOptional.isPresent()) throw new AppException(ErrorCode.USER_ALREADY_ADDED);
        Optional<User> sellerOptional = userRepository.findByLoginName("seller");
        if (sellerOptional.isPresent()) throw new AppException(ErrorCode.USER_ALREADY_ADDED);

        User buyer = new User();
        buyer.setLoginName("buyer");
        buyer.setNickName("buyer");
        buyer.setRole(UserRole.BUYER);
        buyer.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        buyer.setPasswordHash(Md5Helper.digest("reyub" + buyer.getSalt()));

        User seller = new User();
        seller.setLoginName("seller");
        seller.setNickName("seller");
        seller.setRole(UserRole.SELLER);
        seller.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        seller.setPasswordHash(Md5Helper.digest("relles" + seller.getSalt()));

        userRepository.save(buyer);
        userRepository.save(seller);

        Collection<UserData> data = new ArrayList<>();
        data.add(UserTranslator.translate(buyer));
        data.add(UserTranslator.translate(seller));

        return data;
    }
}
