package com.sinaungoding.smartcard.presensi.reader.data;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@AllArgsConstructor
@Slf4j
public class DataKartuByte {
    private byte[] nopol;//10
    private byte[] tanggal;//8
    private byte status_masuk;//1
    private byte kode_gate;//1
    private byte[] nip;//18
    private byte[] expired;//8
    private byte status_kartu;//1
    private byte[] data;

    public DataKartuByte() {
    }

    public DataKartuByte(byte[] nopol, byte[] tanggal, byte status_masuk, byte kode_gate, byte[] nip, byte[] expired, byte status_kartu) {
        try {
            data = new byte[47];
            this.nopol = nopol;
            this.tanggal = tanggal;
            this.status_masuk = status_masuk;
            this.kode_gate = kode_gate;
            this.nip = nip;
            this.expired = expired;
            this.status_kartu = status_kartu;
            System.arraycopy(this.nopol, 0, data, 0, 10);
            System.arraycopy(this.tanggal, 0, data, 10, 8);
            data[18] = status_masuk;
            data[19] = kode_gate;
            System.arraycopy(this.nip, 0, data, 20, 18);
            System.arraycopy(this.expired, 0, data, 38, 8);
            data[46] = status_kartu;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<byte[]> getBlocks() {
        byte[] temp = new byte[48];
        System.arraycopy(data, 0, temp, 0, data.length);
        List<byte[]> blocks = new ArrayList<>();
        int src = 0;

        for (int i = 0; i < 3; i++) {
            byte[] block = new byte[16];
            System.arraycopy(temp, src, block, 0, 16);
            blocks.add(block);
            src += 16;
        }
        return blocks;
    }
}
