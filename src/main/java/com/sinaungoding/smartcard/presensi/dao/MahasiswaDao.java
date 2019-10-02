package com.sinaungoding.smartcard.presensi.dao;

import com.sinaungoding.smartcard.presensi.entity.Mahasiswa;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MahasiswaDao extends PagingAndSortingRepository<Mahasiswa, Long> {
    Mahasiswa findBySerial(String serial);
}
