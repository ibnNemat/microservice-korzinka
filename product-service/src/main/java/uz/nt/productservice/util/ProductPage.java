package uz.nt.productservice.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPage<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductPage(
            @JsonProperty("content") List<T> content,
            @JsonProperty("number") Integer page,
            @JsonProperty("size") Integer size,
            @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
    }
}
