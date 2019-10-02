package com.sinaungoding.smartcard.presensi.dao;

import com.sinaungoding.smartcard.presensi.entity.Attendance;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttendanceDao extends PagingAndSortingRepository<Attendance, Long> {
}
