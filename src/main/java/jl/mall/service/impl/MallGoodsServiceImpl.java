
package jl.mall.service.impl;

import com.alibaba.fastjson.JSON;
import jl.mall.common.ServiceResultEnum;
import jl.mall.dao.MallGoodsMapper;
import jl.mall.entity.MallGoods;
import jl.mall.param.MallGoodsSearchParam;
import jl.mall.service.MallGoodsService;
import jl.mall.util.BeanUtil;
import jl.mall.util.PageQueryUtil;
import jl.mall.util.PageResult;
import jl.mall.util.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MallGoodsServiceImpl implements MallGoodsService {

    @Autowired
    private MallGoodsMapper goodsMapper;

    @Override
    public PageResult getMallGoodsPage(MallGoodsSearchParam pageUtil) {
        log.info("params:{}",JSON.toJSONString(pageUtil));
        List<MallGoods> goodsList = goodsMapper.findMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveMallGoods(MallGoods goods) {
        goods.setCreateTime(new Date());
        goods.setCreateUser(SystemUtil.getAdminUserId());
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveMallGoods(List<MallGoods> mallGoodsList) {
        if (!CollectionUtils.isEmpty(mallGoodsList)) {
            goodsMapper.batchInsert(mallGoodsList);
        }
    }

    @Override
    public String updateMallGoods(MallGoods goods) {
        MallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        log.info(JSON.toJSONString(goods));
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public MallGoods getMallGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }


    @Override
    public PageResult searchMallGoods(PageQueryUtil pageUtil) {
        List<MallGoods> goodsList = goodsMapper.findMallGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalMallGoodsBySearch(pageUtil);
        List<MallGoods> MallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            MallSearchGoodsVOS = BeanUtil.copyList(goodsList, MallGoods.class);
            for (MallGoods MallSearchGoodsVO : MallSearchGoodsVOS) {
                String goodsName = MallSearchGoodsVO.getGoodsName();
                String goodsIntro = MallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    MallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    MallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(MallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
