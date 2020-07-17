
package jl.mall.dao;


import jl.mall.common.Mapper;
import jl.mall.entity.MallUserAddress;

import java.util.List;

public interface MallUserAddressMapper  extends Mapper<MallUserAddress> {

    MallUserAddress getMyDefaultAddress(Long userId);

    List<MallUserAddress> findMyAddressList(Long userId);

}