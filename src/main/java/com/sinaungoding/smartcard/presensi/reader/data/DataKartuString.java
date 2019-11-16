package com.sinaungoding.smartcard.presensi.reader.data;

import com.sinaungoding.smartcard.presensi.reader.util.ConvertUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class DataKartuString {
    private String nopol;//10
    private long tanggal;//4
    private byte statusMasuk;//1
    private byte kodeGate;//1
    private String nip;//18
    private long expired;//4
    private byte statusKartu;//1

    public DataKartuString() {
    }

    public DataKartuString(byte[] data) {
        try {
            byte[] nopol = new byte[10];
            byte[] tanggal = new byte[4];
            byte[] nip = new byte[18];
            byte[] expired = new byte[4];
            System.arraycopy(data, 0, nopol, 0, 10);
            System.arraycopy(data, 10, tanggal, 0, 4);
            statusMasuk = data[14];
            kodeGate = data[15];
            System.arraycopy(data, 16, nip, 0, 18);
            System.arraycopy(data, 34, expired, 0, 4);
            statusKartu = data[38];

            this.nopol = new String(ConvertUtil.trim(nopol));
            this.tanggal = ConvertUtil.bytesToLong(tanggal);
            this.nip = new String(nip);
            this.expired = ConvertUtil.bytesToLong(expired);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
