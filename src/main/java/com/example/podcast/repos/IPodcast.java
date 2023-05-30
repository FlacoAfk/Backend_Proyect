package com.example.podcast.repos;

import com.example.podcast.entidades.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPodcast extends JpaRepository<Podcast, Long> {

    List<Podcast> findAllByClientId(long id);
}
