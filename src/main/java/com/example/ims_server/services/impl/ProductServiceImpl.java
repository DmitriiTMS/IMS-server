package com.example.ims_server.services.impl;

import com.example.ims_server.dtos.ProductDTO;
import com.example.ims_server.dtos.Response;
import com.example.ims_server.entitys.Category;
import com.example.ims_server.entitys.Product;
import com.example.ims_server.exceptions.NotFoundException;
import com.example.ims_server.mapper.ProductMapper;
import com.example.ims_server.repositories.CategoryRepository;
import com.example.ims_server.repositories.ProductRepository;
import com.example.ims_server.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/product-images/";

    //AFTER YOUR FRONTEND IS SETUP CHANGE THE IMAGE DIRECTORY TO YHE FRONTEND YOU ARE USING
    private static final String IMAGE_DIRECTORY_2 = "/Users/dennismac/phegonDev/ims-react/public/products/";

    @Override
    public Response saveProduct(ProductDTO productDTO, MultipartFile imageFile) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        //map our dto to product entity
        Product productToSave = Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        if (imageFile != null && !imageFile.isEmpty()) {
            log.info("Image file exist");
            String imagePath = saveImage(imageFile); //use this when you haven't setup your frontend
//            String imagePath = saveImage2(imageFile); //use this when you ave set up your frontend locally but haven't deployed to produiction

            System.out.println("IMAGE URL IS: " + imagePath);
            productToSave.setImageUrl(imagePath);
        }

        //save the product entity
        productRepository.save(productToSave);

        return Response.builder()
                .status(200)
                .message("Product successfully saved")
                .build();
    }

    @Override
    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) {
        //check if product exisit
        Product existingProduct = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        //check if image is associated with the product to update and upload
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile); //use this when you haven't setup your frontend
//            String imagePath = saveImage2(imageFile); //use this when you ave set up your frontend locally but haven't deployed to produiction

            System.out.println("IMAGE URL IS: " + imagePath);
            existingProduct.setImageUrl(imagePath);
        }

        //check if category is to be chanegd for the products
        if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category Not Found"));
            existingProduct.setCategory(category);
        }

        //check if product fields is to be changed and update
        if (productDTO.getName() != null && !productDTO.getName().isBlank()) {
            existingProduct.setName(productDTO.getName());
        }

        if (productDTO.getSku() != null && !productDTO.getSku().isBlank()) {
            existingProduct.setSku(productDTO.getSku());
        }

        if (productDTO.getDescription() != null && !productDTO.getDescription().isBlank()) {
            existingProduct.setDescription(productDTO.getDescription());
        }

        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
            existingProduct.setPrice(productDTO.getPrice());
        }

        if (productDTO.getStockQuantity() != null && productDTO.getStockQuantity() >= 0) {
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }
        //update the product
        productRepository.save(existingProduct);

        //Build our response
        return Response.builder()
                .status(200)
                .message("Product Updated successfully")
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<Product> productList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ProductDTO> productDTOList = productMapper.toProductDTOList(productList);

        return Response.builder()
                .status(200)
                .message("success")
                .products(productDTOList)
                .build();
    }

    @Override
    public Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        ProductDTO productDTO = productMapper.toProductDTO(product);

        return Response.builder()
                .status(200)
                .message("success")
                .product(productDTO)
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {
        Product prod =  productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
         productRepository.deleteById(id);

         String nameFile = extractValueBetween(prod.getImageUrl());

        deleteFile(nameFile);

        return Response.builder()
                .status(200)
                .message("Product Deleted successfully")
                .build();
    }

    @Override
    public Response searchProduct(String input) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(input, input);

        if (products.isEmpty()) {
            throw new NotFoundException("Product Not Found");
        }

        List<ProductDTO> productDTOList = productMapper.toProductDTOList(products);

        return Response.builder()
                .status(200)
                .message("success")
                .products(productDTOList)
                .build();
    }

    //this save to the root of your project
    private String saveImage(MultipartFile imageFile) {
        //validate image and check if it is greater than 1GIB
//        if (!imageFile.getContentType().startsWith("image/") || imageFile.getSize() > 1024 * 1024 * 1024) {
        // Проверка размера файла (максимум 5 ГБ)
            if (imageFile.getSize() > 5L * 1024 * 1024 * 1024) {

            throw new IllegalArgumentException("all files 5GIG is allowed");
        }

        //create the directory if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY);

        if (!directory.exists()) {
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        //Get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;

        try {
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile); //we are writing the image to this folder
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving Image: " + e.getMessage());
        }
        return imagePath;

    }

    private void deleteFile(String fileUniqueName) {
        // Проверяем, что уникальное имя файла не пустое
        if (fileUniqueName == null || fileUniqueName.isEmpty()) {
            throw new IllegalArgumentException("File unique name cannot be null or empty");
        }

        // Генерируем полный путь к файлу
        String filePath = IMAGE_DIRECTORY + fileUniqueName;

        // Создаем объект файла
        File file = new File(filePath);

        // Проверяем, существует ли файл
        if (!file.exists()) {
            log.warn("File does not exist: {}", filePath);
            return; // Файл уже удален или не существует
        }

        // Удаляем файл
        boolean isDeleted = file.delete();

        if (isDeleted) {
            log.info("File deleted successfully: {}", filePath);
        } else {
            log.error("Failed to delete file: {}", filePath);
        }

    }

    private String extractValueBetween(String fullPath) {
        // Ищем индекс начала подстроки "product-images/"
        int startIndex = fullPath.indexOf("product-images/");

        if (startIndex == -1) {
            throw new IllegalArgumentException("'product-images/' not found in the path");
        }

        // Извлекаем строку, которая идет после "product-images/"
        String valueAfterProductImages = fullPath.substring(startIndex + "product-images/".length());

        return valueAfterProductImages;
    }

    //This saved image to the public folder in your frontend
    //Use this if your have setup your frontend
    private String saveImage2(MultipartFile imageFile) {
        //validate image and check if it is greater than 1GIB
//        if (!imageFile.getContentType().startsWith("image/") || imageFile.getSize() > 1024 * 1024 * 1024) {
//            throw new IllegalArgumentException("Only image files under 1GIG is allowed");
//        }

        if (imageFile.getSize() > 5L * 1024 * 1024 * 1024) {
            throw new IllegalArgumentException("all files 5GIG is allowed");
        }


        //create the directory if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY_2);

        if (!directory.exists()) {
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        //Get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY_2 + uniqueFileName;

        try {
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile); //we are writing the image to this folder
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving Image: " + e.getMessage());
        }
        return "products/"+uniqueFileName;

    }

}
