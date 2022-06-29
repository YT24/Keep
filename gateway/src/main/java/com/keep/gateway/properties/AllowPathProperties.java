package com.keep.gateway.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "yt.allow")
@Data
@Component
public class AllowPathProperties {

    private final List<String> paths;
}
