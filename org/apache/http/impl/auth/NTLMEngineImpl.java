package org.apache.http.impl.auth;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.util.EncodingUtils;

final class NTLMEngineImpl implements NTLMEngine {
    static final String DEFAULT_CHARSET = "ASCII";
    protected static final int FLAG_NEGOTIATE_128 = 536870912;
    protected static final int FLAG_NEGOTIATE_ALWAYS_SIGN = 32768;
    protected static final int FLAG_NEGOTIATE_KEY_EXCH = 1073741824;
    protected static final int FLAG_NEGOTIATE_NTLM = 512;
    protected static final int FLAG_NEGOTIATE_NTLM2 = 524288;
    protected static final int FLAG_NEGOTIATE_SEAL = 32;
    protected static final int FLAG_NEGOTIATE_SIGN = 16;
    protected static final int FLAG_TARGET_DESIRED = 4;
    protected static final int FLAG_UNICODE_ENCODING = 1;
    private static final SecureRandom RND_GEN;
    private static byte[] SIGNATURE;
    private String credentialCharset = "ASCII";

    static class HMACMD5 {
        protected byte[] ipad;
        protected MessageDigest md5;
        protected byte[] opad;

        HMACMD5(byte[] key) throws NTLMEngineException {
            try {
                this.md5 = MessageDigest.getInstance("MD5");
                this.ipad = new byte[64];
                this.opad = new byte[64];
                int keyLength = key.length;
                if (keyLength > 64) {
                    this.md5.update(key);
                    key = this.md5.digest();
                    keyLength = key.length;
                }
                int i = 0;
                while (i < keyLength) {
                    this.ipad[i] = (byte) (key[i] ^ 54);
                    this.opad[i] = (byte) (key[i] ^ 92);
                    i++;
                }
                while (i < 64) {
                    this.ipad[i] = (byte) 54;
                    this.opad[i] = (byte) 92;
                    i++;
                }
                this.md5.reset();
                this.md5.update(this.ipad);
            } catch (Exception ex) {
                throw new NTLMEngineException("Error getting md5 message digest implementation: " + ex.getMessage(), ex);
            }
        }

        byte[] getOutput() {
            byte[] digest = this.md5.digest();
            this.md5.update(this.opad);
            return this.md5.digest(digest);
        }

        void update(byte[] input) {
            this.md5.update(input);
        }

        void update(byte[] input, int offset, int length) {
            this.md5.update(input, offset, length);
        }
    }

    static class MD4 {
        protected int f47A = 1732584193;
        protected int f48B = -271733879;
        protected int f49C = -1732584194;
        protected int f50D = 271733878;
        protected long count = 0;
        protected byte[] dataBuffer = new byte[64];

        MD4() {
        }

        void update(byte[] input) {
            int curBufferPos = (int) (this.count & 63);
            int inputIndex = 0;
            while ((input.length - inputIndex) + curBufferPos >= this.dataBuffer.length) {
                int transferAmt = this.dataBuffer.length - curBufferPos;
                System.arraycopy(input, inputIndex, this.dataBuffer, curBufferPos, transferAmt);
                this.count += (long) transferAmt;
                curBufferPos = 0;
                inputIndex += transferAmt;
                processBuffer();
            }
            if (inputIndex < input.length) {
                transferAmt = input.length - inputIndex;
                System.arraycopy(input, inputIndex, this.dataBuffer, curBufferPos, transferAmt);
                this.count += (long) transferAmt;
                curBufferPos += transferAmt;
            }
        }

        byte[] getOutput() {
            int bufferIndex = (int) (this.count & 63);
            int padLen = bufferIndex < 56 ? 56 - bufferIndex : 120 - bufferIndex;
            byte[] postBytes = new byte[(padLen + 8)];
            postBytes[0] = Byte.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                postBytes[padLen + i] = (byte) ((int) ((this.count * 8) >>> (i * 8)));
            }
            update(postBytes);
            byte[] result = new byte[16];
            NTLMEngineImpl.writeULong(result, this.f47A, 0);
            NTLMEngineImpl.writeULong(result, this.f48B, 4);
            NTLMEngineImpl.writeULong(result, this.f49C, 8);
            NTLMEngineImpl.writeULong(result, this.f50D, 12);
            return result;
        }

