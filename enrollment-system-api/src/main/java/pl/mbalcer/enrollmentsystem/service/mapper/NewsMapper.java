package pl.mbalcer.enrollmentsystem.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.mbalcer.enrollmentsystem.model.News;
import pl.mbalcer.enrollmentsystem.model.dto.NewsDTO;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NewsMapper extends EntityMapper<NewsDTO, News> {

    @Mapping(target = "author", ignore = true)
    News toEntity(NewsDTO dto);

    @Mapping(source = "author", target = "author")
    NewsDTO toDto(News entity);
}
