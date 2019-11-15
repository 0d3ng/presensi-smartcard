package com.sinaungoding.smartcard.presensi;

import com.sinaungoding.smartcard.presensi.reader.ACR122U;
import com.sinaungoding.smartcard.presensi.reader.data.DataKartuByte;
import com.sinaungoding.smartcard.presensi.reader.data.DataKartuString;
import com.sinaungoding.smartcard.presensi.reader.util.ConvertUtil;
import com.sinaungoding.smartcard.presensi.util.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
public class ReaderTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
//    @Ignore
    public void readCardTest() {
        ACR122U acr122U = new ACR122U();
        String UID = null;
        while (true) {
            try {
                String uid = acr122U.getUID();
                if (!uid.equals(UID)) {
                    if (acr122U.AuthBlock((byte) 0x02, ACR122U.defaultKeyA, (byte) 0x60)) {
//                        read data
                        for (int i = 0; i < 16; i++) {
                            byte[] readBlock = acr122U.ReadBlock((byte) i);
                            log.info("" + HexUtils.bytesToHexString(readBlock));
                        }
//                        byte[] blockName = Arrays.copyOfRange(readBlock, 0, readBlock.length);
//                        String name = new String(trim(blockName));
//                        log.info("" + name);

                        //write data
//                        String name = "NOPRIANTO";
//                        if (acr122U.WriteBlock((byte) 0x01, name.getBytes())) {
//                            log.info("Write success");
//                        }

                        //write data real
//                        String nopol = "AB2039YQ";
//                        byte[] np = new byte[10];
//                        System.arraycopy(nopol.getBytes(), 0, np, 0, nopol.length());
//                        long dd = System.currentTimeMillis();
//                        byte[] tanggal = ConvertUtil.longToBytes(dd);
//                        byte stIn = 1;
//                        byte gate = 10;
//                        String nip = "198911082019031020";
//                        byte stKartu = 1;
//                        DataKartuByte kartuByte = new DataKartuByte(np, tanggal, stIn, gate,
//                                nip.getBytes(), tanggal, stKartu);
//                        log.info("HAI...");
//                        log.info(ConvertUtil.byteArrayToHex(kartuByte.getData(), true));
//                        log.info("");
//
//                        log.info("" + acr122U.writeDataParkir(kartuByte));

//                        read data real
//                        byte[] data = new byte[48];
//                        log.info("" + acr122U.readDataParkir(data));
//                        log.info(ConvertUtil.byteArrayToHex(data, true));
//                        DataKartuString kartuString = new DataKartuString(data);
//                        log.info(kartuString.getNopol());
//                        log.info(DATE_FORMAT.format(new Date(kartuString.getTanggal())));
//                        log.info(kartuString.getNip());
//                        log.info(DATE_FORMAT.format(new Date(kartuString.getExpired())));
//                        log.info("" + kartuString.getKodeGate());
//                        log.info("" + kartuString.getStatusKartu());
//                        log.info("" + kartuString.getStatusMasuk());

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

    static byte[] trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }
}
