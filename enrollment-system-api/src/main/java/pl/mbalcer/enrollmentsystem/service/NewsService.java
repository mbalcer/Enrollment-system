package pl.mbalcer.enrollmentsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbalcer.enrollmentsystem.errors.BadRequestException;
import pl.mbalcer.enrollmentsystem.model.News;
import pl.mbalcer.enrollmentsystem.model.dto.NewsDTO;
import pl.mbalcer.enrollmentsystem.repository.NewsRepository;
import pl.mbalcer.enrollmentsystem.service.mapper.NewsMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewsService implements CrudService<NewsDTO> {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    public List<NewsDTO> findAll() {
        log.debug("Request to get all News");
        return newsRepository.findAll()
                .stream()
                .map(newsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NewsDTO> findOne(Long id) {
        log.debug("Request to get News by id: {}", id);
        return newsRepository.findById(id).map(newsMapper::toDto);
    }

    public List<NewsDTO> findLatestNews(Integer limit) {
        log.debug("Request to get {} latest news", limit);
        return newsRepository.findAll()
                .stream()
                .sorted(Comparator.nullsLast((n1, n2) -> n2.getTimeOfPublication().compareTo(n1.getTimeOfPublication())))
                .limit(limit)
                .map(newsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDTO create(NewsDTO dto) {
        log.debug("Request to create News: {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestException("A new news cannot already have an ID");
        }

        News news = newsMapper.toEntity(dto);
        news = newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    @Override
    public NewsDTO update(NewsDTO dto, Long id) {
        log.debug("Request to update News: {}", dto);
        if (id == null || !newsRepository.existsById(id)) {
            throw new BadRequestException("Invalid id");
        }

        dto.setId(id);
        News news = newsMapper.toEntity(dto);
        news = newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete News by id: {}", id);
        Optional<News> newsById = newsRepository.findById(id);
        if (newsById.isEmpty())
            throw new BadRequestException("Invalid id");
        newsRepository.deleteById(id);
    }
}
