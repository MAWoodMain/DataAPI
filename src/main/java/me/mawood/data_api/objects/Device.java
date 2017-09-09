package me.mawood.data_api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Device
{
  private Long id;
  private Long parentId;
  private String name;
  private String tag;
  private String description;

  @JsonIgnore
  public boolean isValid()
  {
      // required fields
      if(name != null && name.length() > 25) return false;
      if(tag != null && tag.length() > 25) return false;
      // optional fields
      if(description != null) if(description.length() > 255) return false;
      return true;
  }

  @JsonIgnore
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @JsonIgnore
  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
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
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
