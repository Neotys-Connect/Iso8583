package com.neotys.iso8583.connect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public class ISo8583Connect implements Action {
	static final String HOST= "Host";
	static final String PORT= "Port";
	static final String ControllerCode= "ControllerCode";
	static final String TypeofChannel= "TypeofChannel";
	static final String AddMessageLengthIntoHeader ="AddMessagelengthintoHeader";
	static final String IncludeHeaderinBitmap ="IncludeHeaderinBitmap";
	
	private static final String BUNDLE_NAME = "com.neotys.iso8583.connect.bundle";
    private static final String DISPLAY_NAME = ResourceBundle.getBundle("com.neotys.iso8583.connect.bundle", Locale.getDefault()).getString("displayName");
    private static final String DISPLAY_PATH = ResourceBundle.getBundle("com.neotys.iso8583.connect.bundle", Locale.getDefault()).getString("displayPath");
	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		List<ActionParameter> actionParameters=new ArrayList<ActionParameter>();
		actionParameters.add(new ActionParameter("Host","Host of the application"));
		actionParameters.add(new ActionParameter("Port","Port"));
		actionParameters.add(new ActionParameter("ControllerCode","Controller Code"));
		actionParameters.add(new ActionParameter("TypeofChannel","Type of Channel ( ASCII,NCC,NAC...etc)"));
		actionParameters.add(new ActionParameter("AddMessageLenthIntoHeader","true"));
		actionParameters.add(new ActionParameter("IncludeHeaderinBitmap","true"));
		return actionParameters;
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("ISO8583 connect allow you to open a connetion to the remote server.\n")
			.append("Parameters are:\n")
			.append("- "+HOST+": Host of the application.\n")
			.append("- "+PORT+": Port.\n")
			.append("- "+ControllerCode+": Controller Code (VIS,CMN,FEP...etc)\n")
			.append("- "+TypeofChannel+": Type of Channel to use. Value possible : ASCII,NCC,NACC,BASE24,HEX,RPB,VAP,CS,AMEX\n")
			.append("- "+AddMessageLengthIntoHeader+": Boolean ( True or False). This will specify if the message lenth has to be added as the first information of the header\n")
			.append("- "+IncludeHeaderinBitmap+": Boolean ( true or false). This will specify if the header has to be included in the Bitmap ( the message would have the header value twice)\n");
		
		return description.toString();
	}

	@Override
	public String getDisplayName()
    {
        return DISPLAY_NAME;
    }

    public String getDisplayPath()
    {
        return DISPLAY_PATH;
    }

	@Override
	public Class<? extends ActionEngine> getEngineClass() {
		// TODO Auto-generated method stub
		return ISO8583ConnectEngine.class;
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<String> getMaximumNeoLoadVersion() {
		// TODO Auto-generated method stub
		return Optional.absent();
	}

	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ISO8583 Connect";
	}

	@Override
	public Optional<String> getMinimumNeoLoadVersion() {
		// TODO Auto-generated method stub
		 return Optional.of("5.1");
	}

}
