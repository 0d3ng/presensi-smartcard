package com.sinaungoding.smartcard.presensi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAttend;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nim")
    private Mahasiswa mahasiswa;
}
