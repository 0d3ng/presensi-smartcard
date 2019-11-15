package com.sinaungoding.smartcard.presensi;

import com.sinaungoding.smartcard.presensi.reader.data.DataKartuByte;
import com.sinaungoding.smartcard.presensi.reader.data.DataKartuString;
import com.sinaungoding.smartcard.presensi.reader.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class DataKartuTest {
    @Test
    public void prepareDataTest() {
        String nopol = "AB2039YQ";
        byte[] np = new byte[10];
        System.arraycopy(nopol.getBytes(), 0, np, 0, nopol.length());
        long dd = System.currentTimeMillis();
        byte[] longToBytes = ConvertUtil.longToBytes(dd);
        for (byte b : longToBytes) {
            System.out.print(b);
        }

        System.out.println();
        log.info("" + longToBytes.length);
        log.info(ConvertUtil.byteArrayToHex(longToBytes, false));
        log.info("" + dd);

        System.out.println();
        byte masuk = 1;
        byte gate = 10;
        String nip = "198911082019031020";
        byte status_kartu = 1;
        log.info(nopol);
        log.info("" + dd);
        log.info(nip);
        log.info("" + dd);
        DataKartuByte kartuByte = new DataKartuByte(np, longToBytes, masuk, gate, nip.getBytes()
                , longToBytes, status_kartu);
        log.info(ConvertUtil.byteArrayToHex(kartuByte.getData(), true));
        log.info("");
        List<byte[]> blocks = kartuByte.getBlocks();
        for (byte[] block : blocks) {
            log.info(ConvertUtil.byteArrayToHex(block, true));
        }
        log.info("");
        DataKartuString kartuString = new DataKartuString(kartuByte.getData());
        log.info(kartuString.getNopol());
        log.info("" + kartuString.getTanggal());
        log.info("" + kartuString.getStatusMasuk());
        log.info("" + kartuString.getKodeGate());
        log.info("" + kartuString.getNip());
        log.info("" + kartuString.getExpired());
        log.info("" + kartuString.getStatusKartu());
    }
}
