package main.java.TalkBox.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

import main.java.TalkBox.view.TalkBoxGui;

public class Main {
	public final static Logger LOG = Logger.getGlobal();

	public static void main(String[] args) {
		 //=============================================
        // Delete basic log
        //------------
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }
        //=============================================
        
        LOG.setLevel(Level.INFO);
        
        Handler handler = new ConsoleHandler();
        CustomLogFormatter formatter = new CustomLogFormatter();
        handler.setFormatter(formatter);
        LOG.addHandler(handler);
        
        LOG.info("App started");
        
        TalkBoxGui talkBoxGui = new TalkBoxGui();
        talkBoxGui.setVisible(true);
	}
}

class CustomLogFormatter extends Formatter {
    public String getHead(Handler h) {
        return "START LOG\n";
    }
    
    public String format(LogRecord rec) {
        StringBuffer buf = new StringBuffer(1000);
 
        buf.append(calcDate(rec.getMillis()));
        
        buf.append(" [");
        buf.append(rec.getLevel());
        buf.append("] ");
        
        buf.append("[");
        buf.append(rec.getSourceMethodName());
        buf.append("] ");
        
        buf.append(rec.getMessage());
        buf.append("\n");
        
        return buf.toString();
    }
 
    public String getTail(Handler h) {
        return "END LOG\n";
    }
    
    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}