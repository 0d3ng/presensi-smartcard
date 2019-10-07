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

    public String getUID() throws Exception {
        byte[] cmd =
                new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x09, (byte) 0xD4, (byte) 0x60, (byte) 0x01, (byte) 0x01,
                        (byte) 0x20, (byte) 0x23, (byte) 0x11, (byte) 0x04, (byte) 0x10, (byte) 0x00};
        CommandAPDU command = new CommandAPDU(cmd);
        ResponseAPDU response = null;
        try {
            response = channel.transmit(command);
            log.info(HexUtils.bytesToHexString(response.getData()));
            if (response.getSW1() == 0x90) {
                log.info(HexUtils.bytesToHexString(response.getBytes()));
                if (response.getData()[2] == 0x00) {
                    throw new NoTagException("No Tag");
                }

                byte[] data = response.getData();
                byte[] uid = null;
                switch (data[3]) {
                    case 0x10:
                        if (data[8] == 0x18) {
                            log.info("Mifare 4K");
                        } else {
                            log.info("Mifare 1K");
                        }
                        uid = new byte[data[9]];
                        uid = Arrays.copyOfRange(data, 10, data.length);
                        break;
                    case 0x20:
                        uid = new byte[data[9]];
                        log.info("ISO14443-4 Type A");
                        uid = Arrays.copyOfRange(data, 10, 17);
                        break;
                    case 0x23:
                        uid = new byte[data[9]];
                        log.info("ISO14443-4 Type B");
                        uid = data;
                        break;
                    default:
                        log.info("Other Tag");
                        uid = data;
                        break;
                }
                String UID = HexUtils.bytesToHexString(uid);
                log.info(UID);
                return UID;
            }
        } catch (CardException e) {
            throw e;
        }
        return null;
    }
}
