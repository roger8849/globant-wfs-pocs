package com.globant.testing.framework.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HateOasResource<T> {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_embedded")
    public Map<String, List<Resource<T>>> embedded = new HashMap<>();

    @JsonProperty("_links")
    public Map<String, Map<String, String>> links;

    @JsonProperty("page")
    public Page page;

}

@JsonInclude(JsonInclude.Include.NON_NULL)
class Page {

    @JsonProperty("size")
    public Integer size;
    @JsonProperty("totalElements")
    public Integer totalElements;
    @JsonProperty("totalPages")
    public Integer totalPages;
    @JsonProperty("number")
    public Integer number;

}