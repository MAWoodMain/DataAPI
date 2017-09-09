package me.mawood.data_api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Reading
{
  private Long readingid;
  private Long locationid;
  private Long datatypeid;
  private Double reading;
  private java.sql.Timestamp timestamp;

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

  public Double getReading() {
    return reading;
  }

  public void setReading(Double reading) {
    this.reading = reading;
  }

  public java.sql.Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(java.sql.Timestamp timestamp) {
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
