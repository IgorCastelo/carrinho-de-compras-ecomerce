package com.nerd.shopping_cart_backend.service.product;

import com.nerd.shopping_cart_backend.exceptions.ProductNotFoundException;
import com.nerd.shopping_cart_backend.model.Category;
import com.nerd.shopping_cart_backend.model.Product;
import com.nerd.shopping_cart_backend.repository.CategoryRepository;
import com.nerd.shopping_cart_backend.repository.ProductRepository;
import com.nerd.shopping_cart_backend.repository.request.AddProductRequest;
import com.nerd.shopping_cart_backend.repository.request.ProductUpdaterequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Product addProduct(AddProductRequest request){
        //check if the category is  found in the DB
        //if yes, set it as the new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }
    public Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );

    }

    @Override
    public Product getProductbyId(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()-> {throw new ProductNotFoundException("Product not Found");});
    }

    @Override
    public Product updateProduct( ProductUpdaterequest productUpdaterequest, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct-> updateExistingProduct(existingProduct, productUpdaterequest))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }
    private Product updateExistingProduct(Product existingProduct, ProductUpdaterequest productUpdaterequest){
        existingProduct.setName(productUpdaterequest.getName());
        existingProduct.setBrand(productUpdaterequest.getBrand());
        existingProduct.setPrice(productUpdaterequest.getPrice());
        existingProduct.setInventory(productUpdaterequest.getInventory());
        existingProduct.setDescription(productUpdaterequest.getDescription());

        Category category = categoryRepository.findByName(productUpdaterequest.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
