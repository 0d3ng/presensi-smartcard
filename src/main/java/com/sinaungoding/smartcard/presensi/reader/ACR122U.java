package com.sinaungoding.smartcard.presensi.reader;

import com.sinaungoding.smartcard.presensi.util.HexUtils;
import lombok.extern.slf4j.Slf4j;

import javax.smartcardio.*;
import java.util.Arrays;

@Slf4j
public class ACR122U {
    private CardChannel channel;

    public ACR122U() {
        CardTerminals terminals = TerminalFactory.getDefault().terminals();
        CardTerminal terminal = null;
        try {
            terminal = terminals.list().get(0);
            System.out.println(terminal.getName());
            Card card = terminal.connect("*");
            byte[] historicalBytes = card.getATR().getBytes();
            System.out.println(HexUtils.bytesToHexString(historicalBytes));
            channel = card.getBasicChannel();
        } catch (CardException e) {
            e.printStackTrace();
        }

    }

    public String getUID() {
        CommandAPDU command = new CommandAPDU(new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0xD4, (byte) 0x4A, (byte) 0x01, (byte) 0x00});
        ResponseAPDU response = null;
        try {
            response = channel.transmit(command);
            if (response.getSW1() == 0x90) {
                log.info(HexUtils.bytesToHexString(response.getData()));
                log.info(HexUtils.bytesToHexString(response.getBytes()));
                byte[] data = response.getData();
                data = Arrays.copyOfRange(data, 0x08, data.length);
                String UID = HexUtils.bytesToHexString(data);
                log.info(UID);
                return UID;
            }
        } catch (CardException e) {
            e.printStackTrace();
        }
        return null;
    }
}
