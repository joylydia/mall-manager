
package jl.mall.dao;

import jl.mall.common.Mapper;
import jl.mall.entity.MallGoods;
import jl.mall.entity.StockNumDTO;
import jl.mall.param.MallGoodsSearchParam;
import jl.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallGoodsMapper extends Mapper<MallGoods> {

    int updateByPrimaryKeyWithBLOBs(MallGoods record);

    List<MallGoods> findMallGoodsList(MallGoodsSearchParam pageUtil);

    int getTotalMallGoods(MallGoodsSearchParam pageUtil);

    List<MallGoods> selecListByKeys(List<Long> goodsIds);

    List<MallGoods> findMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("MallGoodsList") List<MallGoods> mallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}