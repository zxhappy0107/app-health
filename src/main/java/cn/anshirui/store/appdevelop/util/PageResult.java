package cn.anshirui.store.appdevelop.util;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页返回结果
 */
public class PageResult {
	/**
	 * 当前页码
	 */
	@Setter
	@Getter
	private int pageNum;
	/**
	 * 每页数量
	 */
	@Setter
	@Getter
	private int pageSize;
	/**
	 * 记录总数
	 */
	@Setter
	@Getter
	private long count;
	/**
	 * 页码总数
	 */
	@Setter
	@Getter
	private int pageSum;
	/**
	 * 数据模型
	 */
	@Setter
	@Getter
	private List<?> data;

}
