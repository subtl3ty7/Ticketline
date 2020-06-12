package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Image;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(imports = Image.class)
public interface NewsMapper {
    @Named("newsToNewsDto")
    @Mapping(expression = "java(news.getPhoto().getImage())", target = "photo")
    NewsDto newsToNewsDto(News news);

    @IterableMapping(qualifiedByName = "newsToNewsDto")
    List<NewsDto> newsToNewsDto(List<News> news);

    @Mapping(expression = "java(new Image(null, news.getPhoto()))", target = "photo")
    News newsDtoToNews(NewsDto news);

    @Named("newsToSimpleNewsDto")
    SimpleNewsDto newsToSimpleNewsDto(News news);

    @IterableMapping(qualifiedByName = "newsToSimpleNewsDto")
    List<SimpleNewsDto> newsToSimpleNewsDto(List<News> news);
}
