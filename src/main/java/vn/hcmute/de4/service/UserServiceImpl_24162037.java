package vn.hcmute.de4.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.User_24162037;
import vn.hcmute.de4.repository.UserRepository_24162037;

@Service
public class UserServiceImpl_24162037 implements UserService_24162037 {
    private final UserRepository_24162037 repository;
    private final PasswordEncoder passwordEncoder;
    private final MailService_24162037 mailService;

    public UserServiceImpl_24162037(UserRepository_24162037 repository, PasswordEncoder passwordEncoder, MailService_24162037 mailService) {
        this.repository = repository; this.passwordEncoder = passwordEncoder; this.mailService = mailService;
    }
    @Override public Optional<User_24162037> findById(Long id) { return repository.findById(id); }
    @Override public Optional<User_24162037> findByUsername(String username) { return repository.findByUsername(username); }
    @Override public PageResult_24162037<User_24162037> findPage(int page, int size) { return repository.findPage(page, size); }
    @Override public User_24162037 register(User_24162037 user) {
        if (repository.findByUsername(user.getUsername()).isPresent() || repository.findByEmail(user.getEmail()).isPresent()) throw new IllegalArgumentException("Tên đăng nhập hoặc email đã tồn tại");
        user.setPassword(passwordEncoder.encode(user.getPassword())); user.setAdmin(false); user.setActive(false); user.setImages("avatar.svg");
        user.setOtpCode(String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000)));
        user.setOtpExpiresAt(LocalDateTime.now().plusMinutes(5));
        User_24162037 saved = repository.insert(user);
        if (!mailService.sendOtp(saved.getEmail(), saved.getOtpCode())) { repository.deleteById(saved.getUserId()); throw new IllegalStateException("Không thể gửi email OTP. Hãy kiểm tra SMTP_EMAIL và SMTP_PASSWORD."); }
        return saved;
    }
    @Override public boolean verifyOtp(String username, String otp) {
        Optional<User_24162037> optional = repository.findByUsername(username);
        if (optional.isEmpty()) return false;
        User_24162037 user = optional.get();
        if (user.getOtpCode() == null || !user.getOtpCode().equals(otp) || user.getOtpExpiresAt() == null || user.getOtpExpiresAt().isBefore(LocalDateTime.now())) return false;
        user.setActive(true); user.setOtpCode(null); user.setOtpExpiresAt(null); repository.update(user); return true;
    }
    @Override public Optional<User_24162037> authenticate(String username, String password) {
        Optional<User_24162037> optional = repository.findByUsername(username);
        if (optional.isPresent() && optional.get().isActive() && passwordEncoder.matches(password, optional.get().getPassword())) return optional;
        return Optional.empty();
    }
    @Override public User_24162037 saveAdmin(User_24162037 user, boolean newUser) {
        if (newUser) {
            if (repository.findByUsername(user.getUsername()).isPresent() || repository.findByEmail(user.getEmail()).isPresent()) throw new IllegalArgumentException("Tên đăng nhập hoặc email đã tồn tại");
            user.setPassword(passwordEncoder.encode(user.getPassword())); user.setImages("avatar.svg"); user.setActive(true); return repository.insert(user);
        }
        User_24162037 old = repository.findById(user.getUserId()).orElseThrow();
        if (user.getPassword() == null || user.getPassword().isBlank()) user.setPassword(old.getPassword()); else user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getImages() == null || user.getImages().isBlank()) user.setImages(old.getImages());
        repository.update(user); return user;
    }
    @Override public void delete(Long id) { repository.deleteById(id); }
}
