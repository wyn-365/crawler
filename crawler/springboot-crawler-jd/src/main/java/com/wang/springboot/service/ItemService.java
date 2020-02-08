package com.wang.springboot.service;

import com.wang.springboot.entities.Item;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王一宁
 * @date 2020/2/8 12:31
 */
public interface ItemService {
    //保存商品
    public void save(Item item);

    //根据条件查询商品
    public List<Item> findAll(Item item);
}
