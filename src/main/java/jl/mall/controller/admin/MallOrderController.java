/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package jl.mall.controller.admin;

import jl.mall.common.ServiceResultEnum;
import jl.mall.entity.MallOrder;
import jl.mall.entity.MallOrderItem;
import jl.mall.service.MallOrderService;
import jl.mall.util.PageQueryUtil;
import jl.mall.util.Result;
import jl.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author joy
 */
@Controller
@RequestMapping("/admin")
public class MallOrderController {

    @Resource
    private MallOrderService mallOrderService;

    @GetMapping("/orders")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "orders");
        return "admin/mall_order";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/orders/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallOrderService.getNewBeeMallOrdersPage(pageUtil));
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody MallOrder mallOrder) {
        if (Objects.isNull(mallOrder.getTotalPrice())
                || Objects.isNull(mallOrder.getOrderId())
                || mallOrder.getOrderId() < 1
                || mallOrder.getTotalPrice().compareTo(BigDecimal.ONE) <  0) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.updateOrderInfo(mallOrder);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/order-items/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        List<MallOrderItem> orderItems = mallOrderService.getOrderItems(id);
        if (!CollectionUtils.isEmpty(orderItems)) {
            return ResultGenerator.genSuccessResult(orderItems);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    /**
     * 配货
     */
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    public Result checkDone(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.checkDone(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 出库
     */
    @RequestMapping(value = "/orders/checkOut", method = RequestMethod.POST)
    @ResponseBody
    public Result checkOut(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.checkOut(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/orders/close", method = RequestMethod.POST)
    @ResponseBody
    public Result closeOrder(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.closeOrder(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


}