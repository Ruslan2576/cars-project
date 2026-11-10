package org.example.java512mukhumaevspringbootcrudsortpagination.repositories;

import org.example.java512mukhumaevspringbootcrudsortpagination.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByModelContainingIgnoreCase(String query, Pageable pageable);

    Page<Car> findByMarkaContainingIgnoreCase(String query, Pageable pageable);

    Page<Car> findByColorContainingIgnoreCase(String query, Pageable pageable);

    @Query("SELECT c FROM Car c WHERE CAST(c.type AS string) LIKE CONCAT('%', :q, '%')")
    Page<Car> findByTypeContaining(@Param("q") String q, Pageable p);


    @Query("SELECT c FROM Car c WHERE CAST(c.speed AS string) LIKE CONCAT('%', :q, '%')")
    Page<Car> findBySpeedContaining(@Param("q") String q, Pageable p);

    @Query("SELECT c FROM Car c WHERE CAST(c.place AS string) LIKE CONCAT('%', :q, '%')")
    Page<Car> findByPlaceContaining(@Param("q") String q, Pageable p);

    @Query("""
    SELECT c FROM Car c
    WHERE LOWER(c.model) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(c.marka) LIKE LOWER(CONCAT('%', :q, '%'))
       OR LOWER(c.color) LIKE LOWER(CONCAT('%', :q, '%'))
       OR CAST(c.type AS string)  LIKE CONCAT('%', :q, '%')
       OR CAST(c.speed AS string) LIKE CONCAT('%', :q, '%')
       OR CAST(c.place AS string) LIKE CONCAT('%', :q, '%')
    """)
    Page<Car> searchAll(@Param("q") String q, Pageable p);
}
