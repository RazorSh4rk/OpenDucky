package Encoder;

import java.io.BufferedReader;
import java.io.StringReader;

public class Encoder {
	public static int DEVICE_SPEED; //0 - fast, 1 - not fast, 2 - you get the gist
	public Encoder() {
		
	}
	public static void setDeviceSpeed(int in) {
		DEVICE_SPEED = in;
	}
	public static String encode(String in) {
		String ret = "";
		BufferedReader reader = new BufferedReader(new StringReader(in));
		String line;
		try {
			while((line = reader.readLine()) != null) {
				if(line.length() > 0) {
					ret += process(line);
				}
			}
		}catch (Exception e) {
			//finished
		}
		
		return ret;
	}
	private static String process(String in) {
		String ret = "  ";
		switch (in.substring(0, 3)) {
			case "PRN": //print
				ret += "DigiKeyboard.print(\"";
				ret += in.substring(4, in.length());
				ret += "\");";
				break;
				
			case "PRL": //printline
				ret += "DigiKeyboard.println(\"";
				ret += in.substring(4, in.length());
				ret += "\");";
				break;
				
			case "SLP": //sleep
				ret += "DigiKeyboard.delay(";
				ret += in.substring(4, in.length());
				ret += ");";
				break;
				
			case "KST": //keystroke
				ret += "DigiKeyboard.sendKeyStroke(";
				ret += "KEY_";
				ret += in.substring(4, in.length());
				ret += ");";
				break;
				
			case "FUN": //key function left
				if(in.substring(4, in.length()).matches("ARROW")) {
					ret += "DigiKeyboard.sendKeyStroke(";
					ret += "KEY_ARROW_LEFT";
					ret += ");";
				}else if(in.substring(4, in.length()).matches("GUI")) {
					ret += "DigiKeyboard.sendKeyStroke(";
					ret += "MOD_GUI_LEFT";
					ret += ");";
				}else {
					ret += "DigiKeyboard.sendKeyStroke(0, ";
					ret += "MOD_";
					ret += in.substring(4, in.length());
					ret += "_LEFT";
					ret += ");";
				}
				break;
				
			/*	
			case "KFL": //key function left
				ret += "DigiKeyboard.sendKeyStroke(";
				ret += "MOD_";
				ret += in.substring(4, in.length());
				ret += "_LEFT";
				ret += ");";
				break;
			
			case "KFR": //key function right
				ret += "DigiKeyboard.sendKeyStroke(";
				ret += "MOD_";
				ret += in.substring(4, in.length());
				ret += "_RIGHT";
				ret += ");";
				break;
			*/	
				
			case "SPD":
				try{
					DEVICE_SPEED = Integer.parseInt(in.substring(4, 5));
				}catch (Exception e) {
					DEVICE_SPEED = 0;
				}
				break;
				
			default:
				ret = in;
				break;
			}
		if(DEVICE_SPEED != 0)
			ret += "\n  DigiKeyboard.delay(" + (DEVICE_SPEED * 500) + ");\n";
		else
			ret += "\n";
		return ret;
	}
	
	public static String beginFile() {
		return "#include \"DigiKeyboard.h\"\r\n" + 
				"\r\n" + 
				"void setup() {\r\n" + 
				"  pinMode(1, OUTPUT);\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"void loop() {\r\n" + 
				"  DigiKeyboard.sendKeyStroke(0);\r\n" + 
				"  DigiKeyboard.delay(1000);\r\n" + 
				"";
	}
	
	public static String endFile() {
		return "\n  for(;;){}\r\n" + 
				"}";
	}
}
