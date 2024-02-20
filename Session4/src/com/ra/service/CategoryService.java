package com.ra.service;

import com.ra.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    List<Category> findByName(String name);
    Category insert(Category category);
    void update(Category category);
    void delete(int id);
    Category findById(int id);
    void updateById(int id);

}
