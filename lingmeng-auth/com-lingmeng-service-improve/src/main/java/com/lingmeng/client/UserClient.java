package com.lingmeng.client;

import com.lingmeng.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "lingmeng-user-service")
public interface UserClient extends UserApi {
}
