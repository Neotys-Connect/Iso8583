package com.neotys.iso8583.disconnect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public class ISO8583DisconnectAction implements Action {
	static final String HOST= "Host";
	static final String PORT= "Port";
	static final String ControllerCode="ControllerCode";
	private static final String BUNDLE_NAME = "com.neotys.iso8583.disconnect.bundle";
    private static final String DISPLAY_NAME = ResourceBundle.getBundle("com.neotys.iso8583.disconnect.bundle", Locale.getDefault()).getString("displayName");
    private static final String DISPLAY_PATH = ResourceBundle.getBundle("com.neotys.iso8583.disconnect.bundle", Locale.getDefault()).getString("displayPath");
	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		List<ActionParameter> actionParameters=new ArrayList<ActionParameter>();
		actionParameters.add(new ActionParameter("Host","Host of the application"));
		actionParameters.add(new ActionParameter("Port","Port"));	
		actionParameters.add(new ActionParameter("ControllerCode","ControllerCode"));	
		return actionParameters;
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("ISO8583 disconnect allow you to disconnect to the remote server.\n")
			.append("Parameters are:\n")
			.append("- "+HOST+": Host of the application.\n")
			.append("- "+PORT+": Port.\n")
			.append("- "+ControllerCode+": Controller Code (VIS,CMN,FEP...etc)\n");
			
		
		
		return description.toString();
	}

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
		return ISO8583DisconnectActionEngine.class;
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
	public Optional<String> getMinimumNeoLoadVersion() {
		// TODO Auto-generated method stub
		 return Optional.of("5.1");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ISO8583 Disconnect";
	}

}
