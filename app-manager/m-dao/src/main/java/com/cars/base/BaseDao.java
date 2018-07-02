package com.cars.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Mapper接口：基本的增、删、改、查方法,
 * ！！！！！！！！！注意：不能被mapperScannerConfigurer扫描到！！！！！！！！！！！
 * MySqlMapper：针对MySQL的额外补充接口，支持批量插入
 * Created by wangyupeng on 2018/5/3 22:30
 */
public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {
}
