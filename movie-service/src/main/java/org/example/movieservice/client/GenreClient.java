package org.example.movieservice.client;

import org.example.movieservice.dto.GenreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "genre-service", path = "/api/genres")
public interface GenreClient {
    @GetMapping("/{id}")
    GenreDto getGenreById(@PathVariable("id") Long id);
}
