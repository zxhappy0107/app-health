package cn.anshirui.store.appdevelop.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页请求
 */
public class PageRequest {
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

}
