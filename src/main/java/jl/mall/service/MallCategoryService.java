
package jl.mall.service;

import jl.mall.entity.GoodsCategory;
import jl.mall.util.PageQueryUtil;
import jl.mall.util.PageResult;
import jl.mall.vo.MallIndexCategoryVO;

import java.util.List;

public interface MallCategoryService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    GoodsCategory getGoodsCategoryById(Long id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 根据parentId和level获取分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);



    public List<MallIndexCategoryVO> getCategoriesForIndex();
}
