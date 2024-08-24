package com.kjlc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kjlc.app.Entity.Section;

public interface SectionRepository extends JpaRepository<Section,Long>{
   
    @Query(value="select * from section where testid = ?1", nativeQuery = true)
    List<Section> findByTestID(Long testID);
}
