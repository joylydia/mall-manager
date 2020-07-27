
package jl.mall.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jl.mall.common.Constants;
import jl.mall.common.MallException;
import jl.mall.common.ServiceResultEnum;
import jl.mall.config.annotion.TokenToMallUser;
import jl.mall.entity.MallGoods;
import jl.mall.entity.MallUser;
import jl.mall.service.MallGoodsService;
import jl.mall.util.*;
import jl.mall.vo.MallGoodsDetailVO;
import jl.mall.vo.MallSearchGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "4.商城商品相关接口")
@RequestMapping("/api")
@Slf4j
public class MallGoodsAPI {

    @Resource
    private MallGoodsService mallGoodsService;

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索接口", notes = "根据关键字和分类id进行搜索")
    public Result<PageResult<List<MallSearchGoodsVO>>> search(
            @RequestParam(required = false) @ApiParam(value = "搜索关键字") String keyword,
            @RequestParam(required = false) @ApiParam(value = "分类id") Long goodsCategoryId,
            @RequestParam(required = false) @ApiParam(value = "orderBy") String orderBy,
            @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
            @TokenToMallUser MallUser loginMallUser) {

        log.info("goods search api,keyword={},goodsCategoryId={},orderBy={},pageNumber={},userId={}", keyword, goodsCategoryId, orderBy, pageNumber, loginMallUser.getUserId());

        Map params = new HashMap(4);
        //两个搜索参数都为空，直接返回异常
        if (goodsCategoryId == null && StringUtils.isEmpty(keyword)) {
            MallException.fail("非法的搜索参数");
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("goodsCategoryId", goodsCategoryId);
        params.put("page", pageNumber);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //对keyword做过滤 去掉空格
        if (!StringUtils.isEmpty(keyword)) {
            params.put("keyword", keyword);
        }
        if (!StringUtils.isEmpty(orderBy)) {
            params.put("orderBy", orderBy);
        }
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallGoodsService.searchMallGoods(pageUtil));
    }

    @GetMapping("/goods/detail/{goodsId}")
    @ApiOperation(value = "商品详情接口", notes = "传参为商品id")
    public Result<MallGoodsDetailVO> goodsDetail(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId, @TokenToMallUser MallUser loginMallUser) {
        log.info("goods detail api,goodsId={},userId={}", goodsId, loginMallUser.getUserId());
        if (goodsId < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        MallGoods goods = mallGoodsService.getMallGoodsById(goodsId);
        if (goods == null) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            MallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        MallGoodsDetailVO goodsDetailVO = new MallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }

}
