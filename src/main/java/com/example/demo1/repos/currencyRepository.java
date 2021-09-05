package com.example.demo1.repos;

import com.example.demo1.Model.currency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @create 05.09.2021 17:25
 */

public interface currencyRepository extends JpaRepository<currency, Long> {

}