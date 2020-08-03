package jl.mall.param;

import jl.mall.util.PageQueryUtil;
import lombok.Data;
import org.thymeleaf.util.StringUtils;

import java.util.Map;

@Data
public class MallOrderItemSearchParam extends PageQueryUtil {

    private String goodsName;


    public MallOrderItemSearchParam(Map<String, Object> params) {
        super(params);

        //查询参数

        if(!StringUtils.isEmpty(params.get("goodsName").toString())) {
            this.goodsName = params.get("goodsName").toString();
        }
    }

    public MallOrderItemSearchParam() {
        super();
    }


}
