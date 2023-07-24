package com.baegwon.bwm.Controller.ApiController;

import com.baegwon.bwm.Model.Dto.ProductDetailDto;
import com.baegwon.bwm.Model.Dto.ProductDto;
import com.baegwon.bwm.Model.Product;
import com.baegwon.bwm.Model.Size;
import com.baegwon.bwm.Repository.ProductRepository;
import com.baegwon.bwm.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    // Home.vue
    @GetMapping("/product/get/slider/item/{type}")
    public List<ProductDto> slider(@PathVariable String type) {
        List<Product> productList = productService.getSliderItems(type);

        List<ProductDto> productDtoList = productList.stream().
                map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        return productDtoList;
    }

    // List.vue
    @GetMapping("/product/get/list/item/{category}")
    public List<ProductDto> list(@PathVariable String category) {
        List<Product> productList = productService.getListItems(category);

        List<ProductDto> productDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        return productDtoList;
    }

    // Detail.vue
    @GetMapping("/product/get/detail/{product_id}")
    public ProductDetailDto detail(@PathVariable Long product_id) {
        return productService.getProductDetail(product_id);
    }

    @DeleteMapping("product/delete/{product_id}")
    public ResponseEntity<?> delete(@PathVariable Long product_id) {
        productService.deleteProduct(product_id);

        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
