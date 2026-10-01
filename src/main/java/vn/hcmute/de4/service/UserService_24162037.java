package vn.hcmute.de4.service;

import java.util.Optional;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.User_24162037;

public interface UserService_24162037 {
    Optional<User_24162037> findById(Long id);
    Optional<User_24162037> findByUsername(String username);
    PageResult_24162037<User_24162037> findPage(int page, int size);
    User_24162037 register(User_24162037 user);
    boolean verifyOtp(String username, String otp);
    Optional<User_24162037> authenticate(String username, String password);
    User_24162037 saveAdmin(User_24162037 user, boolean newUser);
    void delete(Long id);
}
