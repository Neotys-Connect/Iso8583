package com.neotys.iso8583.connect;

import java.io.IOException;
import java.util.List;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.channel.*;
import org.jpos.iso.packager.GenericPackager;

import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;


public class ISO8583ConnectEngine implements ActionEngine {
	String HOST;
	String PORT;
	String ControllerCode;
	String TypeofChannel;
	boolean BoolAddMessageLength;
	boolean boolIncludeHeaderInBitmap;
	
	BaseChannel channel;@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		String StrAddMessageLength=null;
		String StrIncludeHeaderInBitmap = null;
		SampleResult result = new SampleResult();
		
		for(ActionParameter parameter:parameters) {
		
			switch(parameter.getName()) {
			
			
			case ISo8583Connect.HOST:
				HOST = parameter.getValue();
				break;
			case ISo8583Connect.PORT:
				PORT = parameter.getValue();
				break;
			case ISo8583Connect.ControllerCode:
				ControllerCode = parameter.getValue();
				break;
			case ISo8583Connect.TypeofChannel:
				TypeofChannel = parameter.getValue();
				break;
			case ISo8583Connect.IncludeHeaderinBitmap:
				StrIncludeHeaderInBitmap = parameter.getValue();
				break;
			case ISo8583Connect.AddMessageLengthIntoHeader:
				StrAddMessageLength = parameter.getValue();
				break;
			
			}
			
		}
		if (Strings.isNullOrEmpty(StrIncludeHeaderInBitmap)) {
			boolIncludeHeaderInBitmap=true;
			
		}
		else
		{
			if(StrIncludeHeaderInBitmap.compareToIgnoreCase("true")==0)
			{
				boolIncludeHeaderInBitmap=true;
			}
			if(StrIncludeHeaderInBitmap.compareToIgnoreCase("false")==0)
			{
				boolIncludeHeaderInBitmap=false;
			}
			if(StrIncludeHeaderInBitmap.compareToIgnoreCase("true")!=0 && StrIncludeHeaderInBitmap.compareToIgnoreCase("false")!=0)
			{
				return getErrorResult(context, result, "Invalid argument: value must be true or false "
						+ ISo8583Connect.IncludeHeaderinBitmap + ".");
			}
		}
		if (Strings.isNullOrEmpty(StrAddMessageLength)) {
			BoolAddMessageLength=true;
		}
		else
		{
			if(StrAddMessageLength.compareToIgnoreCase("true")==0)
			{
				BoolAddMessageLength=true;
			}
			if(StrAddMessageLength.compareToIgnoreCase("false")==0)
			{
				BoolAddMessageLength=false;
			}
			if(StrAddMessageLength.compareToIgnoreCase("true")!=0 && StrAddMessageLength.compareToIgnoreCase("false")!=0)
			{
				return getErrorResult(context, result, "Invalid argument: value must be true or false "
						+ ISo8583Connect.AddMessageLengthIntoHeader + ".");
			}
		}
		if (Strings.isNullOrEmpty(HOST)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ ISo8583Connect.HOST + ".");
		}
		if (Strings.isNullOrEmpty(PORT)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ ISo8583Connect.PORT + ".");
		}
		if (Strings.isNullOrEmpty(ControllerCode)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ ISo8583Connect.ControllerCode + ".");
		}
		if (Strings.isNullOrEmpty(TypeofChannel)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ ISo8583Connect.TypeofChannel + ".");
		}
		
		try{
			
			
			result.sampleStart();
			String FilePath =context.getVariableManager().getValue("NL-CustomResources");
			if(FilePath == null)
			{
				return getErrorResult(context, result, "Probleme with nl custom ressources"
						+ ISo8583Connect.ControllerCode + ".");
			}
			//channel =new Socket(HOST,Integer.valueOf(PORT));
			ControllerCode=ControllerCode.replaceAll(" ", "");
			GenericPackager packager = new GenericPackager(FilePath+"/basic_"+ControllerCode+".xml");


			switch(TypeofChannel) {
				case "ASCII":
					if (!BoolAddMessageLength) 
					{
						channel = new ASCIIChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new ASCIIChannel();
	                }
					break;

				case "NCC":
					if (!BoolAddMessageLength) 
					{
						channel = new NCCChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new NCCChannel();
	                }
					break;
				case "NACC":
					
					if (!BoolAddMessageLength) 
					{
						channel = new NACChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new NACChannel();
	                }
					break;

				case "HEX":
					if (!BoolAddMessageLength) 
					{
						channel = new HEXChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new HEXChannel();
	                }
					
					break;
				case "BASE24":
					
					if (!BoolAddMessageLength) 
					{
						channel = new BASE24Channel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new BASE24Channel();
	                }
					break;
				case "RBP":
					
					if (!BoolAddMessageLength) 
					{
						channel = new RBPChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new RBPChannel();
	                }
					break;
				case "PAD":
					
					if (!BoolAddMessageLength) 
					{
						channel = new PADChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new PADChannel();
	                }
					break;
				case "VAP":
					
					if (!BoolAddMessageLength) 
					{
						channel = new VAPChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new VAPChannel();
	                }
					break;
				case "CS":
					
					if (!BoolAddMessageLength) 
					{
						channel = new CSChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new CSChannel();
	                }
					break;
				case "AMEX":
					
					if (!BoolAddMessageLength) 
					{
						channel = new AmexChannel() {
		                        @Override
		                        protected void sendMessageLength(int len) throws IOException {
		                        }
		
		                        @Override
		                        protected int getMessageLength() throws IOException, ISOException {
		                            return -1;
		                        }
						};
	                } else {
	                    channel = new AmexChannel();
	                }
					break;
				case "BASE24TCP":

					if (!BoolAddMessageLength)
					{
						channel = new BASE24TCPChannel() {
							@Override
							protected void sendMessageLength(int len) throws IOException {
							}

							@Override
							protected int getMessageLength() throws IOException, ISOException {
								return -1;
							}
						};
					} else {
						channel = new BASE24TCPChannel();
					}
					break;
			
				default :
					return getErrorResult(context, result, "Invalid channel Code: channel not supported "
							+ ISo8583Connect.TypeofChannel + ".");
		}
			
			channel.setOverrideHeader(!boolIncludeHeaderInBitmap);
			channel.setHost(HOST);
			channel.setPort(Integer.valueOf(PORT));
			channel.setPackager(packager);
			channel.connect();
			if(channel.isConnected())
				context.getLogger().debug(" Channel connected to "+ channel.getName() + "to host"+ channel.getHost());
			else
				context.getLogger().debug(" Channel not connected to "+ channel.getName() + "to host"+ channel.getHost());
				
			result.sampleEnd();
			result.setRequestContent("connected to :" + HOST);
			context.getCurrentVirtualUser().put(ControllerCode+"_"+HOST+":"+PORT+".SocketObj",channel);
			
		} 
		catch (IOException  e) {
			return getErrorResult(context, result, "error socket"+ e.toString() );
		}
		catch(Exception e)
		{
			return getErrorResult(context, result, "Socket connection error"
					+ e.toString() );
			
		}
		return result;
	}
	private static SampleResult getErrorResult(final com.neotys.extensions.action.engine.Context context,
			final SampleResult result, final String errorMessage) {
		result.setError(true);
		result.setStatusCode("NL-ISO8583Connect_ACTION");
		result.setResponseContent(errorMessage);
		context.getLogger().error(errorMessage);
		return result;
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	@Override
	public void stopExecute() {
		// TODO Auto-generated method stub

	}

}
