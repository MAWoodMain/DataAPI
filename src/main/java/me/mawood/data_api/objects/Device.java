package me.mawood.data_api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Device
{
  private Long deviceid;
  private Long parentlocation;
  private String name;
  private String tag;
  private String description;

  @JsonIgnore
  public Long getDeviceid() {
    return deviceid;
  }

  public void setDeviceid(Long deviceid) {
    this.deviceid = deviceid;
  }

  public Long getParentlocation() {
    return parentlocation;
  }

  public void setParentlocation(Long parentlocation) {
    this.parentlocation = parentlocation;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

    @Override
    public String toString()
    {
        return "Device{" +
                "deviceid=" + deviceid +
                ", parentlocation=" + parentlocation +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
