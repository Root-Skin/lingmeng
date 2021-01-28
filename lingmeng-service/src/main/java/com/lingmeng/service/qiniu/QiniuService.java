package com.lingmeng.service.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.rtc.RtcAppManager;
import com.qiniu.rtc.RtcRoomManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

@Service
public class QiniuService {

    private RtcAppManager manager;

    private RtcRoomManager rmanager;
    /**
     * @param appId      房间所属帐号的 app
     * @param roomName   房间名称，需满足规格 ^[a-zA-Z0-9_-]{3,64}$
     * @param userId     请求加入房间的用户 ID，需满足规格 ^[a-zA-Z0-9_-]{3,50}$
     * @param expireAt   int64 类型，鉴权的有效时间，传入以秒为单位的64位Unix绝对时间，token 将在该时间后失效
     * @param permission 该用户的房间管理权限，"admin" 或 "user"，默认为 "user" 。当权限角色为 "admin" 时，拥有将其他用户移除出房
     *                   间等特权.
     * @return roomToken
     * @throws Exception
     */
    public String getRoomToken1(String appId, String roomName, String userId,
                               long expireAt, String permission,String accessKey,String secretKey) throws Exception {
        Auth auth= Auth.create(accessKey, secretKey);
        if(rmanager==null){
            rmanager=new RtcRoomManager(auth);
        }
       return  rmanager.getRoomToken(appId, roomName, userId, expireAt, permission);
    }

    public Response createApp1(String hub, String title, int maxUsers,
                               boolean noAutoKickUser) throws QiniuException {
        Auth auth= Auth.create("asdasdasd", "asdasdad111");
        if(manager==null){
            manager=new RtcAppManager(auth);
        }
        Response response=manager.createApp("test0024", "zwhome", 10, false);

        return response;
    }

}
