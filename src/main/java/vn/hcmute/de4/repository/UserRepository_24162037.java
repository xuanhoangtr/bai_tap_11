package vn.hcmute.de4.repository;

import java.util.Optional;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.User_24162037;

public interface UserRepository_24162037 {
    Optional<User_24162037> findById(Long id);
    Optional<User_24162037> findByUsername(String username);
    Optional<User_24162037> findByEmail(String email);
    PageResult_24162037<User_24162037> findPage(int page, int size);
    User_24162037 insert(User_24162037 user);
    void update(User_24162037 user);
    void deleteById(Long id);
    long count();
}
