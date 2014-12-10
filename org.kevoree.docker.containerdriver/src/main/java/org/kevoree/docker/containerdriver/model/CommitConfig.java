package org.kevoree.docker.containerdriver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.Nullable;

/**
 * Created by leiko on 22/05/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitConfig {

    @Nullable
    private String container = "";
    @Nullable
    private String repo = "";
    @Nullable
    private String tag = "";
    @Nullable
    private String message = "";
    @Nullable
    private String author = "";

    @Nullable
    public String getContainer() {
        return container;
    }

    public void setContainer(@Nullable String container) {
        if (container != null) {
            this.container = container;
        }
    }

    @Nullable
    public String getRepo() {
        return repo;
    }

    public void setRepo(@Nullable String repo) {
        if (repo != null) {
            this.repo = repo;
        }
    }

    @Nullable
    public String getTag() {
        return tag;
    }

    public void setTag(@Nullable String tag) {
        if (tag != null) {
            this.tag = tag;
        }
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String m) {
        if (m != null) {
            this.message = m;
        }
    }

    @Nullable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@Nullable String author) {
        if (author != null) {
            this.author = author;
        }
    }
}
