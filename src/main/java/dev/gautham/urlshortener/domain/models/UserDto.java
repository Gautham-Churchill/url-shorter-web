package dev.gautham.urlshortener.domain.models;

import java.io.Serializable;

public record UserDto(Long id, String name) implements Serializable {
}
