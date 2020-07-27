
package jl.mall.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jl.mall.common.Constants;
import jl.mall.common.MallException;
import jl.mall.common.ServiceResultEnum;
import jl.mall.config.annotion.TokenToMallUser;
import jl.mall.entity.MallShoppingCartItem;
import jl.mall.entity.MallUser;
import jl.mall.param.SaveCartItemParam;
import jl.mall.param.UpdateCartItemParam;
import jl.mall.service.MallShoppingCartService;
import jl.mall.util.PageQueryUtil;
import jl.mall.util.PageResult;
import jl.mall.util.Result;
import jl.mall.util.ResultGenerator;
import jl.mall.vo.MallShoppingCartItemVO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "5.商城购物车相关接口")
@RequestMapping("/api")
public class MallShoppingCartAPI {

    @Resource
    private MallShoppingCartService MallShoppingCartService;

    @GetMapping("/shop-cart/page")
    @ApiOperation(value = "购物车列表(每页默认5条)", notes = "传参为页码")
    public Result<PageResult<List<MallShoppingCartItemVO>>> cartItemPageList(Integer pageNumber, @TokenToMallUser MallUser loginMallUser) {
        Map params = new HashMap(4);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("userId", loginMallUser.getUserId());
        params.put("page", pageNumber);
        params.put("limit", Constants.SHOPPING_CART_PAGE_LIMIT);
        //封装分页请求参数
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(MallShoppingCartService.getMyShoppingCartItems(pageUtil));
    }

    @GetMapping("/shop-cart")
    @ApiOperation(value = "购物车列表(网页移动端不分页)", notes = "")
    public Result<List<MallShoppingCartItemVO>> cartItemList(@TokenToMallUser MallUser loginMallUser) {
        return ResultGenerator.genSuccessResult(MallShoppingCartService.getMyShoppingCartItems(loginMallUser.getUserId()));
    }

    @PostMapping("/shop-cart")
    @ApiOperation(value = "添加商品到购物车接口", notes = "传参为商品id、数量")
    public Result saveMallShoppingCartItem(@RequestBody SaveCartItemParam saveCartItemParam,
                                                 @TokenToMallUser MallUser loginMallUser) {
        String saveResult = MallShoppingCartService.saveMallCartItem(saveCartItemParam, loginMallUser.getUserId());
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ApiOperation(value = "修改购物项数据", notes = "传参为购物项id、数量")
    public Result updateMallShoppingCartItem(@RequestBody UpdateCartItemParam updateCartItemParam,
                                                   @TokenToMallUser MallUser loginMallUser) {
        String updateResult = MallShoppingCartService.updateMallCartItem(updateCartItemParam, loginMallUser.getUserId());
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{MallShoppingCartItemId}")
    @ApiOperation(value = "删除购物项", notes = "传参为购物项id")
    public Result updateMallShoppingCartItem(@PathVariable("MallShoppingCartItemId") Long MallShoppingCartItemId,
                                                   @TokenToMallUser MallUser loginMallUser) {
        MallShoppingCartItem MallCartItemById = MallShoppingCartService.getMallCartItemById(MallShoppingCartItemId);
        if (!loginMallUser.getUserId().equals(MallCartItemById.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        Boolean deleteResult = MallShoppingCartService.deleteById(MallShoppingCartItemId);
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    @ApiOperation(value = "根据购物项id数组查询购物项明细", notes = "确认订单页面使用")
    public Result<List<MallShoppingCartItemVO>> toSettle(Long[] cartItemIds, @TokenToMallUser MallUser loginMallUser) {
        if (cartItemIds.length < 1) {
            MallException.fail("参数异常");
        }
        BigDecimal priceTotal = BigDecimal.ZERO;
        List<MallShoppingCartItemVO> itemsForSettle = MallShoppingCartService.getCartItemsForSettle(Arrays.asList(cartItemIds), loginMallUser.getUserId());
        if (CollectionUtils.isEmpty(itemsForSettle)) {
            //无数据则抛出异常
            MallException.fail("参数异常");
        } else {
            //总价
            for (MallShoppingCartItemVO mallShoppingCartItemVO : itemsForSettle) {
                priceTotal = mallShoppingCartItemVO.getSellingPrice().multiply(BigDecimal.valueOf(mallShoppingCartItemVO.getGoodsCount())).add(priceTotal);
            }
            if (priceTotal.compareTo(BigDecimal.ONE) < 0) {
                MallException.fail("价格异常");
            }
        }
        return ResultGenerator.genSuccessResult(itemsForSettle);
    }
}
