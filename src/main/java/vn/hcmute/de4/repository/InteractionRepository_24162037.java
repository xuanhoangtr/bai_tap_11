package vn.hcmute.de4.repository;

public interface InteractionRepository_24162037 {
    boolean isLiked(Long userId, Long videoId);
    void addFavorite(Long userId, Long videoId);
    void removeFavorite(Long userId, Long videoId);
    void addShare(Long userId, Long videoId, String email);
}
