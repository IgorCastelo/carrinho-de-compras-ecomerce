package com.nerd.shopping_cart_backend.service.image;

import com.nerd.shopping_cart_backend.dto.ImageDto;
import com.nerd.shopping_cart_backend.exceptions.ResourceNotFoundException;
import com.nerd.shopping_cart_backend.model.Image;
import com.nerd.shopping_cart_backend.model.Product;
import com.nerd.shopping_cart_backend.repository.ImageRepository;
import com.nerd.shopping_cart_backend.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No image find with id "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()-> {
                    throw new ResourceNotFoundException("Image not found with id"+ id);
                });

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductbyId(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file: files)
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDowloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDowloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDowloadUrl());
                savedImageDto.add(imageDto);
            }catch (IOException|SQLException e ){
                throw  new RuntimeException(e.getMessage());
            }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
            Image image = getImageById(imageId);

        try {
            image.setImage(new SerialBlob(file.getBytes()));
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
