package org.example.genreservice;

import lombok.RequiredArgsConstructor;
import org.example.genreservice.model.Genre;
import org.example.genreservice.repository.GenreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDataInitializer implements CommandLineRunner {
    private final GenreRepository genreRepository;

    @Override
    public void run(String... args) {
        if (genreRepository.count() == 0) {
            Genre g1 = Genre.builder()
                    .name("Action")
                    .description("Phim hanh dong voi nhung pha hanh dong kich tinh")
                    .build();
            Genre g2 = Genre.builder()
                    .name("Sci-Fi")
                    .description("Phim khoa hoc vien tuong va kham pha vu tru")
                    .build();
            genreRepository.saveAll(List.of(g1, g2));
        }
    }
}
