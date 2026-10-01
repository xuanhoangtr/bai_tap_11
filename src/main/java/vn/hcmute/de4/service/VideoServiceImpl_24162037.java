package vn.hcmute.de4.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.Video_24162037;
import vn.hcmute.de4.repository.InteractionRepository_24162037;
import vn.hcmute.de4.repository.VideoRepository_24162037;

@Service
public class VideoServiceImpl_24162037 implements VideoService_24162037 {
    private final VideoRepository_24162037 repository;
    private final InteractionRepository_24162037 interactionRepository;
    public VideoServiceImpl_24162037(VideoRepository_24162037 repository, InteractionRepository_24162037 interactionRepository) { this.repository = repository; this.interactionRepository = interactionRepository; }
    @Override public PageResult_24162037<Video_24162037> findPage(Long categoryId, int page, int size) { return repository.findPage(categoryId, Math.max(0, page), size); }
    @Override @Transactional public Optional<Video_24162037> findDetail(Long id, Long currentUserId) {
        Optional<Video_24162037> optional = repository.findById(id); if (optional.isEmpty()) return optional;
        repository.incrementViews(id); Video_24162037 video = repository.findById(id).orElse(optional.get());
        video.setLikeCount(repository.countLikes(id)); video.setShareCount(repository.countShares(id));
        video.setLiked(currentUserId != null && interactionRepository.isLiked(currentUserId, id)); return Optional.of(video);
    }
}
