package client;

public class STFMPResponse {
    private String VERSION = "STFMP/1.0";
    private String status;
    private String fileName;
    private String contents;
    public STFMPResponse(String rawResponse) {
        String[] lines = rawResponse.split("##");
        VERSION = lines[0];
        status = lines[1];
        String data = lines[2];
        String[] parts = data.split("#");
        if (parts.length !=2) {
            fileName =null;
            contents = data;
        }else{
            fileName = parts[0];
            contents = parts[1];
        }
        
    }

    public String getVersion() {
        return VERSION;
    }

    public String getStatus() {
        return status;
    }

    public String getFileName() {
        return fileName;
    }
    
    public String getContents() {
        return contents;
    }
}

