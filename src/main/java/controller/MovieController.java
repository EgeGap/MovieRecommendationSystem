package com.ege.app.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ege.app.rest.model.Movie;
import com.ege.app.rest.services.MovieRecommendationService;
import com.ege.app.rest.services.MovieService;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRecommendationService recommendationService;

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @PostMapping("/movieSelection")
    public String processMovieSelection(@RequestParam String userEmail,
            @RequestParam int filmCount,
            @RequestParam(value = "filmNames", required = false) String[] filmNames,
            Model model) {
        Movie movie = new Movie();
        movie.setUserEmail(userEmail);
        movie.setFilmCount(filmCount);
        movie.setFilmNames(filmNames);

        movieService.saveMovieSelection(movie);

        String recommendedMovie = recommendationService.recommendMovie(filmNames);
        model.addAttribute("recommendedMovie", recommendedMovie);

        return "redirect:/result?filmNames=" + String.join(",", filmNames);
    }

    @GetMapping("/result")
    public String showResultPage(@RequestParam(value = "filmNames") String[] filmNames, Model model) {
        String recommendedMovie = recommendationService.recommendMovie(filmNames);

        StringBuilder promptBuilder = new StringBuilder();
        for (String filmName : filmNames) {
            promptBuilder.append(filmName).append(", ");
        }
        promptBuilder.delete(promptBuilder.length() - 2, promptBuilder.length());
        model.addAttribute("recommendedMovie", recommendedMovie);

        return "result";
    }

}