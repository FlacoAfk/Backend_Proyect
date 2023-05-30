package com.example.podcast.controladores;

import com.example.podcast.entidades.Podcast;
import com.example.podcast.servicios.PodcastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/podcasts")
public class PodcastController {

    private final PodcastService podcastService;

    public PodcastController(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @PostMapping("/{id}")
    public String uploadPodcast(@PathVariable long id ,@RequestParam("file")MultipartFile file,@RequestParam("name") String name,@RequestParam("description") String description) throws IOException {
        return podcastService.uploadPodcast(id ,file, name, description);
    }

    @GetMapping
    public List<Podcast> findAll(){
        return podcastService.findAll();
    }


    @GetMapping("/podcast/{id}")
    public ResponseEntity<byte[]> listenPodcast(@PathVariable long id) {
        return podcastService.listenPodcast(id);
    }

    @GetMapping("/podcast/user/{id}")
    public List<Podcast> findAllPodcast(@PathVariable long id) {
        return podcastService.findAllPodcastRecordsById(id);
    }

    @PutMapping("/update/podcast/{id}")
    public String updatePodcast(@PathVariable long id, @RequestParam("file")MultipartFile file) throws IOException {
        return podcastService.updatePodcast(id, file);
    }

}
