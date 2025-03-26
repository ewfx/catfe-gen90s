package com.wf.contextaware.configuration;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KycResultRepository extends JpaRepository<com.wf.contextaware.configuration.KycResult, String> {
    // JpaRepository provides basic CRUD methods; add custom queries if needed
}
