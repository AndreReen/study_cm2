package com.coffeemachine.dao;

import java.util.Optional;

public interface DaoforSale<T,I> {

        Optional<I> save(T t);
}
