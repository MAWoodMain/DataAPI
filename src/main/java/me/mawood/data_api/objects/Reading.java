package me.mawood.data_api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel("Reading")
public class Reading
{
  private Long readingid;
  private Long locationid;
  private Long datatypeid;
  private Double reading;
  private long timestamp;

  @JsonIgnore
  public Long getReadingid() {
    return readingid;
  }

  public void setReadingid(Long readingid) {
    this.readingid = readingid;
  }

  @JsonIgnore
  public Long getLocationid() {
    return locationid;
  }

  public void setLocationid(Long locationid) {
    this.locationid = locationid;
  }

  @JsonIgnore
  public Long getDatatypeid() {
    return datatypeid;
  }

  public void setDatatypeid(Long datatypeid) {
    this.datatypeid = datatypeid;
  }

  @ApiModelProperty(value = "The raw reading value", required = true)
  public Double getReading() {
    return reading;
  }

  public void setReading(Double reading) {
    this.reading = reading;
  }

  @ApiModelProperty(value = "The timestamp in epoch millis", required = true)
  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

    @Override
    public String toString()
    {
        return "Reading{" +
                "readingid=" + readingid +
                ", locationid=" + locationid +
                ", datatypeid=" + datatypeid +
                ", reading=" + reading +
                ", timestamp=" + timestamp +
                '}';
    }
}
