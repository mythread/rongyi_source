package com.rongyi.nasdaq.common.pagination;

import java.util.ArrayList;

/**
 * @author jiejie 2014年5月21日 下午5:09:01
 */
public class PaginationList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 3198909484620898963L;
    private Pagination        query;

    public PaginationList(Pagination query) {
        super();
        this.query = query;
    }

    public Pagination getQuery() {
        return query;
    }

    public void setQuery(Pagination query) {
        this.query = query;
    }

}
