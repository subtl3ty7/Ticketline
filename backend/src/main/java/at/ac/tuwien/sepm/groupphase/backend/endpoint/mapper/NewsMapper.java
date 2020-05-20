package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface NewsMapper {
    @Named("newsToNewsDto")
    NewsDto newsToNewsDto(News news);

    @IterableMapping(qualifiedByName = "newsToNewsDto")
    List<NewsDto> newsToNewsDto(List<News> news);

    News newsDtoToNews(NewsDto newsDto);
}
