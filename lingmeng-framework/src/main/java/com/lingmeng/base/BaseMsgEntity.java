package com.lingmeng.base;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

 /**
  * @Author skin
  * @Date  2021/1/12
  * @Description 消息实体
  **/
@Builder
@Data
@Accessors(chain = true)
public class BaseMsgEntity {

    /**
     * 信息发送者
     */
    private String fromUsers;

    /**
     * 信息接收者
     */
    private List<String> toUsers;

    /**
     * 接收方设备类型 android，ios,rn,web
     */
    private Integer platformType;

    /**
     * 推送方式：广播，组播，单播
     */
    private Integer pushType;

    /**
     * 消息类型：具体消息发送类型
     */
    private Integer msgType;

    /**
     * 消息操作
     */
    private Integer msgMethod;

    /**
     * 消息所属模块
     */
    private Integer msgModular;

    /**
     * 消息所属类型
     */
    private Integer msgStatus;


    private String routeKey;


    /**
     * 消息类型：形式
     */
    private Integer shape;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 推送消息
     */
    private Object msg;

    private List<Map<String,String>> msgMap;

    private String typeStr;

    private long createDate;

    private Integer allType;

    /**
     * 是否弹窗站展示
     */
    private boolean notificationFlag;

    /**
     * 是否推送
     */
    private boolean pushFlag;

    private boolean receiveFlag;

}
