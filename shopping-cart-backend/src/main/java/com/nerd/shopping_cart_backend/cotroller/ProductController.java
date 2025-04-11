package com.nerd.shopping_cart_backend.cotroller;

import com.nerd.shopping_cart_backend.exceptions.ResourceNotFoundException;
import com.nerd.shopping_cart_backend.model.Product;
import com.nerd.shopping_cart_backend.repository.request.AddProductRequest;
import com.nerd.shopping_cart_backend.repository.request.ProductUpdaterequest;
import com.nerd.shopping_cart_backend.response.ApiResponse;
import com.nerd.shopping_cart_backend.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok().body(new ApiResponse("Found!", products));
    }

    @GetMapping("/product{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductbyId(productId);
            return ResponseEntity.ok().body(new ApiResponse("Found!", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok().body(new ApiResponse("Add product sucess!", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/product/{updateID/update}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdaterequest product, Long productId) {
        try {
            Product theProduct = productService.updateProduct(product, productId);
            return ResponseEntity.ok().body(new ApiResponse("Update product sucess!", theProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/product/{updateID/delete}")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestBody Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok().body(new ApiResponse("Update product sucess!", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/products/by/name")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No  product found!", null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Success!", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }

    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName, @PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok().body(new ApiResponse("Found!", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found!", null));
        }
    }
    @GetMapping("/products/by/brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@PathVariable String brandName) {
        try {
            List<Product> products = productService.getProductsByBrand(brandName);
            return ResponseEntity.ok().body(new ApiResponse("Found!", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found!", null));
        }
    }

    @GetMapping("/products/by/category")
    public ResponseEntity<ApiResponse> findProductsCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok().body(new ApiResponse("Found!", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found!", null));
        }
    }



@GetMapping("/products/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            return ResponseEntity.ok().body(new ApiResponse("Found!", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found!", null));
        }
    }
    @GetMapping("/products/category-and-brand")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try{
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok().body(new ApiResponse("Found!", productCount));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found!", null));
        }
    }
    }

