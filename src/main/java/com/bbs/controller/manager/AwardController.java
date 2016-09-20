package com.bbs.controller.manager;

import com.bbs.controller.BaseController;
import com.bbs.mybatis.model.Awards;
import com.bbs.mybatis.model.AwardsRecords;
import com.bbs.service.IEggService;
import com.bbs.service.IUserService;
import com.bbs.util.page.Page;
import com.bbs.util.page.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by lihongde on 2016/9/13 14:49
 */
@Controller
@RequestMapping(value = "/award")
public class AwardController extends BaseController {

    @Resource
    private IEggService eggService;

    @Resource
    private IUserService userService;

    /**
     * 奖项列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listAwards() {
        ModelAndView mav = new ModelAndView("award/list");
        Page<Awards> pages = eggService.findPageAwards(page, size);
        mav.addObject("page", PageHelper.getPageModel(request, pages));
        mav.addObject("awards", pages.getItems());
        return mav;
    }

    /**
     * 添加奖项
     * @return
     */
    @RequestMapping(value = "/pub", method = RequestMethod.GET)
    public String pub(){
        return "award/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrUpdate(Awards awards){
        eggService.addOrUpdate(awards);
        return "redirect:/award/list";
    }

    /**
     * 详情
     * @param awardId
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "id") Integer awardId){
        ModelAndView mav = new ModelAndView("award/add");
        mav.addObject("award", eggService.getAwards(awardId));
        return mav;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "id") Integer id){
        eggService.deleteRecordByAwardId(id);
        eggService.delete(id);
        return "redirect:/award/list";
    }

    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public ModelAndView listAwardsRecord(){
        ModelAndView mav = new ModelAndView("award/record");
        Page<AwardsRecords> pages = eggService.findPageAwardsRecords(page, size);
        mav.addObject("page", PageHelper.getPageModel(request, pages));
        mav.addObject("records", pages.getItems());
        mav.addObject("users", userService.findAllUsers());
        return mav;
    }
}
