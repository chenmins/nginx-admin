package com.jslsolucoes.nginxrest.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClientBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxPingResponse;
import com.jslsolucoes.nginxrest.pojo.NginxNode;
import com.jslsolucoes.nginxrest.pojo.NginxResult;
import com.jslsolucoes.nginxrest.pojo.UserInfo;
import com.jslsolucoes.nginxrest.service.IndexService;
import com.jslsolucoes.nginxrest.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "主要接口")
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

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

    @ApiOperation(value = "根据条件，分页获取所有nginx节点的列表" ,  notes="没有参数即全部查询，如果有参数，返回条件查询后的列表；分页信息如果不传将只返回列表，需要分页的话，page为1是第一页，limit为一页显示多少")
    @RequestMapping(value="/index/selectByExample",method=RequestMethod.POST)
    public Result<NginxNode> selectByExample(NginxNode nginxNode,int page,int limit){
        List<NginxNode> list = indexService.selectByExample(nginxNode);
        if (page != 0 && limit != 0){
            int start = (page-1)*limit;
            int end = page*limit;
            return new Result<NginxNode>(0,"成功",list.size(),CollectionUtil.sub(list, start, end));
        }else{
            return new Result<NginxNode>(0,"成功",0,list);
        }
    }

    @ApiOperation(value = "根据主键获取nginx状态信息" ,  notes="需要传递id参数")
    @RequestMapping(value="/index/status",method=RequestMethod.POST)
    public NginxResult status(@RequestParam int idNginx){
        return indexService.status(idNginx);
    }


}
