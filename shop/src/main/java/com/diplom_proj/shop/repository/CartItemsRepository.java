package com.diplom_proj.shop.repository;

import com.diplom_proj.shop.entity.CartItems;
import com.diplom_proj.shop.entity.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {

    boolean existsByCarts_CartsIdAndProduct_ProductId(Integer cartId, Integer productId);

    @Query("SELECT c.product.productId FROM CartItems c WHERE c.carts.cartsId = :cart_id")
    List<Integer> getAllProductsIdsInCartByCartId(@Param("cart_id") Integer cartId);

    @Transactional
    Integer deleteByCarts_CartsIdAndProduct_ProductId(Integer cartsId, Integer productId);

    @Query("SELECT c.product FROM CartItems c WHERE c.carts.cartsId = :cart_id")
    List<Products> getAllProductsInCartByCartId(@Param("cart_id") Integer carts);

    @Query("SELECT COALESCE(SUM(c.product.productPrice * c.quantityProductInCartItem), 0) FROM CartItems c WHERE c.carts.cartsId = :cart_id")
    Integer getSumOfPricesByCartId(@Param("cart_id") Integer cartId);
    @Query("SELECT c.quantityProductInCartItem FROM CartItems c WHERE c.carts.cartsId = :cartId AND c.product.productId = :productId")
    Integer findQuantityByCartAndProduct(@Param("cartId") Integer cartId, @Param("productId") Integer productId);
    CartItems findCartItemsByCarts_CartsIdAndProduct_ProductId(Integer cartsId, Integer productId);
}
