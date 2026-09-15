package org.example.movieservice.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.movieservice.client.GenreClient;
import org.example.movieservice.dto.GenreDto;
import org.example.movieservice.dto.MovieRequest;
import org.example.movieservice.exception.GenreNotFoundException;
import org.example.movieservice.model.Movie;
import org.example.movieservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreClient genreClient;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie createMovie(MovieRequest request) {
        GenreDto genreDto = null;
        try {
            genreDto = genreClient.getGenreById(request.getGenreId());
        } catch (FeignException.NotFound ex) {
            throw new GenreNotFoundException("Genre not found with id: " + request.getGenreId());
        } catch (Exception ex) {
            throw new GenreNotFoundException("Genre not found with id: " + request.getGenreId());
        }

        if (genreDto == null) {
            throw new GenreNotFoundException("Genre not found with id: " + request.getGenreId());
        }

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .director(request.getDirector())
                .duration(request.getDuration())
                .genreId(request.getGenreId())
                .ticketPrice(request.getTicketPrice())
                .build();

        return movieRepository.save(movie);
    }
}
