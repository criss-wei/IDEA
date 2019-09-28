package com.wei.mvc.controller;


import com.wei.mvc.service.WeiCurdService;
import com.wei.mvcframework.annotation.WeiAutowired;
import com.wei.mvcframework.annotation.WeiController;
import com.wei.mvcframework.annotation.WeiRequestMapping;
import com.wei.mvcframework.annotation.WeiRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wei
 * @date 2019/7/3-22:35
 */
@WeiController
@WeiRequestMapping("/wei")
public class WeiCurdController {

    @WeiAutowired
    private WeiCurdService weiCurdService;

    @WeiRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,
                     @WeiRequestParam("name") String name){
            String  result=weiCurdService.get(name);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
