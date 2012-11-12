package com.zhyfoundry.crm.web;

import javax.servlet.http.HttpServletRequest;

import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.utils.CommonUtils;

public abstract class PagingController extends BaseController {

	/** 默认分页大小(也可以全局固定成一个数) */
	protected abstract int getDefaultPageSize();

	/** 最大取得数据量(也可以全局固定成一个数) */
	protected abstract int getCountLimitation();

	protected Pager getPager(final HttpServletRequest req) {
		return new Pager(getPageNo(req), getPageSize(req), getOrder(req));
	}

	/** 当前处于第几页 */
	protected int getPageNo(final HttpServletRequest req) {
		return CommonUtils.String2Int(req.getParameter("page"), 1);
	}

	/** 排序条件 */
	protected String getOrder(final HttpServletRequest req) {
		return req.getParameter("order");
	}

	/** 每页返回多少行数据 */
	protected int getPageSize(final HttpServletRequest req) {
		final int pageSize = CommonUtils.String2Int(
				req.getParameter("pageSize"), getDefaultPageSize());
		return pageSize <= getCountLimitation() ? pageSize
				: getCountLimitation();
	}

	/** 计算从第几行开始 */
	@Deprecated
	protected int calcStartIndex(final int page, final int pageSize) {
		if (page <= 1) {
			return 0;
		} else {
			return (page - 1) * pageSize;
		}
	}

	/** 计算总页数 */
	@Deprecated
	protected int calcPageSum(final long count, final int pageSize) {
		if (count % pageSize > 0) {
			return (int) (count / pageSize + 1);
		} else {
			return (int) (count / pageSize);
		}
	}
}
