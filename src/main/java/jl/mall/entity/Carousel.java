
package jl.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "jl_mall_carousel")
@ToString
public class Carousel extends BaseEntity {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name="carousel_id")
    private Integer carouselId;

    @Column(name="carousel_url")
    private String carouselUrl;

    @Column(name="redirect_url")
    private String redirectUrl;

    @Column(name="carousel_rank")
    private Integer carouselRank;

    @Column(name="status")
    private Byte status = 0;

}