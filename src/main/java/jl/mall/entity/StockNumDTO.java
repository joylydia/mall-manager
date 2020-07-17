
package jl.mall.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 库存修改所需实体
 */
@Data
public class StockNumDTO implements Serializable {
    private Long goodsId;

    private Integer goodsCount;

}
