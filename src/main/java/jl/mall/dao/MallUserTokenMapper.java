
package jl.mall.dao;

import jl.mall.common.Mapper;
import jl.mall.entity.MallUserToken;

public interface MallUserTokenMapper extends Mapper<MallUserToken> {


    MallUserToken selectByToken(String token);

}