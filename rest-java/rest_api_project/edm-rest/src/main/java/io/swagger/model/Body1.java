package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
/**
 * Body1
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-12T12:44:56.423Z")

public class Body1   {
  /**
   * Gets or Sets filesystem
   */
  public enum FilesystemEnum {
    HDFS("hdfs"),
    
    S3("s3"),
    
    FS("fs"),
    
    TARBALL("tarball");

    private String value;

    FilesystemEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FilesystemEnum fromValue(String text) {
      for (FilesystemEnum b : FilesystemEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("filesystem")
  private FilesystemEnum filesystem = null;

  @JsonProperty("location")
  private String location = null;

  @JsonProperty("memberOfSuT")
  private String memberOfSuT = null;

  @JsonProperty("isBigFile")
  private Boolean isBigFile = null;

  @JsonProperty("bigFileURL")
  private String bigFileURL = null;

  @JsonProperty("content")
  private String content = null;

  public Body1 filesystem(FilesystemEnum filesystem) {
    this.filesystem = filesystem;
    return this;
  }

   /**
   * Get filesystem
   * @return filesystem
  **/
  @ApiModelProperty(example = "s3", value = "")
  public FilesystemEnum getFilesystem() {
    return filesystem;
  }

  public void setFilesystem(FilesystemEnum filesystem) {
    this.filesystem = filesystem;
  }

  public Body1 location(String location) {
    this.location = location;
    return this;
  }

   /**
   * Can be empty or can contain any valid folder Path. Should start with /. It is recommended to start always with the callers module 3 letters identifier
   * @return location
  **/
  @ApiModelProperty(example = "/MyModule/MyLocation", value = "Can be empty or can contain any valid folder Path. Should start with /. It is recommended to start always with the callers module 3 letters identifier")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Body1 memberOfSuT(String memberOfSuT) {
    this.memberOfSuT = memberOfSuT;
    return this;
  }

   /**
   * Empty or Systems Under Test identification
   * @return memberOfSuT
  **/
  @ApiModelProperty(value = "Empty or Systems Under Test identification")
  public String getMemberOfSuT() {
    return memberOfSuT;
  }

  public void setMemberOfSuT(String memberOfSuT) {
    this.memberOfSuT = memberOfSuT;
  }

  public Body1 isBigFile(Boolean isBigFile) {
    this.isBigFile = isBigFile;
    return this;
  }

   /**
   * Get isBigFile
   * @return isBigFile
  **/
  @ApiModelProperty(example = "true", value = "")
  public Boolean getIsBigFile() {
    return isBigFile;
  }

  public void setIsBigFile(Boolean isBigFile) {
    this.isBigFile = isBigFile;
  }

  public Body1 bigFileURL(String bigFileURL) {
    this.bigFileURL = bigFileURL;
    return this;
  }

   /**
   * When is big file, specify the URL for fetching by the EDM or by the Caller
   * @return bigFileURL
  **/
  @ApiModelProperty(example = "http://example.com/files/file001.dmp", value = "When is big file, specify the URL for fetching by the EDM or by the Caller")
  public String getBigFileURL() {
    return bigFileURL;
  }

  public void setBigFileURL(String bigFileURL) {
    this.bigFileURL = bigFileURL;
  }

  public Body1 content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(example = "Lorem ipsum dolor sit amet", value = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body1 body1 = (Body1) o;
    return Objects.equals(this.filesystem, body1.filesystem) &&
        Objects.equals(this.location, body1.location) &&
        Objects.equals(this.memberOfSuT, body1.memberOfSuT) &&
        Objects.equals(this.isBigFile, body1.isBigFile) &&
        Objects.equals(this.bigFileURL, body1.bigFileURL) &&
        Objects.equals(this.content, body1.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filesystem, location, memberOfSuT, isBigFile, bigFileURL, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body1 {\n");
    
    sb.append("    filesystem: ").append(toIndentedString(filesystem)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    memberOfSuT: ").append(toIndentedString(memberOfSuT)).append("\n");
    sb.append("    isBigFile: ").append(toIndentedString(isBigFile)).append("\n");
    sb.append("    bigFileURL: ").append(toIndentedString(bigFileURL)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

