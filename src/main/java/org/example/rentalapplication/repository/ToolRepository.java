package org.example.rentalapplication.repository;

import org.example.rentalapplication.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool getToolsByToolCode(String toolCode);
}
