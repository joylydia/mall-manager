
package jl.mall.service;

import jl.mall.entity.MallOrder;
import jl.mall.entity.MallOrderItem;
import jl.mall.entity.MallUser;
import jl.mall.entity.MallUserAddress;
import jl.mall.param.MallOrderSearchParam;
import jl.mall.util.PageQueryUtil;
import jl.mall.util.PageResult;
import jl.mall.vo.MallOrderDetailVO;
import jl.mall.vo.MallShoppingCartItemVO;

import java.util.List;

public interface MallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getMallOrdersPage(MallOrderSearchParam pageUtil);

    /**
     * 订单信息修改
     *
     * @param mallOrder
     * @return
     */
    String updateOrderInfo(MallOrder mallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    List<MallOrderItem> getOrderItems(Long id);



    public String saveOrder(MallUser loginMallUser, MallUserAddress address, List<MallShoppingCartItemVO> myShoppingCartItems);


    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    MallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);


    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    public PageResult getMyOrders(PageQueryUtil pageUtil);


    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);


    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);


}
