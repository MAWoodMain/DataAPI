package me.mawood.data_api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataType
{
  private Long datatypeid;
  private String name;
  private String tag;
  private String symbol;
  private String description;

  @JsonIgnore
  public Long getDatatypeid() {
    return datatypeid;
  }

  public void setDatatypeid(Long datatypeid) {
    this.datatypeid = datatypeid;
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

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
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
        return "DataType{" +
                "datatypeid=" + datatypeid +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", symbol='" + symbol + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
