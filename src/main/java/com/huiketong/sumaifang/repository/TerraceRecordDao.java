package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.TerraceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TerraceRecordDao extends JpaRepository<TerraceRecord,Integer> {

    /**
     * 查询当天的带看次数
     * @param day
     * @return
     */
    @Query(value = "SELECT count(1) FROM terrace_record WHERE to_days(DATE_SUB(now(),INTERVAL ?1 DAY)) - to_days(happened_time) = 0 and rec_type = 1",nativeQuery = true)
    int findCurrentDaySeeNums(int day);

    /**
     * 查询当天的呼叫次数
     * @param day
     * @return
     */
    @Query(value = "SELECT count(1) FROM terrace_record WHERE to_days(DATE_SUB(now(),INTERVAL ?1 DAY)) - to_days(happened_time) = 0 and rec_type = 2",nativeQuery = true)
    int findCurrentDayCallNums(int day);


    /**
     * 查询当天的关注人数
     * @param day
     * @return
     */
    @Query(value = "SELECT count(1) FROM terrace_record WHERE to_days(DATE_SUB(now(),INTERVAL ?1 DAY)) - to_days(happened_time) = 0 and rec_type = 3",nativeQuery = true)
    int findCurrentDayFollowNums(int day);

    /**
     * 查询当天的浏览人数
     * @param day
     * @return
     */
    @Query(value = "SELECT count(1) FROM terrace_record WHERE to_days(DATE_SUB(now(),INTERVAL ?1 DAY)) - to_days(happened_time) = 0 and rec_type = 1",nativeQuery = true)
    int findCurrentDayVisitNums(int day);
}
