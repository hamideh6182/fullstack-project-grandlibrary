package com.github.hamideh6182.repository;

import com.github.hamideh6182.model.Checkout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends MongoRepository<Checkout, String> {
}
