package com.voter.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private String name;
    private String path;
    private long size;
    // getters and setters
}