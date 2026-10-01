package vn.hcmute.de4.model;

import java.time.LocalDateTime;

public class User_24162037 {
    private Long userId;
    private String username;
    private String password;
    private String phone;
    private String fullname;
    private String email;
    private boolean admin;
    private boolean active;
    private String images;
    private String otpCode;
    private LocalDateTime otpExpiresAt;

    public User_24162037() {}

    public User_24162037(Long userId, String username, String password, String phone, String fullname,
                         String email, boolean admin, boolean active, String images, String otpCode,
                         LocalDateTime otpExpiresAt) {
        this.userId = userId; this.username = username; this.password = password; this.phone = phone;
        this.fullname = fullname; this.email = email; this.admin = admin; this.active = active;
        this.images = images; this.otpCode = otpCode; this.otpExpiresAt = otpExpiresAt;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
    public LocalDateTime getOtpExpiresAt() { return otpExpiresAt; }
    public void setOtpExpiresAt(LocalDateTime otpExpiresAt) { this.otpExpiresAt = otpExpiresAt; }
}
