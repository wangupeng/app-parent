package com.cars.util.page;

import com.cars.util.global.GlobalConst;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by wangyupeng on 2017/8/18.
 */
public class Page implements Serializable{
    @Transient
    private Integer pageNum = 1;//当前页(传入的数据)
    @Transient
    private Integer pageSize = GlobalConst.PAGESIZE;//每页显示条数
    public Page() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
