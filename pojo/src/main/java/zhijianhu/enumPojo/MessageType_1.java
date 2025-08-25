package zhijianhu.enumPojo;

import lombok.Getter;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/10
 * 说明:
 */
@Getter
public enum MessageType {
    LIKE(1,"点赞了"),
    REVIEW(2,"评论了");

  private final Integer value;
  private final String name;
  MessageType(Integer value,String name){
      this.value = value;
      this.name = name;
  }
  public static String getNameByCode(int code){
      for (MessageType messageType : MessageType.values()) {
          if(messageType.getValue() == code){
              return messageType.getName();
          }
      }
      return null;
  }
}
