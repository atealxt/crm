package com.zhyfoundry.crm.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhyfoundry.crm.utils.CommonUtils;

public abstract class BaseController {

    protected Log logger = LogFactory.getLog(getClass());

    /** 分页：默认分页大小(也可以全局固定成一个数) */
    protected abstract int getDefaultPageSize();

    /** 分页：最大取得数据量(也可以全局固定成一个数) */
    protected abstract int getCountLimitation();

    /** 分页：当前处于第几页 */
    protected int getPage(final HttpServletRequest req) {
        return CommonUtils.String2Int(req.getParameter("page"), 1);
    }

    /** 分页：每页返回多少行数据 */
    protected int getPageSize(final HttpServletRequest req) {
        final int pageSize = CommonUtils.String2Int(req.getParameter("pageSize"), getDefaultPageSize());
        return pageSize <= getCountLimitation() ? pageSize : getCountLimitation();
    }

    /** 分页：计算从第几行开始 */
    protected int calcStartIndex(final int page, final int pageSize) {
        if (page <= 1) {
            return 0;
        } else {
            return (page - 1) * pageSize;
        }
    }

    /** 分页：计算总页数 */
    protected int calcPageSum(final int count, final int pageSize) {
        if (count % pageSize > 0) {
            return count / pageSize + 1;
        } else {
            return count / pageSize;
        }
    }
}
