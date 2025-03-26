package com.wellsfargo.developers.configuration;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KycResultRepository extends JpaRepository<com.wellsfargo.developers.configuration.KycResult, String> {
    // JpaRepository provides basic CRUD methods; add custom queries if needed
}
