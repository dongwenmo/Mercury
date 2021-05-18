package com.cn.momo.common;

import java.util.List;

/**
 * @author dongwenmo
 * @create 2020-04-24 20:12
 */
public interface IBaseService<T> {
	// 根据实体中的属性值进行查询，查询条件使用等号
	List<T> select(T t);

	// 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	T selectByPrimaryKey(Object key);

	// 查询全部结果，select(null)方法能达到同样的效果
	List<T> selectAll();

	// 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	T selectOne(T t);

	// 根据实体中的属性查询总数，查询条件使用等号
	int selectCount(T t);

	// 保存一个实体，null的属性也会保存，不会使用数据库默认值
	int insert(T t);

	// 保存一个实体，null的属性不会保存，会使用数据库默认值
	int insertSelective(T t);

	// 根据主键更新实体全部字段，null值会被更新
	int updateByPrimaryKey(T t);

	// 根据主键更新属性不为null的值
	int updateByPrimaryKeySelective(T t);

	// 根据实体属性作为条件进行删除，查询条件使用等号
	int delete(T t);

	// 根据主键字段进行删除，方法参数必须包含完整的主键属性
	int deleteByPrimaryKey(Object key);
}

