
package jl.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import jl.mall.common.*;
import jl.mall.dao.*;
import jl.mall.dto.MallOrderDTO;
import jl.mall.entity.*;
import jl.mall.param.MallOrderItemSearchParam;
import jl.mall.param.MallOrderSearchParam;
import jl.mall.service.MallOrderService;
import jl.mall.util.BeanUtil;
import jl.mall.util.NumberUtil;
import jl.mall.util.PageQueryUtil;
import jl.mall.util.PageResult;
import jl.mall.vo.MallOrderDetailVO;
import jl.mall.vo.MallOrderItemVO;
import jl.mall.vo.MallOrderListVO;
import jl.mall.vo.MallShoppingCartItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
public class MallOrderServiceImpl implements MallOrderService {

    @Autowired
    private MallOrderMapper mallOrderMapper;
    @Autowired
    private MallOrderItemMapper mallOrderItemMapper;
    @Autowired
    private MallGoodsMapper mallGoodsMapper;
    @Autowired
    private MallShoppingCartItemMapper mallShoppingCartItemMapper;
    @Autowired
    private MallOrderAddressMapper mallOrderAddressMapper;
    @Autowired
    private MallUserMapper mallUserMapper;

    @Override
    public PageResult getMallOrdersPage(MallOrderSearchParam pageUtil) {
        if(!StringUtils.isEmpty(pageUtil.getUserName())){
            MallUser mallUser = mallUserMapper.selectByLoginName(pageUtil.getUserName().trim());
            pageUtil.put("userId",mallUser.getUserId());
        }

        if(!StringUtils.isEmpty(pageUtil.getGoodsName())){
            MallOrderItemSearchParam searchParam = new MallOrderItemSearchParam(pageUtil);
            searchParam.put("goodsName",pageUtil.getGoodsName());
            List<MallOrderItem> orderItems = mallOrderItemMapper.selectSearch(searchParam);
            List<Long> orderIds = orderItems.stream().map(MallOrderItem::getOrderId).collect(Collectors.toList());
            pageUtil.put("orderIds",orderIds);
        }
        List<MallOrder> mallOrders = mallOrderMapper.findMallOrderList(pageUtil);
        int total = mallOrderMapper.getTotalMallOrders(pageUtil);
        List<Long> orderIds = mallOrders.stream().map(MallOrder::getOrderId).collect(Collectors.toList());

        //????????????
        List<MallOrderItem> orderItems = mallOrderItemMapper.selectByOrderIds(orderIds);
        Map<Long,String> orderItemMap = new HashMap<>();
        for (MallOrderItem orderItem: orderItems) {
            log.info("orderItemId:{},goodsName:{}",orderItem.getOrderItemId(),orderItem.getGoodsName());
            String goodsName = orderItem.getGoodsName();
            if(!StringUtils.isEmpty(orderItemMap.get(orderItem.getOrderId()))){
                goodsName += "," + orderItemMap.get(orderItem.getOrderId());
            }
            orderItemMap.put(orderItem.getOrderId() , goodsName);
        }

        //????????????
        List<MallOrder> userList = mallOrders.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getUserId()))),
                ArrayList::new));
        List<Long> userIdList = userList.stream().map(MallOrder::getUserId).collect(Collectors.toList());
        String userIds = userIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
        List<MallUser> users = mallUserMapper.selectByIds(userIds);
        Map<Long,String> userMap = users.stream().distinct().collect(Collectors.toMap(MallUser::getUserId,MallUser::getLoginName));

        List<MallOrderDTO> dtos = new ArrayList<>(mallOrders.size());
        for (MallOrder order : mallOrders) {
            MallOrderDTO dto = new MallOrderDTO(userMap.get(order.getUserId()), orderItemMap.get(order.getOrderId()));
            BeanUtil.copyProperties(order,dto);
            dtos.add(dto);
        }
        PageResult pageResult = new PageResult(dtos, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(MallOrder mallOrder) {
        MallOrder temp = mallOrderMapper.selectByPrimaryKey(mallOrder.getOrderId());
        //????????????orderStatus>=0????????????????????????????????????????????????
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(mallOrder.getTotalPrice());
            temp.setUpdateTime(new Date());
            if (mallOrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }


    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //????????????????????? ???????????? ???????????????????????????
        List<MallOrder> orders = mallOrderMapper.selectListByKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MallOrder mallOrder : orders) {
                if (mallOrder.getStatus() == 2) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                    continue;
                }
                if (mallOrder.getOrderStatus() != 1) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //?????????????????? ?????????????????????????????? ?????????????????????????????????
                if (mallOrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //????????????????????????????????????
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "?????????????????????????????????????????????????????????";
                } else {
                    return "????????????????????????????????????????????????????????????????????????????????????";
                }
            }
        }
        //?????????????????? ??????????????????
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        //????????????????????? ???????????? ???????????????????????????
        List<MallOrder> orders = mallOrderMapper.selectListByKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MallOrder mallOrder : orders) {
                if (mallOrder.getStatus() == 2) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                    continue;
                }
                if (mallOrder.getOrderStatus() != 1 && mallOrder.getOrderStatus() != 2) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //?????????????????? ???????????????????????? ?????????????????????????????????
                if (mallOrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //????????????????????????????????????
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "????????????????????????????????????????????????????????????????????????";
                } else {
                    return "?????????????????????????????????????????????????????????????????????????????????????????????";
                }
            }
        }
        //?????????????????? ??????????????????
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        //????????????????????? ???????????? ???????????????????????????
        List<MallOrder> orders = mallOrderMapper.selectListByKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MallOrder mallOrder : orders) {
                // status=1 ????????????????????????
                if (mallOrder.getStatus() == 2) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                    continue;
                }
                //??????????????????????????????????????????
                if (mallOrder.getOrderStatus() == 4 || mallOrder.getOrderStatus() < 0) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //?????????????????? ???????????????????????? ?????????????????????????????????
                if (mallOrderMapper.closeOrder(Arrays.asList(ids), MallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //????????????????????????????????????
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "??????????????????????????????";
                } else {
                    return "??????????????????????????????????????????";
                }
            }
        }
        //?????????????????? ??????????????????
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }
    @Override
    public List<MallOrderItem> getOrderItems(Long id) {
        MallOrder mallOrder = mallOrderMapper.selectByPrimaryKey(id);
        if (mallOrder != null) {
            List<MallOrderItem> orderItems = mallOrderItemMapper.selectByOrderId(mallOrder.getOrderId());
            //?????????????????????
            if (!CollectionUtils.isEmpty(orderItems)) {
                return orderItems;
            }
        }
        return null;
    }


    @Override
    @Transactional
    public String saveOrder(MallUser loginMallUser, MallUserAddress address, List<MallShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(MallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(MallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<MallGoods> mallGoods = mallGoodsMapper.selecListByKeys(goodsIds);
        //?????????????????????????????????
        List<MallGoods> goodsListNotSelling = mallGoods.stream()
                .filter(MallGoodsTemp -> MallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling ????????????????????????????????????
            MallException.fail(goodsListNotSelling.get(0).getGoodsName() + "??????????????????????????????");
        }
        Map<Long, MallGoods> MallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //??????????????????
        for (MallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //?????????????????????????????????????????????????????????????????????????????????????????????
            if (!MallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                MallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //????????????????????????????????????????????????????????????
            if (shoppingCartItemVO.getGoodsCount() > MallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                MallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //???????????????
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(mallGoods)) {
            if (mallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);

                int updateStockNumResult = mallGoodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    MallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //???????????????
                String orderNo = NumberUtil.genOrderNo();
                BigDecimal priceTotal = BigDecimal.ZERO;
                //????????????
                MallOrder mallOrder = new MallOrder();
                mallOrder.setOrderNo(orderNo);
                mallOrder.setUserId(loginMallUser.getUserId());
                //??????
                for (MallShoppingCartItemVO mallShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal = mallShoppingCartItemVO.getSellingPrice().multiply(BigDecimal.valueOf(mallShoppingCartItemVO.getGoodsCount())).add(priceTotal);
                }
                if (priceTotal.compareTo(BigDecimal.ONE) < 0) {
                    MallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                mallOrder.setTotalPrice(priceTotal);
                String extraInfo = "";
                mallOrder.setExtraInfo(extraInfo);
                mallOrder.setPayStatus(((byte) PayStatusEnum.PAY_ING.getPayStatus()));
                mallOrder.setOrderStatus((byte)MallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus());
                mallOrder.setCreateTime(new Date());
                //???????????????????????????????????????
                log.info("mallOrder:{}", JSON.toJSONString(mallOrder));
                if (mallOrderMapper.insertSelective(mallOrder) > 0) {
                    //??????????????????????????????????????????????????????
                    MallOrderAddress mallOrderAddress = new MallOrderAddress();
                    BeanUtil.copyProperties(address, mallOrderAddress);
                    mallOrderAddress.setOrderId(mallOrder.getOrderId());
                    //??????????????????????????????????????????????????????
                    List<MallOrderItem> mallOrderItems = new ArrayList<>();
                    for (MallShoppingCartItemVO mallShoppingCartItemVO : myShoppingCartItems) {
                        MallOrderItem mallOrderItem = new MallOrderItem();
                        //??????BeanUtil????????????MallShoppingCartItemVO?????????????????????MallOrderItem?????????
                        BeanUtil.copyProperties(mallShoppingCartItemVO, mallOrderItem);
                        //MallOrderMapper??????insert()??????????????????useGeneratedKeys??????orderId???????????????
                        mallOrderItem.setOrderId(mallOrder.getOrderId());
                        mallOrderItems.add(mallOrderItem);
                    }
                    //??????????????????
                    if (mallOrderItemMapper.insertBatch(mallOrderItems) > 0 && mallOrderAddressMapper.insertSelective(mallOrderAddress) > 0) {
                        //???????????????????????????????????????????????????Controller???????????????????????????
                        return orderNo;
                    }
                    MallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        MallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }


    @Override
    public MallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        if (!userId.equals(mallOrder.getUserId())) {
            MallException.fail(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        List<MallOrderItem> orderItems = mallOrderItemMapper.selectByOrderId(mallOrder.getOrderId());
        //?????????????????????
        if (!CollectionUtils.isEmpty(orderItems)) {
            List<MallOrderItemVO> MallOrderItemVOS = BeanUtil.copyList(orderItems, MallOrderItemVO.class);
            MallOrderDetailVO mallOrderDetailVO = new MallOrderDetailVO();
            BeanUtil.copyProperties(mallOrder, mallOrderDetailVO);
            log.info("MallOrderDetailVO:{}",JSON.toJSONString(mallOrderDetailVO));
            mallOrderDetailVO.setOrderStatusString(MallOrderStatusEnum.getMallOrderStatusEnumByStatus(mallOrderDetailVO.getOrderStatus()).getName());
            if(mallOrderDetailVO.getPayType() != null) {
                mallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(mallOrderDetailVO.getPayType()).getName());
            }
            mallOrderDetailVO.setMallOrderItemVOS(MallOrderItemVOS);
            return mallOrderDetailVO;
        } else {
            MallException.fail(ServiceResultEnum.ORDER_ITEM_NULL_ERROR.getResult());
            return null;
        }
    }



    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = mallOrderMapper.getTotalMallOrders(pageUtil);
        List<MallOrder> mallOrders = mallOrderMapper.findMallOrderList(pageUtil);
        List<MallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //???????????? ??????????????????vo
            orderListVOS = BeanUtil.copyList(mallOrders, MallOrderListVO.class);
            //?????????????????????????????????
            for (MallOrderListVO MallOrderListVO : orderListVOS) {
                MallOrderListVO.setOrderStatusString(MallOrderStatusEnum.getMallOrderStatusEnumByStatus(MallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = mallOrders.stream().map(MallOrder::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<MallOrderItem> orderItems = mallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<MallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(MallOrderItem::getOrderId));
                for (MallOrderListVO MallOrderListVO : orderListVOS) {
                    //????????????????????????????????????????????????
                    if (itemByOrderIdMap.containsKey(MallOrderListVO.getOrderId())) {
                        List<MallOrderItem> orderItemListTemp = itemByOrderIdMap.get(MallOrderListVO.getOrderId());
                        //???MallOrderItem?????????????????????MallOrderItemVO????????????
                        List<MallOrderItemVO> MallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, MallOrderItemVO.class);
                        MallOrderListVO.setMallOrderItemVOS(MallOrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }



    @Override
    public String cancelOrder(String orderNo, Long userId) {
        MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //todo ?????????????????????userId???????????????????????????
            //todo ??????????????????
            if (mallOrderMapper.closeOrder(Collections.singletonList(mallOrder.getOrderId()), MallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }


    @Override
    public String finishOrder(String orderNo, Long userId) {
        MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //todo ?????????????????????userId???????????????????????????
            //todo ??????????????????
            mallOrder.setOrderStatus((byte) MallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            mallOrder.setUpdateTime(new Date());
            if (mallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            if (mallOrder.getOrderStatus().intValue() != MallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                MallException.fail("??????????????????????????????????????????");
            }
            mallOrder.setOrderStatus((byte) MallOrderStatusEnum.OREDER_PAID.getOrderStatus());
            mallOrder.setPayType((byte) payType);
            mallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            mallOrder.setPayTime(new Date());
            mallOrder.setUpdateTime(new Date());
            if (mallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }
}
