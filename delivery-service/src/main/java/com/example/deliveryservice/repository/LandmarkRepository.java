package com.example.deliveryservice.repository;

import com.example.deliveryservice.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, Integer> {

    List<Landmark> findAllByCityId(Integer id);
}
