/*
 * Copyright 2009 Brian Pellin.
 *     
 * This file is part of KeePassDroid.
 *
 *  KeePassDroid is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  KeePassDroid is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.android.keepass.keepasslib;

import java.io.IOException;
import java.io.OutputStream;

import org.phoneid.keepassj2me.PwEntry;
import org.phoneid.keepassj2me.Types;

public class PwEntryOutput {
	private OutputStream mOS;
	private PwEntry mPE;
	
	/** Output the PwGroup to the stream
	 * @param pe
	 * @param os
	 */
	public PwEntryOutput(PwEntry pe, OutputStream os) {
		mPE = pe;
		mOS = os;
	}

	//NOTE: Need be to careful about using ints.  The actual type written to file is a unsigned int
	public void output() throws IOException {
		// UUID
		mOS.write(UUID_FIELD_TYPE);
		mOS.write(UUID_FIELD_SIZE);
		mOS.write(mPE.uuid);
		
		// Group ID
		mOS.write(GROUPID_FIELD_TYPE);
		mOS.write(LONG_FOUR);
		mOS.write(Types.writeInt(mPE.groupId));
		
		// Image ID
		mOS.write(IMAGEID_FIELD_TYPE);
		mOS.write(LONG_FOUR);
		mOS.write(Types.writeInt(mPE.imageId));

		// Title
		byte[] title = mPE.title.getBytes("UTF-8");
		mOS.write(TITLE_FIELD_TYPE);
		mOS.write(Types.writeInt(title.length));
		mOS.write(title);
		
		// URL
		byte[] url = mPE.url.getBytes("UTF-8");
		mOS.write(URL_FIELD_TYPE);
		mOS.write(Types.writeInt(url.length));
		mOS.write(url);
		
		// Username
		byte[] username = mPE.username.getBytes("UTF-8");
		mOS.write(USERNAME_FIELD_TYPE);
		mOS.write(Types.writeInt(username.length));
		mOS.write(username);
		
		// Password
		byte[] password = mPE.getPassword();
		mOS.write(PASSWORD_FIELD_TYPE);
		mOS.write(Types.writeInt(password.length));
		mOS.write(password);
		
		// Additional
		byte[] additional = mPE.additional.getBytes("UTF-8");
		mOS.write(ADDITIONAL_FIELD_TYPE);
		mOS.write(Types.writeInt(additional.length));
		mOS.write(additional);
		
		// Create date
		mOS.write(CREATE_FIELD_TYPE);
		mOS.write(DATE_FIELD_SIZE);
		mOS.write(Types.writeTime(mPE.tCreation));
		
		// Modification date
		mOS.write(MOD_FIELD_TYPE);
		mOS.write(DATE_FIELD_SIZE);
		mOS.write(Types.writeTime(mPE.tLastMod));
		
		// Access date
		mOS.write(ACCESS_FIELD_TYPE);
		mOS.write(DATE_FIELD_SIZE);
		mOS.write(Types.writeTime(mPE.tLastAccess));
		
		// Expiration date
		mOS.write(EXPIRE_FIELD_TYPE);
		mOS.write(DATE_FIELD_SIZE);
		mOS.write(Types.writeTime(mPE.tExpire));
		
		// Binary desc
		byte[] binaryDesc = mPE.binaryDesc.getBytes("UTF-8");
		mOS.write(BINARY_DESC_FIELD_TYPE);
		mOS.write(Types.writeInt(binaryDesc.length));
		mOS.write(binaryDesc);
		
		// Binary data
		byte[] data = mPE.getBinaryData();
		mOS.write(BINARY_DATA_FIELD_TYPE);
		mOS.write(Types.writeInt(data.length));
		mOS.write(data);
		
		// End
		mOS.write(END_FIELD_TYPE);
		mOS.write(ZERO_FIELD_SIZE);
	}

	public static final byte[] UUID_FIELD_TYPE =     { 0x00, 0x01};
	public static final byte[] GROUPID_FIELD_TYPE =  { 0x00, 0x02};
	public static final byte[] IMAGEID_FIELD_TYPE =  { 0x00, 0x03};
	public static final byte[] TITLE_FIELD_TYPE = { 0x00, 0x04};
	public static final byte[] URL_FIELD_TYPE =  { 0x00, 0x05};
	public static final byte[] USERNAME_FIELD_TYPE =  { 0x00, 0x06};
	public static final byte[] PASSWORD_FIELD_TYPE = { 0x00, 0x07};
	public static final byte[] ADDITIONAL_FIELD_TYPE =   { 0x00, 0x08};
	public static final byte[] CREATE_FIELD_TYPE =   { 0x00, 0x09};
	public static final byte[] MOD_FIELD_TYPE =   { 0x00, 0x0A};
	public static final byte[] ACCESS_FIELD_TYPE =   { 0x00, 0x0B};
	public static final byte[] EXPIRE_FIELD_TYPE =   { 0x00, 0x0C};
	public static final byte[] BINARY_DESC_FIELD_TYPE =   { 0x00, 0x0D};
	public static final byte[] BINARY_DATA_FIELD_TYPE =   { 0x00, 0x0E};
	public static final byte[] END_FIELD_TYPE =     Types.writeUByte(0xFFFF);
	public static final byte[] LONG_FOUR = { 0x00, 0x00, 0x00, 0x04};
	public static final byte[] UUID_FIELD_SIZE =    { 0x00, 0x00, 0x00, 0x10};
	public static final byte[] DATE_FIELD_SIZE =    { 0x00, 0x00, 0x00, 0x05};
	public static final byte[] IMAGEID_FIELD_SIZE = LONG_FOUR;
	public static final byte[] LEVEL_FIELD_SIZE =   LONG_FOUR;
	public static final byte[] FLAGS_FIELD_SIZE =   LONG_FOUR;
	public static final byte[] ZERO_FIELD_SIZE =    { 0x00, 0x00, 0x00, 0x00};
	


}