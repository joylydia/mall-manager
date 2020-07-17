
package jl.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "jl_mall_index_config")
@Data
@ToString
public class IndexConfig extends BaseEntity {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="config_id")
    private Long configId;

    @Column(name="config_name")
    private String configName;

    @Column(name="config_type")
    private Byte configType;

    @Column(name="goods_id")
    private Long goodsId;

    @Column(name="redirect_url")
    private String redirectUrl;

    @Column(name="config_rank")
    private Integer configRank;

    @Column(name="status")
    private Byte status = 0;


}