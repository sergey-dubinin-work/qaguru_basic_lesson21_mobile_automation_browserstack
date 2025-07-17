package guru.qa.helpers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class SessionDetails {

    @JsonProperty("video_url")
    private String videoUrl;

    @JsonProperty("logs")
    private String logsUrl;

    @JsonProperty("appium_logs_url")
    private String appiumLogsUrl;

    @JsonProperty("device_logs_url")
    private String deviceLogsUrl;

}
