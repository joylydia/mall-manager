
package jl.mall.service;


import jl.mall.entity.MallShoppingCartItem;
import jl.mall.param.SaveCartItemParam;
import jl.mall.param.UpdateCartItemParam;
import jl.mall.util.PageQueryUtil;
import jl.mall.util.PageResult;
import jl.mall.vo.MallShoppingCartItemVO;

import java.util.List;

public interface MallShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param saveCartItemParam
     * @param userId
     * @return
     */
    String saveMallCartItem(SaveCartItemParam saveCartItemParam, Long userId);

    /**
     * 修改购物车中的属性
     *
     * @param updateCartItemParam
     * @param userId
     * @return
     */
    String updateMallCartItem(UpdateCartItemParam updateCartItemParam, Long userId);

    /**
     * 获取购物项详情
     *
     * @param MallShoppingCartItemId
     * @return
     */
    MallShoppingCartItem getMallCartItemById(Long MallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     * @param MallShoppingCartItemId
     * @return
     */
    Boolean deleteById(Long MallShoppingCartItemId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param userId
     * @return
     */
    List<MallShoppingCartItemVO> getMyShoppingCartItems(Long userId);

    /**
     * 根据userId和cartItemIds获取对应的购物项记录
     *
     * @param cartItemIds
     * @param userId
     * @return
     */
    List<MallShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long userId);

    /**
     * 我的购物车(分页数据)
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyShoppingCartItems(PageQueryUtil pageUtil);
}