        protected void processBuffer() {
            int[] d = new int[16];
            for (int i = 0; i < 16; i++) {
                d[i] = (((this.dataBuffer[i * 4] & 255) + ((this.dataBuffer[(i * 4) + 1] & 255) << 8)) + ((this.dataBuffer[(i * 4) + 2] & 255) << 16)) + ((this.dataBuffer[(i * 4) + 3] & 255) << 24);
            }
            int AA = this.f47A;
            int BB = this.f48B;
            int CC = this.f49C;
            int DD = this.f50D;
            round1(d);
            round2(d);
            round3(d);
            this.f47A += AA;
            this.f48B += BB;
            this.f49C += CC;
            this.f50D += DD;
        }

        protected void round1(int[] d) {
            this.f47A = NTLMEngineImpl.rotintlft((this.f47A + NTLMEngineImpl.m471F(this.f48B, this.f49C, this.f50D)) + d[0], 3);
            this.f50D = NTLMEngineImpl.rotintlft((this.f50D + NTLMEngineImpl.m471F(this.f47A, this.f48B, this.f49C)) + d[1], 7);
            this.f49C = NTLMEngineImpl.rotintlft((this.f49C + NTLMEngineImpl.m471F(this.f50D, this.f47A, this.f48B)) + d[2], 11);
            this.f48B = NTLMEngineImpl.rotintlft((this.f48B + NTLMEngineImpl.m471F(this.f49C, this.f50D, this.f47A)) + d[3], 19);
            this.f47A = NTLMEngineImpl.rotintlft((this.f47A + NTLMEngineImpl.m471F(this.f48B, this.f49C, this.f50D)) + d[4], 3);
            this.f50D = NTLMEngineImpl.rotintlft((this.f50D + NTLMEngineImpl.m471F(this.f47A, this.f48B, this.f49C)) + d[5], 7);
            this.f49C = NTLMEngineImpl.rotintlft((this.f49C + NTLMEngineImpl.m471F(this.f50D, this.f47A, this.f48B)) + d[6], 11);
            this.f48B = NTLMEngineImpl.rotintlft((this.f48B + NTLMEngineImpl.m471F(this.f49C, this.f50D, this.f47A)) + d[7], 19);
            this.f47A = NTLMEngineImpl.rotintlft((this.f47A + NTLMEngineImpl.m471F(this.f48B, this.f49C, this.f50D)) + d[8], 3);
            this.f50D = NTLMEngineImpl.rotintlft((this.f50D + NTLMEngineImpl.m471F(this.f47A, this.f48B, this.f49C)) + d[9], 7);
            this.f49C = NTLMEngineImpl.rotintlft((this.f49C + NTLMEngineImpl.m471F(this.f50D, this.f47A, this.f48B)) + d[10], 11);
            this.f48B = NTLMEngineImpl.rotintlft((this.f48B + NTLMEngineImpl.m471F(this.f49C, this.f50D, this.f47A)) + d[11], 19);
            this.f47A = NTLMEngineImpl.rotintlft((this.f47A + NTLMEngineImpl.m471F(this.f48B, this.f49C, this.f50D)) + d[12], 3);
            this.f50D = NTLMEngineImpl.rotintlft((this.f50D + NTLMEngineImpl.m471F(this.f47A, this.f48B, this.f49C)) + d[13], 7);
            this.f49C = NTLMEngineImpl.rotintlft((this.f49C + NTLMEngineImpl.m471F(this.f50D, this.f47A, this.f48B)) + d[14], 11);
            this.f48B = NTLMEngineImpl.rotintlft((this.f48B + NTLMEngineImpl.m471F(this.f49C, this.f50D, this.f47A)) + d[15], 19);
        }

