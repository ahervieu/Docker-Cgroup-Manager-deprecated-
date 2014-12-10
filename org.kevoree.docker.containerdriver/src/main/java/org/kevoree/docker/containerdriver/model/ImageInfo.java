package org.kevoree.docker.containerdriver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by leiko on 22/05/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageInfo {

    @NotNull
    @JsonProperty("description")
    private String description = "";

    @JsonProperty("is_official")
    private boolean isOfficial = false;

    @JsonProperty("is_trusted")
    private boolean isTrusted = false;

    @NotNull
    @JsonProperty("name")
    private String name = "";

    @JsonProperty("star_count")
    private int starCount = 0;

    @NotNull
    @Override
    public String toString() {
        return "ImageInfo {description="+description+", isOfficial="+isOfficial+", isTrusted="+isTrusted+", name="+name+", startCount="+starCount+"}";
    }
}
