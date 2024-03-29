package org.kevoree.docker.containerdriver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author expi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContainerInfo {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Warnings")
    private String[] warnings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getWarnings() {
        return warnings;
    }

    public void setWarnings(String[] warnings) {
        this.warnings = warnings;
    }


    @Override
    public String toString() {
        return "ContainerInfo{" + "id=" + id + "}";
    }
    
}