        protected void round2(int[] d) {
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m472G(this.f48B, this.f49C, this.f50D)) + d[0]) + 1518500249, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m472G(this.f47A, this.f48B, this.f49C)) + d[4]) + 1518500249, 5);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m472G(this.f50D, this.f47A, this.f48B)) + d[8]) + 1518500249, 9);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m472G(this.f49C, this.f50D, this.f47A)) + d[12]) + 1518500249, 13);
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m472G(this.f48B, this.f49C, this.f50D)) + d[1]) + 1518500249, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m472G(this.f47A, this.f48B, this.f49C)) + d[5]) + 1518500249, 5);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m472G(this.f50D, this.f47A, this.f48B)) + d[9]) + 1518500249, 9);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m472G(this.f49C, this.f50D, this.f47A)) + d[13]) + 1518500249, 13);
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m472G(this.f48B, this.f49C, this.f50D)) + d[2]) + 1518500249, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m472G(this.f47A, this.f48B, this.f49C)) + d[6]) + 1518500249, 5);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m472G(this.f50D, this.f47A, this.f48B)) + d[10]) + 1518500249, 9);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m472G(this.f49C, this.f50D, this.f47A)) + d[14]) + 1518500249, 13);
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m472G(this.f48B, this.f49C, this.f50D)) + d[3]) + 1518500249, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m472G(this.f47A, this.f48B, this.f49C)) + d[7]) + 1518500249, 5);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m472G(this.f50D, this.f47A, this.f48B)) + d[11]) + 1518500249, 9);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m472G(this.f49C, this.f50D, this.f47A)) + d[15]) + 1518500249, 13);
        }

        protected void round3(int[] d) {
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m473H(this.f48B, this.f49C, this.f50D)) + d[0]) + 1859775393, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m473H(this.f47A, this.f48B, this.f49C)) + d[8]) + 1859775393, 9);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m473H(this.f50D, this.f47A, this.f48B)) + d[4]) + 1859775393, 11);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m473H(this.f49C, this.f50D, this.f47A)) + d[12]) + 1859775393, 15);
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m473H(this.f48B, this.f49C, this.f50D)) + d[2]) + 1859775393, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m473H(this.f47A, this.f48B, this.f49C)) + d[10]) + 1859775393, 9);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m473H(this.f50D, this.f47A, this.f48B)) + d[6]) + 1859775393, 11);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m473H(this.f49C, this.f50D, this.f47A)) + d[14]) + 1859775393, 15);
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m473H(this.f48B, this.f49C, this.f50D)) + d[1]) + 1859775393, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m473H(this.f47A, this.f48B, this.f49C)) + d[9]) + 1859775393, 9);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m473H(this.f50D, this.f47A, this.f48B)) + d[5]) + 1859775393, 11);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m473H(this.f49C, this.f50D, this.f47A)) + d[13]) + 1859775393, 15);
            this.f47A = NTLMEngineImpl.rotintlft(((this.f47A + NTLMEngineImpl.m473H(this.f48B, this.f49C, this.f50D)) + d[3]) + 1859775393, 3);
            this.f50D = NTLMEngineImpl.rotintlft(((this.f50D + NTLMEngineImpl.m473H(this.f47A, this.f48B, this.f49C)) + d[11]) + 1859775393, 9);
            this.f49C = NTLMEngineImpl.rotintlft(((this.f49C + NTLMEngineImpl.m473H(this.f50D, this.f47A, this.f48B)) + d[7]) + 1859775393, 11);
            this.f48B = NTLMEngineImpl.rotintlft(((this.f48B + NTLMEngineImpl.m473H(this.f49C, this.f50D, this.f47A)) + d[15]) + 1859775393, 15);
        }
    }

    static class NTLMMessage {
        private int currentOutputPosition = 0;
        private byte[] messageContents = null;

        NTLMMessage() {
        }

        NTLMMessage(String messageBody, int expectedType) throws NTLMEngineException {
            this.messageContents = Base64.decodeBase64(EncodingUtils.getBytes(messageBody, "ASCII"));
            if (this.messageContents.length < NTLMEngineImpl.SIGNATURE.length) {
                throw new NTLMEngineException("NTLM message decoding error - packet too short");
            }
            for (int i = 0; i < NTLMEngineImpl.SIGNATURE.length; i++) {
                if (this.messageContents[i] != NTLMEngineImpl.SIGNATURE[i]) {
                    throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
                }
            }
            int type = readULong(NTLMEngineImpl.SIGNATURE.length);
            if (type != expectedType) {
                throw new NTLMEngineException("NTLM type " + Integer.toString(expectedType) + " message expected - instead got type " + Integer.toString(type));
            }
            this.currentOutputPosition = this.messageContents.length;
        }

        protected int getPreambleLength() {
            return NTLMEngineImpl.SIGNATURE.length + 4;
        }

        protected int getMessageLength() {
            return this.currentOutputPosition;
        }

        protected byte readByte(int position) throws NTLMEngineException {
            if (this.messageContents.length >= position + 1) {
                return this.messageContents[position];
            }
            throw new NTLMEngineException("NTLM: Message too short");
        }

        protected void readBytes(byte[] buffer, int position) throws NTLMEngineException {
            if (this.messageContents.length < buffer.length + position) {
                throw new NTLMEngineException("NTLM: Message too short");
            }
            System.arraycopy(this.messageContents, position, buffer, 0, buffer.length);
        }

        protected int readUShort(int position) throws NTLMEngineException {
            return NTLMEngineImpl.readUShort(this.messageContents, position);
        }

        protected int readULong(int position) throws NTLMEngineException {
            return NTLMEngineImpl.readULong(this.messageContents, position);
        }

        protected byte[] readSecurityBuffer(int position) throws NTLMEngineException {
            return NTLMEngineImpl.readSecurityBuffer(this.messageContents, position);
        }

        protected void prepareResponse(int maxlength, int messageType) {
            this.messageContents = new byte[maxlength];
            this.currentOutputPosition = 0;
            addBytes(NTLMEngineImpl.SIGNATURE);
            addULong(messageType);
        }

        protected void addByte(byte b) {
            this.messageContents[this.currentOutputPosition] = b;
            this.currentOutputPosition++;
        }

        protected void addBytes(byte[] bytes) {
            for (byte b : bytes) {
                this.messageContents[this.currentOutputPosition] = b;
                this.currentOutputPosition++;
            }
        }

        protected void addUShort(int value) {
            addByte((byte) (value & 255));
            addByte((byte) ((value >> 8) & 255));
        }

        protected void addULong(int value) {
            addByte((byte) (value & 255));
            addByte((byte) ((value >> 8) & 255));
            addByte((byte) ((value >> 16) & 255));
            addByte((byte) ((value >> 24) & 255));
        }

        String getResponse() {
            byte[] resp;
            if (this.messageContents.length > this.currentOutputPosition) {
                byte[] tmp = new byte[this.currentOutputPosition];
                for (int i = 0; i < this.currentOutputPosition; i++) {
                    tmp[i] = this.messageContents[i];
                }
                resp = tmp;
            } else {
                resp = this.messageContents;
            }
            return EncodingUtils.getAsciiString(Base64.encodeBase64(resp));
        }
    }

    static class Type1Message extends NTLMMessage {
        protected byte[] domainBytes;
        protected byte[] hostBytes;

        Type1Message(String domain, String host) throws NTLMEngineException {
            try {
                host = NTLMEngineImpl.convertHost(host);
                domain = NTLMEngineImpl.convertDomain(domain);
                this.hostBytes = host.getBytes("UnicodeLittleUnmarked");
                this.domainBytes = domain.toUpperCase().getBytes("UnicodeLittleUnmarked");
            } catch (UnsupportedEncodingException e) {
                throw new NTLMEngineException("Unicode unsupported: " + e.getMessage(), e);
            }
        }

        String getResponse() {
            prepareResponse((this.hostBytes.length + 32) + this.domainBytes.length, 1);
            addULong(537395765);
            addUShort(this.domainBytes.length);
            addUShort(this.domainBytes.length);
            addULong(this.hostBytes.length + 32);
            addUShort(this.hostBytes.length);
            addUShort(this.hostBytes.length);
            addULong(32);
            addBytes(this.hostBytes);
            addBytes(this.domainBytes);
            return super.getResponse();
        }
    }

    static class Type2Message extends NTLMMessage {
        protected byte[] challenge = new byte[8];
        protected int flags;
        protected String target;
        protected byte[] targetInfo;

        Type2Message(String message) throws NTLMEngineException {
            super(message, 2);
            readBytes(this.challenge, 24);
            this.flags = readULong(20);
            if ((this.flags & 1) == 0) {
                throw new NTLMEngineException("NTLM type 2 message has flags that make no sense: " + Integer.toString(this.flags));
            }
            byte[] bytes;
            this.target = null;
            if (getMessageLength() >= 20) {
                bytes = readSecurityBuffer(12);
                if (bytes.length != 0) {
                    try {
                        this.target = new String(bytes, "UnicodeLittleUnmarked");
                    } catch (UnsupportedEncodingException e) {
                        throw new NTLMEngineException(e.getMessage(), e);
                    }
                }
            }
            this.targetInfo = null;
            if (getMessageLength() >= 48) {
                bytes = readSecurityBuffer(40);
                if (bytes.length != 0) {
                    this.targetInfo = bytes;
                }
            }
        }

        byte[] getChallenge() {
            return this.challenge;
        }

        String getTarget() {
            return this.target;
        }

        byte[] getTargetInfo() {
            return this.targetInfo;
        }

        int getFlags() {
            return this.flags;
        }
    }

    static class Type3Message extends NTLMMessage {
        protected byte[] domainBytes;
        protected byte[] hostBytes;
        protected byte[] lmResp;
        protected byte[] ntResp;
        protected int type2Flags;
        protected byte[] userBytes;

        Type3Message(String domain, String host, String user, String password, byte[] nonce, int type2Flags, String target, byte[] targetInformation) throws NTLMEngineException {
            this.type2Flags = type2Flags;
            host = NTLMEngineImpl.convertHost(host);
            domain = NTLMEngineImpl.convertDomain(domain);
            byte[] clientChallenge;
            if (targetInformation != null && target != null) {
                try {
                    clientChallenge = NTLMEngineImpl.makeRandomChallenge();
                    this.ntResp = NTLMEngineImpl.getNTLMv2Response(target, user, password, nonce, clientChallenge, targetInformation);
                    this.lmResp = NTLMEngineImpl.getLMv2Response(target, user, password, nonce, clientChallenge);
                } catch (NTLMEngineException e) {
                    this.ntResp = new byte[0];
                    this.lmResp = NTLMEngineImpl.getLMResponse(password, nonce);
                }
            } else if ((524288 & type2Flags) != 0) {
                clientChallenge = NTLMEngineImpl.makeNTLM2RandomChallenge();
                this.ntResp = NTLMEngineImpl.getNTLM2SessionResponse(password, nonce, clientChallenge);
                this.lmResp = clientChallenge;
            } else {
                this.ntResp = NTLMEngineImpl.getNTLMResponse(password, nonce);
                this.lmResp = NTLMEngineImpl.getLMResponse(password, nonce);
            }
            try {
                this.domainBytes = domain.toUpperCase().getBytes("UnicodeLittleUnmarked");
                this.hostBytes = host.getBytes("UnicodeLittleUnmarked");
                this.userBytes = user.getBytes("UnicodeLittleUnmarked");
            } catch (UnsupportedEncodingException e2) {
                throw new NTLMEngineException("Unicode not supported: " + e2.getMessage(), e2);
            }
        }

        String getResponse() {
            int ntRespLen = this.ntResp.length;
            int lmRespLen = this.lmResp.length;
            int domainLen = this.domainBytes.length;
            int hostLen = this.hostBytes.length;
            int userLen = this.userBytes.length;
            int ntRespOffset = 64 + lmRespLen;
            int domainOffset = ntRespOffset + ntRespLen;
            int userOffset = domainOffset + domainLen;
            int hostOffset = userOffset + userLen;
            int finalLength = (hostOffset + hostLen) + 0;
            prepareResponse(finalLength, 3);
            addUShort(lmRespLen);
            addUShort(lmRespLen);
            addULong(64);
            addUShort(ntRespLen);
            addUShort(ntRespLen);
            addULong(ntRespOffset);
            addUShort(domainLen);
            addUShort(domainLen);
            addULong(domainOffset);
            addUShort(userLen);
            addUShort(userLen);
            addULong(userOffset);
            addUShort(hostLen);
            addUShort(hostLen);
            addULong(hostOffset);
            addULong(0);
            addULong(finalLength);
            addULong(((((536871429 | (this.type2Flags & 524288)) | (this.type2Flags & 16)) | (this.type2Flags & 32)) | (this.type2Flags & NTLMEngineImpl.FLAG_NEGOTIATE_KEY_EXCH)) | (this.type2Flags & 32768));
            addBytes(this.lmResp);
            addBytes(this.ntResp);
            addBytes(this.domainBytes);
            addBytes(this.userBytes);
            addBytes(this.hostBytes);
            return super.getResponse();
        }
    }

    NTLMEngineImpl() {
    }

    static {
        SecureRandom rnd = null;
        try {
            rnd = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
        }
        RND_GEN = rnd;
        byte[] bytesWithoutNull = EncodingUtils.getBytes("NTLMSSP", "ASCII");
        SIGNATURE = new byte[(bytesWithoutNull.length + 1)];
        System.arraycopy(bytesWithoutNull, 0, SIGNATURE, 0, bytesWithoutNull.length);
        SIGNATURE[bytesWithoutNull.length] = (byte) 0;
    }

    final String getResponseFor(String message, String username, String password, String host, String domain) throws NTLMEngineException {
        if (message == null || message.trim().equals("")) {
            return getType1Message(host, domain);
        }
        Type2Message t2m = new Type2Message(message);
        return getType3Message(username, password, host, domain, t2m.getChallenge(), t2m.getFlags(), t2m.getTarget(), t2m.getTargetInfo());
    }

    String getType1Message(String host, String domain) throws NTLMEngineException {
        return new Type1Message(domain, host).getResponse();
    }

    String getType3Message(String user, String password, String host, String domain, byte[] nonce, int type2Flags, String target, byte[] targetInformation) throws NTLMEngineException {
        return new Type3Message(domain, host, user, password, nonce, type2Flags, target, targetInformation).getResponse();
    }

    String getCredentialCharset() {
        return this.credentialCharset;
    }

    void setCredentialCharset(String credentialCharset) {
        this.credentialCharset = credentialCharset;
    }

    private static String stripDotSuffix(String value) {
        int index = value.indexOf(".");
        if (index != -1) {
            return value.substring(0, index);
        }
        return value;
    }

    private static String convertHost(String host) {
        return stripDotSuffix(host);
    }

    private static String convertDomain(String domain) {
        return stripDotSuffix(domain);
    }

    private static int readULong(byte[] src, int index) throws NTLMEngineException {
        if (src.length >= index + 4) {
            return (((src[index] & 255) | ((src[index + 1] & 255) << 8)) | ((src[index + 2] & 255) << 16)) | ((src[index + 3] & 255) << 24);
        }
        throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
    }

    private static int readUShort(byte[] src, int index) throws NTLMEngineException {
        if (src.length >= index + 2) {
            return (src[index] & 255) | ((src[index + 1] & 255) << 8);
        }
        throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
    }

    private static byte[] readSecurityBuffer(byte[] src, int index) throws NTLMEngineException {
        int length = readUShort(src, index);
        int offset = readULong(src, index + 4);
        if (src.length < offset + length) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
        }
        byte[] buffer = new byte[length];
        System.arraycopy(src, offset, buffer, 0, length);
        return buffer;
    }

    private static byte[] makeRandomChallenge() throws NTLMEngineException {
        if (RND_GEN == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        byte[] rval = new byte[8];
        synchronized (RND_GEN) {
            RND_GEN.nextBytes(rval);
        }
        return rval;
    }

    private static byte[] makeNTLM2RandomChallenge() throws NTLMEngineException {
        if (RND_GEN == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        byte[] rval = new byte[24];
        synchronized (RND_GEN) {
            RND_GEN.nextBytes(rval);
        }
        Arrays.fill(rval, 8, 24, (byte) 0);
        return rval;
    }

    static byte[] getLMResponse(String password, byte[] challenge) throws NTLMEngineException {
        return lmResponse(lmHash(password), challenge);
    }

    static byte[] getNTLMResponse(String password, byte[] challenge) throws NTLMEngineException {
        return lmResponse(ntlmHash(password), challenge);
    }

    static byte[] getNTLMv2Response(String target, String user, String password, byte[] challenge, byte[] clientChallenge, byte[] targetInformation) throws NTLMEngineException {
        return lmv2Response(ntlmv2Hash(target, user, password), challenge, createBlob(clientChallenge, targetInformation));
    }

    static byte[] getLMv2Response(String target, String user, String password, byte[] challenge, byte[] clientChallenge) throws NTLMEngineException {
        return lmv2Response(ntlmv2Hash(target, user, password), challenge, clientChallenge);
    }

    static byte[] getNTLM2SessionResponse(String password, byte[] challenge, byte[] clientChallenge) throws NTLMEngineException {
        try {
            byte[] ntlmHash = ntlmHash(password);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(challenge);
            md5.update(clientChallenge);
            byte[] sessionHash = new byte[8];
            System.arraycopy(md5.digest(), 0, sessionHash, 0, 8);
            return lmResponse(ntlmHash, sessionHash);
        } catch (Exception e) {
            if (e instanceof NTLMEngineException) {
                throw ((NTLMEngineException) e);
            }
            throw new NTLMEngineException(e.getMessage(), e);
        }
    }

    private static byte[] lmHash(String password) throws NTLMEngineException {
        try {
            byte[] oemPassword = password.toUpperCase().getBytes("US-ASCII");
            byte[] keyBytes = new byte[14];
            System.arraycopy(oemPassword, 0, keyBytes, 0, Math.min(oemPassword.length, 14));
            Key lowKey = createDESKey(keyBytes, 0);
            Key highKey = createDESKey(keyBytes, 7);
            byte[] magicConstant = "KGS!@#$%".getBytes("US-ASCII");
            Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
            des.init(1, lowKey);
            byte[] lowHash = des.doFinal(magicConstant);
            des.init(1, highKey);
            byte[] highHash = des.doFinal(magicConstant);
            byte[] lmHash = new byte[16];
            System.arraycopy(lowHash, 0, lmHash, 0, 8);
            System.arraycopy(highHash, 0, lmHash, 8, 8);
            return lmHash;
        } catch (Exception e) {
            throw new NTLMEngineException(e.getMessage(), e);
        }
    }

    private static byte[] ntlmHash(String password) throws NTLMEngineException {
        try {
            byte[] unicodePassword = password.getBytes("UnicodeLittleUnmarked");
            MD4 md4 = new MD4();
            md4.update(unicodePassword);
            return md4.getOutput();
        } catch (UnsupportedEncodingException e) {
            throw new NTLMEngineException("Unicode not supported: " + e.getMessage(), e);
        }
    }

    private static byte[] ntlmv2Hash(String target, String user, String password) throws NTLMEngineException {
        try {
            HMACMD5 hmacMD5 = new HMACMD5(ntlmHash(password));
            hmacMD5.update(user.toUpperCase().getBytes("UnicodeLittleUnmarked"));
            hmacMD5.update(target.getBytes("UnicodeLittleUnmarked"));
            return hmacMD5.getOutput();
        } catch (UnsupportedEncodingException e) {
            throw new NTLMEngineException("Unicode not supported! " + e.getMessage(), e);
        }
    }

    private static byte[] lmResponse(byte[] hash, byte[] challenge) throws NTLMEngineException {
        try {
            byte[] keyBytes = new byte[21];
            System.arraycopy(hash, 0, keyBytes, 0, 16);
            Key lowKey = createDESKey(keyBytes, 0);
            Key middleKey = createDESKey(keyBytes, 7);
            Key highKey = createDESKey(keyBytes, 14);
            Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
            des.init(1, lowKey);
            byte[] lowResponse = des.doFinal(challenge);
            des.init(1, middleKey);
            byte[] middleResponse = des.doFinal(challenge);
            des.init(1, highKey);
            byte[] highResponse = des.doFinal(challenge);
            byte[] lmResponse = new byte[24];
            System.arraycopy(lowResponse, 0, lmResponse, 0, 8);
            System.arraycopy(middleResponse, 0, lmResponse, 8, 8);
            System.arraycopy(highResponse, 0, lmResponse, 16, 8);
            return lmResponse;
        } catch (Exception e) {
            throw new NTLMEngineException(e.getMessage(), e);
        }
    }

    private static byte[] lmv2Response(byte[] hash, byte[] challenge, byte[] clientData) throws NTLMEngineException {
        HMACMD5 hmacMD5 = new HMACMD5(hash);
        hmacMD5.update(challenge);
        hmacMD5.update(clientData);
        byte[] mac = hmacMD5.getOutput();
        byte[] lmv2Response = new byte[(mac.length + clientData.length)];
        System.arraycopy(mac, 0, lmv2Response, 0, mac.length);
        System.arraycopy(clientData, 0, lmv2Response, mac.length, clientData.length);
        return lmv2Response;
    }

    private static byte[] createBlob(byte[] clientChallenge, byte[] targetInformation) {
        byte[] blobSignature = new byte[]{(byte) 1, (byte) 1, (byte) 0, (byte) 0};
        byte[] reserved = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
        byte[] unknown1 = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
        long time = (System.currentTimeMillis() + 11644473600000L) * 10000;
        byte[] timestamp = new byte[8];
        for (int i = 0; i < 8; i++) {
            timestamp[i] = (byte) ((int) time);
            time >>>= 8;
        }
        byte[] blob = new byte[(((((blobSignature.length + reserved.length) + timestamp.length) + 8) + unknown1.length) + targetInformation.length)];
        System.arraycopy(blobSignature, 0, blob, 0, blobSignature.length);
        int offset = 0 + blobSignature.length;
        System.arraycopy(reserved, 0, blob, offset, reserved.length);
        offset += reserved.length;
        System.arraycopy(timestamp, 0, blob, offset, timestamp.length);
        offset += timestamp.length;
        System.arraycopy(clientChallenge, 0, blob, offset, 8);
        offset += 8;
        System.arraycopy(unknown1, 0, blob, offset, unknown1.length);
        System.arraycopy(targetInformation, 0, blob, offset + unknown1.length, targetInformation.length);
        return blob;
    }

    private static Key createDESKey(byte[] bytes, int offset) {
        keyBytes = new byte[7];
        System.arraycopy(bytes, offset, keyBytes, 0, 7);
        byte[] material = new byte[]{keyBytes[0], (byte) ((keyBytes[0] << 7) | ((keyBytes[1] & 255) >>> 1)), (byte) ((keyBytes[1] << 6) | ((keyBytes[2] & 255) >>> 2)), (byte) ((keyBytes[2] << 5) | ((keyBytes[3] & 255) >>> 3)), (byte) ((keyBytes[3] << 4) | ((keyBytes[4] & 255) >>> 4)), (byte) ((keyBytes[4] << 3) | ((keyBytes[5] & 255) >>> 5)), (byte) ((keyBytes[5] << 2) | ((keyBytes[6] & 255) >>> 6)), (byte) (keyBytes[6] << 1)};
        oddParity(material);
        return new SecretKeySpec(material, "DES");
    }

    private static void oddParity(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (((((((((b >>> 7) ^ (b >>> 6)) ^ (b >>> 5)) ^ (b >>> 4)) ^ (b >>> 3)) ^ (b >>> 2)) ^ (b >>> 1)) & 1) == 0) {
                bytes[i] = (byte) (bytes[i] | 1);
            } else {
                bytes[i] = (byte) (bytes[i] & -2);
            }
        }
    }

    static void writeULong(byte[] buffer, int value, int offset) {
        buffer[offset] = (byte) (value & 255);
        buffer[offset + 1] = (byte) ((value >> 8) & 255);
        buffer[offset + 2] = (byte) ((value >> 16) & 255);
        buffer[offset + 3] = (byte) ((value >> 24) & 255);
    }

    static int m471F(int x, int y, int z) {
        return (x & y) | ((x ^ -1) & z);
    }

    static int m472G(int x, int y, int z) {
        return ((x & y) | (x & z)) | (y & z);
    }

    static int m473H(int x, int y, int z) {
        return (x ^ y) ^ z;
    }

    static int rotintlft(int val, int numbits) {
        return (val << numbits) | (val >>> (32 - numbits));
    }

    public String generateType1Msg(String domain, String workstation) throws NTLMEngineException {
        return getType1Message(workstation, domain);
    }

    public String generateType3Msg(String username, String password, String domain, String workstation, String challenge) throws NTLMEngineException {
        Type2Message t2m = new Type2Message(challenge);
        return getType3Message(username, password, workstation, domain, t2m.getChallenge(), t2m.getFlags(), t2m.getTarget(), t2m.getTargetInfo());
    }
}
