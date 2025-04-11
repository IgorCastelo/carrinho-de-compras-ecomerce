package com.nerd.shopping_cart_backend.service.category;

import com.nerd.shopping_cart_backend.exceptions.AlreadyExistsException;
import com.nerd.shopping_cart_backend.exceptions.ProductNotFoundException;
import com.nerd.shopping_cart_backend.exceptions.ResourceNotFoundException;
import com.nerd.shopping_cart_backend.model.Category;
import com.nerd.shopping_cart_backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoreService{
    final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        // 1 pode lançar um objeto vazio
        //verifica se a categoria existe
        //if exist save the category on repository
        //if category not exist returns alrady exist exption
        return Optional.ofNullable(category).filter((c ->!categoryRepository.existingByName(c.getName())))        //O filter no código é usado para filtrar objetos Optional de acordo com uma condição específica.
                .map(categoryRepository::save) //o filtro verifica se existe uma condição em um objeto optional, caso seja falsa ele retorna um objeto nulo
                .orElseThrow(()-> {
                    return new AlreadyExistsException(category.getName() + "alrady exist");
                });
    }

    @Override
    public Category updateCategory(Category category, Long id) {
       return Optional.ofNullable(getCategoryById(id)).map(oldCategory ->{                                      //map é usado para aplicar uma função a um valor encapsulado em um Optional e retornar um novo Optional com o resultado da função aplicada. Se o valor não estiver presente (ou seja, se o Optional for vazio), o método map simplesmente retorna um Optional vazio,
           oldCategory.setName(category.getName());
            return  categoryRepository.save(oldCategory);
       }).orElseThrow((()-> new ProductNotFoundException("Category not found!")));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, ()-> {throw new ResourceNotFoundException("Category not fund!");});
    }
}
