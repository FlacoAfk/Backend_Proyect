package com.example.podcast.servicios;

import com.example.podcast.entidades.Podcast;
import com.example.podcast.entidades.UserRecord;
import com.example.podcast.repos.IPodcast;
import com.example.podcast.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PodcastService {

    private final IPodcast podcastRepository;
    private final UserRepository userRepository;

    public PodcastService(IPodcast podcastRepository, UserRepository userRepository) {
        this.podcastRepository = podcastRepository;
        this.userRepository = userRepository;
    }

    public List<Podcast> findAllPodcastRecordsById(long id) {
        log.info("Inside PodcastService::findAllPodcastRecordsById id: {} ", id);
        List<Podcast> podcasts = podcastRepository.findAllByClientId(id);
        return podcasts;
    }

    public List<Podcast> findAll() {
        log.info("Inside PodcastService::findAll");
        return podcastRepository.findAll();
    }

    public String uploadPodcast(long id, MultipartFile file, String name, String description) throws IOException {
        log.info("Inside PodcastService::uploadPodcast file: {} ", file.getOriginalFilename());
        UserRecord user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Podcast podcast = new Podcast();
        podcast.setFile_name(file.getOriginalFilename());
        podcast.setArchivo(file.getBytes());
        podcast.setFechaCreacion(LocalDateTime.now());
        podcast.setClientId(id);
        podcast.setTitle(name);
        podcast.setDescription(description);
        podcastRepository.save(podcast);
        userRepository.save(user);
        return "Podcast uploaded";
    }

    public String updatePodcast(long id, MultipartFile file) throws IOException {
        log.info("Inside PodcastService::updatePodcast id: {} ", id);
        Optional<Podcast> podcastOptional = podcastRepository.findById(id);
        if (podcastOptional.isPresent()) {
            Podcast podcast = podcastOptional.get();
            podcast.setFile_name(file.getOriginalFilename());
            podcast.setArchivo(file.getBytes());
            podcast.setFechaCreacion(LocalDateTime.now());
            podcastRepository.save(podcast);
            return "Podcast updated";
        } else {
            return "Podcast not found";
        }
    }

    public ResponseEntity<byte[]> listenPodcast(long podcastId) {
        log.info("Inside PodcastService::listenPodcast podcastId: {} ", podcastId);
        Optional<Podcast> podcastOptional = podcastRepository.findById(podcastId);
        if (podcastOptional.isPresent()) {
            Podcast podcast = podcastOptional.get();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename(podcast.getFile_name()).build());

            return new ResponseEntity<>(podcast.getArchivo(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
