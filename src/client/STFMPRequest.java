package client;

public class STFMPRequest {
    final private String VERSION = "STFMP/1.0";
    private String actions;
    private String fileName;
    private String fileContents;

    public STFMPRequest(String actions, String fileName, String fileContents) {
        this.actions = actions;
        this.fileName = fileName;
        this.fileContents = fileContents;
    }
    
    public String getRequest() {
        String raw_request = String.format("%s##%s##%s#%s", VERSION, actions, fileName, fileContents) ;
        return raw_request;
    }

}

