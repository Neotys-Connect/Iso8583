package com.neotys.iso8583.model;

import java.util.BitSet;

import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFieldPackager;
import org.jpos.iso.ISOUtil;

public class CustomIFA_BitMap extends ISOFieldPackager{
	
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	
	public CustomIFA_BitMap(int length, String description) {
		super(length, description);
	}
	public CustomIFA_BitMap() {
		super();
	}
	
	
	@Override
	public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException {
		final BitSet bmap = ISOUtil.byte2BitSet(b, offset, true);
		c.setValue(bmap);
		int len = (len = bmap.size()) > 128 ? 128 : len;
		return (len >> 3);
	}

	@Override
	public byte[] pack(ISOComponent c) throws ISOException {
		byte[] b = ISOUtil.bitSet2byte((BitSet) c.getValue());
		final byte[] result = hexStringToByteArray("00000000000000000000000000000000");
		for (int i = 0; i < b.length; i++) {
			result[i] = b[i];
		}
		return result;
	}

	@Override
	public int getMaxPackedLength() {
		return getLength() >> 2;
	}

}
