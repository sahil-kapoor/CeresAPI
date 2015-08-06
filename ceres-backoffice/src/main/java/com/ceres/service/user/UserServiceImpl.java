package com.ceres.service.user;

import com.ceres.domain.entity.human.Human;
import com.ceres.domain.entity.human.SystemUser;
import com.ceres.domain.entity.human.UserCategory;
import com.ceres.domain.repository.human.HumanRepository;
import com.ceres.domain.repository.human.UserRepository;
import com.ceres.service.user.exception.AuthenticationException;
import com.ceres.service.user.exception.AuthenticationExceptionMessages;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
@Service(value = "UserService")
@EnableCaching
public final class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private HumanRepository humanRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository, HumanRepository humanRepository) {
        this.userRepository = userRepository;
        this.humanRepository = humanRepository;
    }

    @Override
    public SystemUser findUserByToken(String token) {
        SystemUser user = decodeTokenToUser(token);
        return userRepository.findByUsername(user.getUsername());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.countByUsername(username) > 0;
    }

    @Override
    public String validateAndGetToken(String username, String password) throws AuthenticationException {
        validate(username, password);
        return encodeUserToToken(username, password);
    }

    @Override
    public void validate(String token) throws AuthenticationException {
        SystemUser stranger;

        try {
            stranger = decodeTokenToUser(token);
        } catch (RuntimeException e) {
            throw new AuthenticationException(AuthenticationExceptionMessages.INVALID_TOKEN.create());
        }

        validate(stranger.getUsername(), stranger.getPassword());
    }

    private void validate(String username, String password) throws AuthenticationException {
        if (userRepository.countByPassword(hashSHA256(password, username)) == 0) {
            throw new AuthenticationException(AuthenticationExceptionMessages.INVALID_TOKEN.create());
        }
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.exists(id);
    }

    @Override
    public boolean humanExists(Long id) {
        return humanRepository.exists(id);
    }

    @Override
    public Human getHumanById(Long id) {
        return humanRepository.findOne(id);
    }

    @Override
    public SystemUser getById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<SystemUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public SystemUser update(SystemUser user) {
        validateUser(user);
        return userRepository.save(user);
    }

    @Override
    public SystemUser create(SystemUser user) {
        validateUser(user);
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.delete(id);
    }

    private void validateUser(SystemUser user) {
        user.setRole(UserCategory.getCategoryOf(user.getHuman()));
        user.setPassword(hashSHA256(user.getPassword(), user.getUsername()));
    }

    private SystemUser decodeTokenToUser(String auth) {
        String authToken = auth.replace("Basic ", "");
        byte[] decoded = Base64.decodeBase64(authToken);
        List<String> credentials = Arrays.asList(new String(decoded, Charset.forName("UTF-8")).split(":"));
        return SystemUser.createUser(credentials.get(0), credentials.get(1));
    }

    private String encodeUserToToken(String username, String password) {
        String authToken = "Basic ";
        String tokenContext = username.concat(":").concat(password);
        byte[] encodedBytes = Base64.encodeBase64(tokenContext.getBytes());
        return authToken.concat(new String(encodedBytes));
    }

    private static String hashSHA256(String password, String salt) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // TODO THROWS
        }
        messageDigest.reset();
        messageDigest.update(salt.getBytes());
        return bytesToHex(messageDigest.digest(password.getBytes()));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

}
