package jl.mall.param;

import jl.mall.util.PageQueryUtil;
import lombok.Data;
import org.thymeleaf.util.StringUtils;

import java.util.Map;

@Data
public class MallGoodsSearchParam extends PageQueryUtil {


    private Integer goodsId;

    private Byte goodsSellStatus;

    public MallGoodsSearchParam(Map<String, Object> params) {
        super(params);

        //查询参数
        if(!StringUtils.isEmpty(params.get("goodsId").toString())) {
            this.goodsId = Integer.parseInt(params.get("goodsId").toString());
        }
        if(!StringUtils.isEmpty(params.get("goodsSellStatus").toString())) {
            this.goodsSellStatus = Byte.parseByte(params.get("goodsSellStatus").toString());
        }

    }

    public MallGoodsSearchParam() {
        super();
    }

    public MallGoodsSearchParam(Map<String, Object> params, Byte orderStatus) {
        super(params);
        this.goodsSellStatus = orderStatus;
    }
}
