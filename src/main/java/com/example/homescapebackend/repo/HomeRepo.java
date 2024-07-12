package com.example.homescapebackend.repo;

import com.example.homescapebackend.entity.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeRepo extends JpaRepository<Home, Integer> {

    @Query("SELECT DISTINCT h.city FROM Home h")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT h.type FROM Home h")
    List<String> findDistinctPropertyTypes();

    @Query("SELECT h FROM Home h WHERE "
            + "(:city IS NULL OR h.city = :city) AND "
            + "(:propertyType IS NULL OR h.type = :propertyType) AND "
            + "(:minPrice IS NULL OR h.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR h.price <= :maxPrice) ")

    List<Home> filterHomes(String city, String propertyType, Long minPrice, Long maxPrice);
}
