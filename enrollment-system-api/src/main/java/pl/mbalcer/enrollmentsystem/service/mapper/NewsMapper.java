package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.News;
import pl.mbalcer.enrollmentsystem.model.dto.NewsDTO;

@Mapper(componentModel = "spring", uses = {})
public interface NewsMapper extends EntityMapper<NewsDTO, News> {

    @Mapping(source = "title", target = "title")
    News toEntity(NewsDTO dto);

    NewsDTO toDto(News entity);
}
