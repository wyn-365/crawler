package com.wang.springboot.dao;

import com.wang.springboot.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王一宁
 * @date 2020/2/8 12:30
 */
public interface ItemDao extends JpaRepository<Item,Long> {
}
