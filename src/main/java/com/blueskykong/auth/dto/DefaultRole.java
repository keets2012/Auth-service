package com.blueskykong.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.UUID;

/**
 * Created by keets on 2017/11/22.
 */

@JsonPropertyOrder({
        "id",
        "name",
        "description"})
@Data
public class DefaultRole {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public DefaultRole(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
