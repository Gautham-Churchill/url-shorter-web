package dev.gautham.urlshortener.domain.service;

import dev.gautham.urlshortener.domain.models.ShortUrlDto;
import dev.gautham.urlshortener.domain.repositories.ShortUrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final EntityMapper entityMapper;

    public List<ShortUrlDto> findAllPublicShortUrls() {
        return shortUrlRepository.findPublicShortUrls()
                .stream()
                .map(entityMapper::toShortUrlDto)
                .toList();
    }
}
