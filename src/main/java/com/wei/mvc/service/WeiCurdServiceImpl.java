package com.wei.mvc.service;

import com.wei.mvcframework.annotation.WeiService;

/**
 * @author wei
 * @date 2019/7/7-17:19
 */
@WeiService
public class WeiCurdServiceImpl implements  WeiCurdService {
    @Override
    public String get(String name) {
        return "wei:"+name ;
    }
}
