package com.sinaungoding.smartcard.presensi;

import com.sinaungoding.smartcard.presensi.dao.AttendanceDao;
import com.sinaungoding.smartcard.presensi.dao.MahasiswaDao;
import com.sinaungoding.smartcard.presensi.entity.Attendance;
import com.sinaungoding.smartcard.presensi.entity.Mahasiswa;
import com.sinaungoding.smartcard.presensi.reader.ACR122U;
import com.sinaungoding.smartcard.presensi.util.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PresensiApplicationTests {

    @Autowired
    MahasiswaDao mahasiswaDao;
    @Autowired
    AttendanceDao attendanceDao;

    @Ignore
    @Test
    public void insertTest() {
        Mahasiswa mahasiswa = new Mahasiswa();
//        mahasiswa.setNim("075410099");
//        mahasiswa.setJurusan("Teknologi Informasi");
//        mahasiswa.setProdi("DIV Teknik Informatika");
//        mahasiswa.setNama("Uwais Al Qarni");
//        mahasiswa.setSerial("82DBDA99");
//        mahasiswaDao.save(mahasiswa);

        mahasiswa.setNim("075410100");
        mahasiswa.setJurusan("Teknologi Informasi");
        mahasiswa.setProdi("DIII Manajemen Informatika");
        mahasiswa.setNama("Fulan");
        mahasiswa.setSerial("1EA541BC");
        mahasiswaDao.save(mahasiswa);
    }

    @Test
//    @Ignore
    public void presensiTest() {
        ACR122U acr122U = new ACR122U();
//        new Thread(() -> {
        String uid = null;
        while (true) {
            try {
                String uuid = acr122U.getUID();
                log.info(uuid);
                if (!uuid.equals(uid)) {
                    Mahasiswa mhs = mahasiswaDao.findBySerial(uuid);
                    if (mhs != null) {
                        Attendance attendance = new Attendance();
                        attendance.setDateAttend(new Date());
                        attendance.setMahasiswa(mhs);
                        attendanceDao.save(attendance);
                        log.info(mhs.toString());
                        log.info("Presensi berhasil");
                    } else
                        log.error("Presensi gagal!");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                        break;
                    }
                    uid = uuid;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                uid = null;
            }
        }
//        }).start();
    }

    @Test
    @Ignore
    public void readUIDTest() {
        ACR122U acr122U = new ACR122U();
        while (true) {
            try {
                String uid = acr122U.getUID();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Test
    public void readCardTest() {
        ACR122U acr122U = new ACR122U();
        String UID = null;
        while (true) {
            try {
                String uid = acr122U.getUID();
                if (!uid.equals(UID)) {
                    if (acr122U.AuthBlock((byte) 0x00, ACR122U.defaultKeyA, (byte) 0x60)) {
                        byte[] readBlock = acr122U.ReadBlock((byte) 0x01);
                        log.info("" + HexUtils.bytesToHexString(readBlock));
                        byte[] blockName = Arrays.copyOfRange(readBlock, 0, 9);
                        log.info("" + new String(blockName));

                        //write data
//                        String name = "NOPRIANTO";
//                        if (acr122U.WriteBlock((byte) 0x01, name.getBytes())) {
//                            log.info("Write success");
//                        }
                    }
                }
                UID = uid;
            } catch (Exception e) {
                log.error(e.getMessage());
                UID = null;
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
