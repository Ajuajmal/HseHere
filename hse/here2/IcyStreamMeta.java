package hse.here2;

import com.google.android.gms.appinvite.PreviewActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IcyStreamMeta {
    private boolean isError = false;
    private Map<String, String> metadata;
    protected URL streamUrl;

    public IcyStreamMeta(URL streamUrl) {
        setStreamUrl(streamUrl);
    }

    public String getArtist() throws IOException {
        Map<String, String> data = getMetadata();
        if (data == null) {
            return "";
        }
        if (!data.containsKey("StreamTitle")) {
            return "";
        }
        try {
            String streamTitle = (String) data.get("StreamTitle");
            return streamTitle.substring(0, streamTitle.indexOf("-")).trim();
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getTitle() throws IOException {
        Map<String, String> data = getMetadata();
        if (data == null) {
            return "";
        }
        if (!data.containsKey("StreamTitle")) {
            return "";
        }
        try {
            String streamTitle = (String) data.get("StreamTitle");
            return streamTitle.substring(streamTitle.indexOf("-") + 1).trim();
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    public Map<String, String> getMetadata() throws IOException {
        if (this.metadata == null) {
            refreshMeta();
        }
        return this.metadata;
    }

    public void refreshMeta() throws IOException {
        retreiveMetadata();
    }

    private void retreiveMetadata() throws IOException {
        URLConnection con = this.streamUrl.openConnection();
        con.setRequestProperty("Icy-MetaData", "1");
        con.setRequestProperty("Connection", PreviewActivity.ON_CLICK_LISTENER_CLOSE);
        con.connect();
        int metaDataOffset = 0;
        Map<String, List<String>> headers = con.getHeaderFields();
        InputStream stream = con.getInputStream();
        if (headers.containsKey("icy-metaint")) {
            metaDataOffset = Integer.parseInt((String) ((List) headers.get("icy-metaint")).get(0));
        } else {
            StringBuilder strHeaders = new StringBuilder();
            String result = "";
            while (true) {
                char c = (char) stream.read();
                if (c == '￿') {
                    break;
                }
                result = result + c;
                strHeaders.append(c);
                if (result.length() > 4000 || (strHeaders.length() > 5 && strHeaders.substring(strHeaders.length() - 4, strHeaders.length()).equals("\r\n\r\n"))) {
                    break;
                }
            }
            Matcher m = Pattern.compile("\\r\\n(icy-metaint):\\s*(.*)\\r\\n").matcher(strHeaders.toString());
            if (m.find()) {
                metaDataOffset = Integer.parseInt(m.group(2));
            }
        }
        if (metaDataOffset == 0) {
            this.isError = true;
            return;
        }
        int count = 0;
        int metaDataLength = 4080;
        StringBuilder metaData = new StringBuilder();
        do {
            int b = stream.read();
            if (b == -1) {
                break;
            }
            boolean inData;
            count++;
            if (count == metaDataOffset + 1) {
                metaDataLength = b * 16;
            }
            if (count <= metaDataOffset + 1 || count >= metaDataOffset + metaDataLength) {
                inData = false;
            } else {
                inData = true;
            }
            if (inData && b != 0) {
                metaData.append((char) b);
            }
        } while (count <= metaDataOffset + metaDataLength);
        this.metadata = parseMetadata(metaData.toString());
        stream.close();
    }

    public boolean isError() {
        return this.isError;
    }

    public URL getStreamUrl() {
        return this.streamUrl;
    }

    public void setStreamUrl(URL streamUrl) {
        this.metadata = null;
        this.streamUrl = streamUrl;
        this.isError = false;
    }

    public static Map<String, String> parseMetadata(String metaString) {
        Map<String, String> metadata = new HashMap();
        String[] metaParts = metaString.split(";");
        Pattern p = Pattern.compile("^([a-zA-Z]+)=\\'([^\\']*)\\'$");
        for (CharSequence matcher : metaParts) {
            Matcher m = p.matcher(matcher);
            if (m.find()) {
                metadata.put(m.group(1), m.group(2));
            }
        }
        return metadata;
    }
}
