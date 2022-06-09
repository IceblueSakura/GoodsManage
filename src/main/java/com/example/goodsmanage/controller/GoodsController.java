package com.example.goodsmanage.controller;


import com.example.goodsmanage.entity.GoodsVO;
import com.example.goodsmanage.entity.GoodsUpload;
import com.example.goodsmanage.entity.TokenInfo;
import com.example.goodsmanage.entity.UserVO;
import com.example.goodsmanage.model.Goods;
import com.example.goodsmanage.services.GoodsService;

import com.example.goodsmanage.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/goods")  // 这个类接收的请求地址
@Slf4j // 自动生成log对象注解，用于输出格式化的日志(而不是println）
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired //自动装配Service
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping({"", "/"})  // 查询所有商品
    public String getAllGoods(Model model) {
        model.addAttribute("goodsList", goodsService.selectAllGoods());
        model.addAttribute("select", "所有商品");
        return "goods/goodsList";
    }

    @GetMapping({"/{gid}"})  // 根据gid查询商品
    public String getOneGoods(@PathVariable("gid") Integer gid, Model model) {  //@PathVariable获取地址传递的数据
        GoodsVO goodsVO = goodsService.selectOneGoods(gid, null);
        if (goodsVO == null) {
            model.addAttribute("message", "资源：GID: " + gid + " 未找到！");
            return "404";
        }
        model.addAttribute("goods", goodsService.selectOneGoods(gid, null));

        return "goods/goodsInfo";
    }

    @GetMapping({"/modified/{gid}"})  // 根据gid获取内容，传递内容修改页面
    public String modifiedGoodsPage(@PathVariable("gid") Integer gid, Model model) {
        model.addAttribute("goods", goodsService.selectOneGoods(gid, null));
        return "goods/goodsModified";
    }

    @PostMapping({"/modified"})  // 提交商品修改的结果
    public String modifiedGoods(@CookieValue(value = "token", defaultValue = "") String token,  // 获取Cookie内的token信息，设置默认值防止没有token(比如还没登录)
                                @ModelAttribute("goods") GoodsUpload goodsUpload,  // 通过Model传递来的参数(不是普通的post提交方式，普通post/get用@RequestParam)
                                Model model) {
        TokenInfo tokenInfo = Utils.getInfoByToken(token); // 还是通过Cookie内的token获取用户信息，用于修改商品的最后修改人
        if (tokenInfo != null) {
            Goods goods = goodsUpload.convertToGoods(tokenInfo.getSid());  // 把修改用户传进去，转换为数据库操作需要的Goods对象(也就是数据表的Model)
            try {
                goodsService.updateGoods(goods);
                model.addAttribute("status", "修改成功！");
                model.addAttribute("info", "修改用户：" + tokenInfo.getUsername() + "  修改后商品名：" + goods.getName());
            } catch (Exception e) {
                log.error(e.toString());
                model.addAttribute("status", "修改失败！请检查输入后重试！");
                model.addAttribute("info", "修改用户：" + tokenInfo.getUsername() + "  准备修改的商品名：" + goods.getName());
            }
        } else {
            model.addAttribute("用户未登录或无权限！");
        }
        return "tip";
    }

    @GetMapping({"/add"})  // 返回添加商品页面
    public String addGoodsPage(Model model) {
        model.addAttribute("goods", new GoodsVO());
        return "goods/goodsAdd";
    }

    @PostMapping({"/add"})  // 提交添加商品的结果
    public String addGoods(@CookieValue(value = "token", defaultValue = "") String token,
                           @ModelAttribute("goods") GoodsUpload goodsUpload,
                           Model model) {
        TokenInfo tokenInfo = Utils.getInfoByToken(token);
        if (tokenInfo != null) {
            Goods goods = goodsUpload.convertToGoods(tokenInfo.getSid(), tokenInfo.getSid()); // 把修改用户和新增用户都传进去
            try {
                goodsService.addGoods(goods);
                model.addAttribute("status", "新增成功！");
                model.addAttribute("info", "新增用户：" + tokenInfo.getUsername() + "  新增商品名：" + goods.getName());
            } catch (Exception e) {
                log.error(e.toString());
                model.addAttribute("status", "修改失败！请检查输入后重试！");
                model.addAttribute("info", "修改用户：" + tokenInfo.getUsername() + "  准备修改的商品名：" + goods.getName());
            }
        }
        return "tip";

    }

    @GetMapping({"/delete/{gid}"})  // 删除商品的页面，给页面传了商品的gid
    public String deletePage(@PathVariable("gid") Integer gid, Model model) {
        model.addAttribute("gid", gid);
        return "goods/goodsDelete";
    }

    @PostMapping({"/delete"})  // 删除商品的响应(这是POST方法)
    public String delete(@RequestParam("gid") Integer gid, Model model) {
        goodsService.deleteGoodsById(gid);
        model.addAttribute("status", "删除成功！");
        model.addAttribute("info", "刚才删除的商品gid：" + gid);
        return "tip";
    }

    @GetMapping({"/conditions"})  // 按条件查找的页面
    public String goodsListConditionsPage() {
        return "goods/goodsListConditions";
    }

    @GetMapping({"/conditions/select"})  // 按条件查询的结果
    public String goodsListConditions(@RequestParam(value = "hasSort", required = false) Boolean hasSort,  // 是否排序，html内checkbox选中则为true，未选择为null
                                      @RequestParam(value = "hasRange", required = false) Boolean hasRange,  // 是否设定范围，html内checkbox选中则为true，未选择为null
                                      @RequestParam(value = "rangeBy", required = false) String rangeBy,  // 范围设定依据
                                      @RequestParam(value = "rangeLess", required = false) Double rangeLess,  // 最小值
                                      @RequestParam(value = "rangeGreater", required = false) Double rangeGreater,  // 最大值
                                      @RequestParam(value = "orderBy", required = false) String orderBy,  // 排序依据
                                      @RequestParam(value = "order", required = false) String order,  // 降序还是升序，asc升序desc降序
                                      Model model) {

        boolean isAsc = Objects.equals(order, "asc");  // 如果order=asc,那这个变量的值为true

        try {
            List<GoodsVO> goodsVOList;
            String selectTip = "条件筛选, ";  // 查询条件的文本提示
            if (hasSort != null && hasRange != null) {  // 排序和范围都有
                goodsVOList = goodsService.selectByConditions(rangeBy, rangeLess, rangeGreater, orderBy, isAsc);
                selectTip = selectTip + "范围：" + rangeLess + "<" + rangeBy + "<" + rangeGreater + ", 排序：依据：" + orderBy + ", 方式：" + order;  // 查询条件的文本提示拼接
            } else if (hasSort != null) {  // 只有排序
                goodsVOList = goodsService.selectByConditions(null, null, null, orderBy, isAsc);
                selectTip = "排序：依据：" + orderBy + ", 方式：" + order;  // 查询条件的文本提示拼接
            } else if (hasRange != null) {  // 只有范围
                goodsVOList = goodsService.selectByConditions(rangeBy, rangeLess, rangeGreater, null, null);
                selectTip = selectTip + "范围：" + rangeLess + "<" + rangeBy + "<" + rangeGreater;  // 查询条件的文本提示拼接
            } else {  // 都没有设定，返回全部商品
                goodsVOList = goodsService.selectAllGoods();
                selectTip = selectTip + "全部商品";  // 查询条件的文本提示拼接
            }
            model.addAttribute("goodsList", goodsVOList);
            model.addAttribute("select", selectTip);
            return "goods/goodsList";
        } catch (Exception e) {
            log.error(e.toString());
            model.addAttribute("status", "条件查询失败！");
            model.addAttribute("info", "可能是条件有空白的！具体原因：" + e.toString());

            return "tip";
        }
    }

    @GetMapping({"/classification"})
    public String classificationPage(Model model) {
        model.addAttribute("goodsClassificationList", goodsService.selectClassification());
        return "goods/goodsListClassification";
    }

    @GetMapping({"/classification/select"})
    public String classification(@RequestParam(value = "classification", required = false,defaultValue = "default") String classification,
                                 Model model) {
        model.addAttribute("goodsClassificationList", goodsService.selectClassification());
        model.addAttribute("goodsList", goodsService.selectAllByClassification(classification));
        return "goods/goodsListClassification";
    }
}