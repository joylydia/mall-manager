package jl.mall.param;

import jl.mall.util.PageQueryUtil;
import lombok.Data;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

@Data
public class MallOrderSearchParam extends PageQueryUtil {

    private Integer payType;

    private Integer orderStatus;

    private String orderNo;

    private String userName;

    private String goodsName;

    private Long userId;

    private List<Long> orderIds;

    public MallOrderSearchParam(Map<String, Object> params) {
        super(params);

        //查询参数
        if(!StringUtils.isEmpty(params.get("payType").toString())) {
            this.payType = Integer.parseInt(params.get("payType").toString());
        }
        if(!StringUtils.isEmpty(params.get("orderStatus").toString())) {
            this.orderStatus = Integer.parseInt(params.get("orderStatus").toString());
        }
        if(!StringUtils.isEmpty(params.get("orderNo").toString())) {
            this.orderNo = params.get("orderNo").toString();
        }
        if(!StringUtils.isEmpty(params.get("userName").toString())) {
            this.userName = params.get("userName").toString();
        }
        if(!StringUtils.isEmpty(params.get("goodsName").toString())) {
            this.goodsName = params.get("goodsName").toString();
        }

    }

    public MallOrderSearchParam() {
        super();
    }

    public MallOrderSearchParam(Map<String, Object> params, Integer orderStatus) {
        super(params);
        this.orderStatus = orderStatus;
    }
}
