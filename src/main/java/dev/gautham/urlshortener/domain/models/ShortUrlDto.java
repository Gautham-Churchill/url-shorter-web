package dev.gautham.urlshortener.domain.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ShortUrlDto(Long id, String shortKey, String originalUrl, Boolean isPrivate, LocalDateTime expiresAt,
                          UserDto createdBy, Long clickCount, LocalDateTime createdAt) implements Serializable {
}
