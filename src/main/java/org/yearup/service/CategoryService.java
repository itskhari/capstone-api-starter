package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories()
    {
        // get all categories
        return categoryRepository.findAll();
    }

    public Category getById(int categoryId)
    {
        // get category by id
        return categoryRepository.findById(categoryId).orElseThrow(()
        -> new RuntimeException("Category not found"));
    }

    public Category create(Category category)
    {
        // create a new category
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(int categoryId, Category category)
    {
        // update category and return the updated category
        Category existing = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setName(category.getName());

        return categoryRepository.save(existing);
    }

    @Transactional
    public void delete(int categoryId)
    {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }
        // delete category
        categoryRepository.deleteById(categoryId);
    }
}
