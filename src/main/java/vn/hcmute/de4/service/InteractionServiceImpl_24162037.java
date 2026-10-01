package vn.hcmute.de4.service;

import org.springframework.stereotype.Service;
import vn.hcmute.de4.repository.InteractionRepository_24162037;

@Service
public class InteractionServiceImpl_24162037 implements InteractionService_24162037 {
    private final InteractionRepository_24162037 repository;
    public InteractionServiceImpl_24162037(InteractionRepository_24162037 repository) { this.repository = repository; }
    @Override public boolean toggleLike(Long userId, Long videoId) { if (repository.isLiked(userId, videoId)) { repository.removeFavorite(userId, videoId); return false; } repository.addFavorite(userId, videoId); return true; }
    @Override public void share(Long userId, Long videoId, String email) { repository.addShare(userId, videoId, email); }
}
