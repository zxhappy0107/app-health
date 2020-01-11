package cn.anshirui.store.appdevelop.util;

import com.github.pagehelper.PageInfo;

public class PageUtils {

	/**
	 * 将分页信息封装到统一的接口
	 * 
	 * @param pageRequest
	 * @param
	 * @return
	 */
	public static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
		PageResult pageResult = new PageResult();
		pageResult.setPageNum(pageInfo.getPageNum());
		pageResult.setPageSize(pageInfo.getPageSize());
		pageResult.setCount(pageInfo.getTotal());
		pageResult.setPageSum(pageInfo.getPages());
		pageResult.setData(pageInfo.getList());
		return pageResult;
	}
	
}
