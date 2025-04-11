package com.nerd.shopping_cart_backend.service.image;

import com.nerd.shopping_cart_backend.dto.ImageDto;
import com.nerd.shopping_cart_backend.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long roductId);
    void updateImage(MultipartFile file, Long ImageId);


}
