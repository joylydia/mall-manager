package jl.mall.dao;

import jl.mall.common.Mapper;
import jl.mall.entity.MallShoppingCartItem;
import jl.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallShoppingCartItemMapper   extends Mapper<MallShoppingCartItem> {


    MallShoppingCartItem selectByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    List<MallShoppingCartItem> selectByUserId(@Param("userId") Long userId, @Param("number") int number);

    List<MallShoppingCartItem> selectByUserIdAndCartItemIds(@Param("userId") Long userId, @Param("cartItemIds") List<Long> cartItemIds);

    int selectCountByUserId(Long userId);


    int deleteBatch(List<Long> ids);

    List<MallShoppingCartItem> findMyMallCartItems(PageQueryUtil pageUtil);

    int getTotalMyMallCartItems(PageQueryUtil pageUtil);
}