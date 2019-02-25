package com.fastdfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName PageController
 * @description:  基本页面controller
 **/
@Controller
public class PageController {

    ModelAndView mav = new ModelAndView();

    @RequestMapping("uploadFile.html")
    public ModelAndView indexInput(){
        mav.setViewName("index/uploadFile");
        return mav;
    }

    @RequestMapping("login.html")
    public ModelAndView loginInput(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        mav.setViewName("login/index");
        return mav;
    }



    /**
     * @Title: error
     * @Description:  错误页面
     * @param code : 状态码
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequestMapping(value = "/error/{code}")
    public ModelAndView error(@PathVariable("code") int code) {
        switch (code) {
            case 404:
                mav.addObject("code",404);
                mav.setViewName("error/404");
                break;
            case 500:
                mav.addObject("code",500);
                mav.setViewName("error/500");
                break;
        }
        return mav;
    }

}
