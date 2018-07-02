package com.cars.service.base;

import com.cars.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyupeng on 2018/3/31 11:25
 */
public abstract class BaseService<T> {
    //获取当前登录用户
    //protected SysUser userSession = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userSession");

    @Autowired
    private BaseDao<T> baseDao;

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param t
     * @return
     */
    public int insert(T t){
        return baseDao.insertSelective(t);
    }

    /**
     * 根据主键更新属性不为null的值
     * @param t
     * @return
     */
    public int update(T t){
        return baseDao.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     * @param t
     * @return
     */
    public int delete(T t){
        return baseDao.delete(t);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param key
     * @return
     */
    public int deleteByPrimaryKey(Object key){
        return baseDao.deleteByPrimaryKey(key);
    }

    /**
     * 查询全部结果
     * @return
     */
    public List<T> selectAll(){
        return baseDao.selectAll();
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param t
     * @return
     */
    public List<T> select(T t){
        return baseDao.select(t);
    }
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public T selectById(Serializable id){
        return (T) baseDao.selectByPrimaryKey(id);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param t
     * @return
     */
    public T selectOne(T t){
        return (T) baseDao.selectOne(t);
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     * @param t
     * @return
     */
    public int selectCount(T t){
        return baseDao.selectCount(t);
    }

    /**
     * 根据主键查询是否存在
     * @param key
     * @return
     */
    public boolean existsWithPrimaryKey(Object key){
        return baseDao.existsWithPrimaryKey(key);
    }

    /**
     * ******************通用Mapper接口,Example查询----Example 用法*******************
     * 1、查询
     * Example example = new Example(Country.class);
     * example.createCriteria().andGreaterThan("id", 100).andLessThan("id",151);
     * example.or().andLessThan("id", 41);
     * List<Country> countries = mapper.selectByExample(example);
     * -----SELECT id,countryname,countrycode FROM country WHERE ( id > ? and id < ? ) or ( id < ? ) ORDER BY id desc
     *
     * 2、 动态 SQL
     * Example example = new Example(Country.class);
     * Example.Criteria criteria = example.createCriteria();
     * if(query.getCountryname() != null){
     *     criteria.andLike("countryname", query.getCountryname() + "%");
     * }
     * if(query.getId() != null){
     *     criteria.andGreaterThan("id", query.getId());
     * }
     * List<Country> countries = mapper.selectByExample(example);
     * -----SELECT id,countryname,countrycode FROM country WHERE ( countryname like ? ) ORDER BY id desc
     *  3、排序
     *  Example example = new Example(Country.class);
     *  example.orderBy("id").desc().orderBy("countryname").orderBy("countrycode").asc();
     *  List<Country> countries = mapper.selectByExample(example);
     *  ------SELECT id,countryname,countrycode FROM country order by id DESC,countryname,countrycode ASC
     *
     *  4、去重
     *  CountryExample example = new CountryExample();
     * //设置 distinct
     * example.setDistinct(true);
     * example.createCriteria().andCountrynameLike("A%");
     * example.or().andIdGreaterThan(100);
     * List<Country> countries = mapper.selectByExample(example);
     * ------SELECT distinct id,countryname,countrycode FROM country WHERE ( countryname like ? ) or ( Id > ? ) ORDER BY id desc
     *
     * 5、设置查询列
     * Example example = new Example(Country.class);
     * example.selectProperties("id", "countryname");
     * List<Country> countries = mapper.selectByExample(example);
     * ------SELECT id , countryname FROM country ORDER BY id desc
     *
     * 除了这里提到的方法外，还有很多其他的方法，可以查看 Example 源码进行了解。
     */

    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    public List<T> selectByExample(Object example){
        return baseDao.selectByExample(example);
    }

    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    public T selectOneByExample(Object example){
        return baseDao.selectOneByExample(example);
    }

    /**
     * 根据Example条件进行查询总数
     *
     * @param example
     * @return
     */
    public int selectCountByExample(Object example){
        return baseDao.selectCountByExample(example);
    }

    /**
     * 根据Example条件删除数据
     *
     * @param example
     * @return
     */
    public int deleteByExample(Object example){
        return baseDao.deleteByExample(example);
    }

    /**
     * 根据Example条件更新实体`record`包含的全部属性，null值会被更新
     *
     * @param record
     * @param example
     * @return
     */
    public int updateByExample(@Param("record") T record, @Param("example") Object example){
        return baseDao.updateByExample(record,example);
    }

    /**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     *
     * @param record
     * @param example
     * @return
     */
    public int updateByExampleSelective(@Param("record") T record, @Param("example") Object example){
        return baseDao.updateByExampleSelective(record,example);
    }

}
