package com.huiketong.sumaifang.service;

public interface TerraceRecordService {

    /**
     * 当天带看次数
     */
   int getSeeNums(int day);

    /**
     *
     */

    int getCallNums(int day);

    /**
     *
     */
    int getFollowNums(int day);

    int getVisitNums(int day);
}
