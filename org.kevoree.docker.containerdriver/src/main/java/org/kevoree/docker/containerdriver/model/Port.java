package org.kevoree.docker.containerdriver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author expi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Port {
    @JsonProperty("PrivatePort")
    public int privatePort;

    @JsonProperty("PublicPort")
    public int publicPort;

    @JsonProperty("Type")
    public String type;

    @JsonProperty("IP")
    public String ip;

    @NotNull
    @Override
    public String toString() {
        return "Port{" + "privatePort=" + privatePort + ", publicPort=" + publicPort + ", type=" + type + ", ip=" + ip + '}';
    }
}
