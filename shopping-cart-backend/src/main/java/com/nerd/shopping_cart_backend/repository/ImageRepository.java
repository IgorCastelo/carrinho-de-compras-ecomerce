package com.nerd.shopping_cart_backend.repository;

import com.nerd.shopping_cart_backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
