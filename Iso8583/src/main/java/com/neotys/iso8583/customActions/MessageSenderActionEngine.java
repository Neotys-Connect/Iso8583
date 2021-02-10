package com.neotys.iso8583.customActions;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;



import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import org.jpos.iso.header.BaseHeader;


public class MessageSenderActionEngine implements ActionEngine {
	String MTI;
	String HOST;
	String PORT;
	String ControllerCode;
	String Header;
	BaseChannel channel;	
	long timeout = DEFAULT_TIMEOUT;

	private static final long DEFAULT_TIMEOUT = 10;
	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		SampleResult result = new SampleResult();
		
		String pattern = "Field(\\d+)";
		Pattern reg = Pattern.compile(pattern);
		HashMap< Integer,String> FieldList;
		FieldList = new HashMap< Integer,String>();
		byte[]  send_PackedRequestData;
		
		for(ActionParameter parameter:parameters) {
		
			switch(parameter.getName()) {
			case MessageSenderAction.TIMEOUT:
				try {
					timeout = Long.parseLong(parameter.getValue());
				} catch (final NumberFormatException e) {
					context.getLogger().warn("could not parse timeout " + parameter.getValue() + ", take default value "
							+ DEFAULT_TIMEOUT);
					timeout = DEFAULT_TIMEOUT;
				}
				break;

			case MessageSenderAction.ISOMTI:
				MTI = parameter.getValue();
				break;
			case MessageSenderAction.HOST:
				HOST = parameter.getValue();
				break;
			case MessageSenderAction.PORT:
				PORT = parameter.getValue();
				break;
			case MessageSenderAction.ControllerCode:
				ControllerCode = parameter.getValue();
				break;
			case MessageSenderAction.Header:
				Header = parameter.getValue();
				break;
			default:
				Matcher m = reg.matcher(parameter.getName());
				 if (m.find( ))
				 {
					 FieldList.put(Integer.valueOf(m.group(1)),parameter.getValue());
				 }
			}
			
		}
		
		
		if (Strings.isNullOrEmpty(HOST)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ MessageSenderAction.HOST + ".");
		}
		if (Strings.isNullOrEmpty(PORT)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ MessageSenderAction.PORT + ".");
		}
		if (Strings.isNullOrEmpty(MTI)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ MessageSenderAction.ISOMTI+ ".");
		}
		if (Strings.isNullOrEmpty(ControllerCode)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ MessageSenderAction.ControllerCode+ ".");
		}

	
		try{
			
			
			
			channel =(BaseChannel  )context.getCurrentVirtualUser().get(ControllerCode+"_"+HOST+":"+PORT+".SocketObj");
				if (channel.isConnected())
				{
					ISOMsg isoMsg = new ISOMsg();
					isoMsg.setPackager(channel.getPackager());

					if(Header !=null)
						channel.setHeader(Header.getBytes());

					isoMsg.setMTI(MTI);
					context.getLogger().debug("SetMIT");
					for(int key:FieldList.keySet())
					{
						context.getLogger().debug("key "+key+" and value:"+FieldList.get(key)+"\n");
						isoMsg.set(key,FieldList.get(key));
					}
					
					result.sampleStart();
					context.getLogger().debug("before sending");
		

					send_PackedRequestData=isoMsg.pack();
					
							
					context.getLogger().debug("data sent " + new String(send_PackedRequestData));
					
					
					channel.send(isoMsg);
				
					
					StringBuilder out = new StringBuilder();
					out.append("Response sent.\n")
					.append("MTI :"+isoMsg.getMTI()+" .\n");
					
					for (int j=1;j<=isoMsg.getMaxField();j++) {
						if (isoMsg.hasField(j)) {
							out.append("Field-"+j+" : "+isoMsg.getString(j)+"\n");
						}
					}
					out.append("message length: " + send_PackedRequestData.length+"\n");
					out.append("Hexa: " + bytesToHex(send_PackedRequestData)+"\n");
				
					context.getLogger().debug(out.toString());
					
					context.getLogger().debug("sent");
					
					
					context.getLogger().debug("receiving");
							
					// Unpacking it
					ISOMsg isorcp = receiveMessage(context);
				
					result.sampleEnd();
					
					StringBuilder output = new StringBuilder();
					output.append("Response received.\n")
					.append("MTI :"+isorcp.getMTI()+" .\n");
					
					for (int j=1;j<=isorcp.getMaxField();j++) {
						if (isorcp.hasField(j)) {
							output.append("Field-"+j+" : "+isorcp.getString(j)+"\n");
						}
					}
					
					result.setResponseContent(output.toString());	
					
				}
				else
				{
					return getErrorResult(context, result, "Socket connection needs to be done before " );
				}
			
		} catch (ISOException e) {
			return getErrorResult(context, result, "error ISO"+ e.getMessage());
		}
		catch (IOException  e) {
			return getErrorResult(context, result, "error socket"+ e.getMessage());
		}
		
		catch(Exception e)
		{
			return getErrorResult(context, result, "Socket connection needs to be done before "
					+ e.getMessage() );
			
		}
		return result;
	}
	private ISOMsg receiveMessage(final Context context)
			throws InterruptedException, ExecutionException, TimeoutException {
		final ExecutorService executor = Executors.newCachedThreadPool();
		Callable<ISOMsg> task = new Callable<ISOMsg>() {
			public ISOMsg call() {
				try {
					return channel.receive();
				} catch (IOException | ISOException e) {
					context.getLogger().error("error while receiving response", e);
				}
				return null;
			}
		};
		Future<ISOMsg> future = executor.submit(task);
		return future.get(timeout, TimeUnit.SECONDS);
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

	private static SampleResult getErrorResult(final com.neotys.extensions.action.engine.Context context,
			final SampleResult result, final String errorMessage) {
		result.setError(true);
		result.setStatusCode("NL-ISO8583Send_ACTION");
		result.setResponseContent(errorMessage);
		context.getLogger().error(errorMessage);
		return result;
	}
	@Override
	public void stopExecute() {
		// TODO Auto-generated method stub

	}


	///code generating the header
// public class TCPHeader { public static void main(String[] args)
// {
// TODO Auto-generated method stub byte [] by={30,38,30,30,82,38,00,00,00,00,00,00,04,00,00,00,00,00,00,00,30,35,31,38,32,32,30,32,32,34,31,38,31,39,35,37,30,30,30,32,32,34,30,35,31,39,33,30,31};
// byte[] res = new2ByteHeaderPacket(by);
// System.out.println(res.toString()); }
// public static byte[] new2ByteHeaderPacket(byte[] data)
// {
// int len = data.length;
// byte[] buf = new byte[len + 2];
// buf[0] = (byte)(len >> 8 & 0xFF);
// buf[1] = (byte)(len & 0xFF);




}
