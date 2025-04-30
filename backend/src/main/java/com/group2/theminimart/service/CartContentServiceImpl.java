package com.group2.theminimart.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.group2.theminimart.dto.CartDto;
import com.group2.theminimart.entity.CartContent;
import com.group2.theminimart.entity.Product;
import com.group2.theminimart.entity.User;
import com.group2.theminimart.exception.CartContentAlreadyExistException;
import com.group2.theminimart.exception.CartContentNotFoundException;
import com.group2.theminimart.exception.CartNotFoundException;
import com.group2.theminimart.exception.ProductNotFoundException;
import com.group2.theminimart.exception.UserNotFoundException;
import com.group2.theminimart.mapper.CartMapper;
import com.group2.theminimart.repository.CartContentRepository;
import com.group2.theminimart.repository.ProductRepository;
import com.group2.theminimart.repository.UserRepository;

@Primary
@Service
public class CartContentServiceImpl implements CartContentService {

    private ProductRepository productRepository;
    private CartContentRepository cartContentRepository;
    private UserRepository userRepository;

    public CartContentServiceImpl(CartContentRepository cartContentRepository, UserRepository userRepository,
            ProductRepository productRepository) {
        this.cartContentRepository = cartContentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartContent createCartContent(CartContent cartContent) {
        return cartContentRepository.save(cartContent);
    }

    @Override
    public CartDto createCartContent(Long userId, CartDto cartDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Product product = productRepository.findById(cartDto.getId())
                .orElseThrow(() -> new ProductNotFoundException(cartDto.getId()));
        if (cartContentRepository.findByUserIdAndProductId(userId, product.getId()).isPresent()) {
            throw new CartContentAlreadyExistException(userId, product.getId());
        }
        return CartMapper.toDto(cartContentRepository.save(CartMapper.fromDto(cartDto, user, product)));

    }

    @Override
    public CartDto createCartContent(String username, CartDto cartDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Product product = productRepository.findById(cartDto.getId())
                .orElseThrow(() -> new ProductNotFoundException(cartDto.getId()));
        if (cartContentRepository.findByUserIdAndProductId(user.getId(), product.getId()).isPresent()) {
            throw new CartContentAlreadyExistException(user.getId(), product.getId());
        }
        return CartMapper.toDto(cartContentRepository.save(CartMapper.fromDto(cartDto, user, product)));
    }

    @Override
    public List<CartDto> getCartContents(Long userId) {
        List<CartContent> cart = cartContentRepository
                .findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return cart
                .stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CartDto> getCartContents(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<CartContent> cart = cartContentRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new UserNotFoundException(username));
        return cart
                .stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartDto getCartContent(Long userId) {
        return CartMapper
                .toDto(cartContentRepository.findById(userId).orElseThrow(() -> new CartNotFoundException(userId)));
    }

    @Override
    public List<CartDto> updateCart(Long userId, List<CartDto> cartDtoList) {
        List<CartContent> userCart = cartContentRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<CartContent> updatedCart = userCart
                .stream()
                .map(existingCartContent -> {
                    CartDto cartDto = cartDtoList.stream()
                            .filter(dto -> dto.getId().equals(existingCartContent.getProduct().getId()))
                            .findFirst()
                            .orElseThrow(() -> new CartContentNotFoundException(userId,
                                    existingCartContent.getProduct().getId()));
                    existingCartContent.setCount(cartDto.getQuantity());
                    existingCartContent.setTotal(cartDto.getTotal());

                    return cartContentRepository.save(existingCartContent);
                })
                .collect(Collectors.toList());

        return updatedCart
                .stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<CartDto> updateCart(String username, List<CartDto> cartDtoList) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException());
    
        List<CartContent> userCart = cartContentRepository.findByUserId(user.getId())
            .orElseThrow(() -> new UserNotFoundException(user.getId()));
    
        // Create a map of existing cart content
        Map<Long, CartContent> existingCartMap = userCart.stream()
            .collect(Collectors.toMap(cartContent -> cartContent.getProduct().getId(), cartContent -> cartContent));
    
        // Process new items and update existing ones
        List<CartContent> updatedCart = cartDtoList.stream()
            .map(cartDto -> {
                Product product = productRepository.findById(cartDto.getId())
                    .orElseThrow(() -> new ProductNotFoundException(cartDto.getId()));
                CartContent existingCartContent = existingCartMap.get(cartDto.getId());
    
                if (existingCartContent != null) {
                    if (cartDto.getQuantity() == 0) {																// ✅ Remove from DB if quantity is 0
                        cartContentRepository.delete(existingCartContent);
                        return null;
                    }
                    existingCartContent.setCount(cartDto.getQuantity());
                    existingCartContent.setTotal(cartDto.getTotal());
                    return cartContentRepository.save(existingCartContent);
                } else {
                    CartContent newCartContent = new CartContent();
                    newCartContent.setUser(user);
                    newCartContent.setProduct(product);
                    newCartContent.setCount(cartDto.getQuantity());
                    newCartContent.setTotal(cartDto.getTotal());
                    return cartContentRepository.save(newCartContent);
                }
            })
            .collect(Collectors.toList());
    
        // Remove items that are no longer in the cart
        userCart.stream()
            .filter(cartContent -> cartDtoList.stream().noneMatch(dto -> dto.getId().equals(cartContent.getProduct().getId())))
            .forEach(cartContentRepository::delete);
    
        return updatedCart.stream().map(CartMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CartDto updateCartContent(Long userId, Long productId, CartDto cartDto) {
        CartContent exisitingCartContent = cartContentRepository
                .findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new CartContentNotFoundException(userId, productId));
        exisitingCartContent.setCount(cartDto.getQuantity());
        exisitingCartContent.setTotal(cartDto.getTotal());
        return CartMapper.toDto(cartContentRepository.save(exisitingCartContent));

    }

    @Override
    public CartDto updateCartContent(String username, Long productId, CartDto cartDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        CartContent exisitingCartContent = cartContentRepository
                .findByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() -> new CartContentNotFoundException(user.getId(), productId));
        exisitingCartContent.setCount(cartDto.getQuantity());
        exisitingCartContent.setTotal(cartDto.getTotal());
        return CartMapper.toDto(cartContentRepository.save(exisitingCartContent));
    }

    @Override
    public void deleteCart(Long userId) {
        List<CartContent> cart = cartContentRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        cartContentRepository.deleteAll(cart);
    }

    @Override
    public void deleteCart(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<CartContent> cart = cartContentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        cartContentRepository.deleteAll(cart);
    }

    @Override
    public void deleteCartContent(Long userId, Long productId) {
        CartContent cartContent = cartContentRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new CartContentNotFoundException(userId, productId));
        cartContentRepository.delete(cartContent);
    }

    @Override
    public void deleteCartContent(String username, Long productId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        CartContent cartContent = cartContentRepository.findByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() -> new CartContentNotFoundException(user.getId(), productId));
        cartContentRepository.delete(cartContent);
    }
}
