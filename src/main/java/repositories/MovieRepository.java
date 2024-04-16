package com.ege.app.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ege.app.rest.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
