package com.kjlc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kjlc.app.Entity.Result;

public interface ResultRepository extends JpaRepository<Result,Long>{

    @Query(value = "select * from result where testid = ?1 ORDER BY resultid DESC", nativeQuery=true)
    public List<Result> findByTestId(Long id);

    @Query(value="select * from result where userid = ?1 ORDER BY resultid DESC", nativeQuery = true)
    public List<Result> findByUserID(Long id);
 
    @Query(value="select * from result where userid = ?1 and testid = ?2 ORDER BY resultid DESC", nativeQuery = true)
    public List<Result> findByUserID(Long id, Long testID);

    @Query(value="SELECT r.*\n" + //
                "FROM result r\n" + //
                "JOIN (\n" + //
                "    SELECT testID, MAX(score) AS max_score\n" + //
                "    FROM result\n" + //
                "    GROUP BY testID\n" + //
                "    HAVING MAX(score) > 40\n" + //
                ") max_scores ON r.testID = max_scores.testID AND r.score = max_scores.max_score\n" + //
                "WHERE r.userid = 1409;", nativeQuery = true)
    public List<Result> findPassedTests(Long userID);

    @Modifying
    @Query(value = "update result set note=?1, state = 1 where resultid = ?2 ", nativeQuery = true)
    Integer addNote(String note, Long id);
}
