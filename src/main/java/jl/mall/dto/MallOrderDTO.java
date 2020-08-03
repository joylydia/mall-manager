package jl.mall.dto;

import jl.mall.entity.MallOrder;
import jl.mall.entity.MallUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class MallOrderDTO extends MallOrder implements Serializable {

    //会员帐号
    private String loginName;

    //商品名称
    private String goodsName;

    public MallOrderDTO() {
    }

    public MallOrderDTO(String loginName,String goodsName) {
        this.loginName = loginName;
        this.goodsName = goodsName;
    }
}
