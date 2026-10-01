package vn.hcmute.de4.service;

import java.util.Optional;
import vn.hcmute.de4.model.PageResult_24162037;
import vn.hcmute.de4.model.Video_24162037;

public interface VideoService_24162037 {
    PageResult_24162037<Video_24162037> findPage(Long categoryId, int page, int size);
    Optional<Video_24162037> findDetail(Long id, Long currentUserId);
}
