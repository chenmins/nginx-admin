package com.jslsolucoes.nginxrest.wcl;

import cn.hutool.core.util.StrUtil;
import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClientBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxPingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(description = "主要接口")
@RestController
public class IndexController {

    private NginxAgentClient nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();

    @RequestMapping("/index/{s}")
    public String index(@PathVariable String s){
        final StringBuffer a = new StringBuffer();
        nginxAgentClient.api(NginxAgentClientApis.ping())
                .withAuthorizationKey("changeit")
                .withEndpoint("http://192.168.1.172:3000")
                .build()
                .ping()
                .thenAccept(nginxResponse -> {
                    if(nginxResponse.error()) {
                        NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
                        a.append( "你好error:"+nginxExceptionResponse.getStackTrace());
                    } else if(nginxResponse.forbidden()) {
                        NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
                        a.append("你好forbidden："+nginxAuthenticationFailResponse.getMessage());
                    } else {
                        NginxPingResponse nginxPingResponse = (NginxPingResponse) nginxResponse;
                        a.append( "pong"+nginxPingResponse.getMessage());
                    }
                }).join();
        return a.toString();
    }

    @ApiOperation(value = "用户登陆接口，form表单提交" ,  notes="userName=password即登陆成功")
    @RequestMapping(value="/index/loginForm",method=RequestMethod.POST)
    public Result<String> loginForm(UserInfo userInfo){
        if(!userInfo.getUserName().equals(userInfo.getPassword())){
            return new Result<String>(1,"账号或密码错误，登陆失败",0,null);
        }
        return new Result<String>(0,"登陆成功",0,null);
    }

    @ApiOperation(value = "用户登陆接口，json格式提交" ,  notes="userName=password即登陆成功")
    @RequestMapping(value="/index/loginJson",method=RequestMethod.POST)
    public Result<String> loginJson(@RequestBody UserInfo userInfo){
        if(!userInfo.getUserName().equals(userInfo.getPassword())){
            return new Result<String>(1,"账号或密码错误，登陆失败",0,null);
        }
        return new Result<String>(0,"登陆成功",0,null);
    }


}
