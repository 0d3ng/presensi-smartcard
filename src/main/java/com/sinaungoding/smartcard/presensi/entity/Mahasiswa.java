package com.sinaungoding.smartcard.presensi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Mahasiswa {
    @Id
    private String nim;
    @Column(unique = true)
    private String serial;
    private String nama;
    private String jurusan;
    private String prodi;
}
