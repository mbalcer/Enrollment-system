package pl.mbalcer.enrollmentsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbalcer.enrollmentsystem.model.dto.NewsDTO;
import pl.mbalcer.enrollmentsystem.service.NewsService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/news")
@Slf4j
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        log.debug("REST request to get all News");
        List<NewsDTO> all = newsService.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/latest/{limit}")
    public ResponseEntity<List<NewsDTO>> getLatestNews(@PathVariable Integer limit) {
        log.debug("REST request to get {} latest News", limit);
        List<NewsDTO> latest = newsService.findLatestNews(limit);
        return ResponseEntity.ok().body(latest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
        log.debug("REST request to get News : {}", id);
        Optional<NewsDTO> newsDTO = newsService.findOne(id);
        if (newsDTO.isPresent()) return ResponseEntity.ok().body(newsDTO.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO) throws URISyntaxException {
        log.debug("REST request to save News : {}", newsDTO);
        NewsDTO result = newsService.create(newsDTO);
        return ResponseEntity.created(new URI("/api/news/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@RequestBody NewsDTO newsDTO, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update News : {}", newsDTO);
        NewsDTO result = newsService.update(newsDTO, id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        log.debug("REST request to delete News : {}", id);
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
