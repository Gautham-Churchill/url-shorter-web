package dev.gautham.urlshortener.domain.repositories;

import dev.gautham.urlshortener.domain.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    @Query("select s from ShortUrl s left join fetch s.createdBy where s.isPrivate is false order by s.createdAt desc")
    List<ShortUrl> findPublicShortUrls();
}
