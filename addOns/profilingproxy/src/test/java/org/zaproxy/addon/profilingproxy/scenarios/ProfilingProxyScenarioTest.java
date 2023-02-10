package org.zaproxy.addon.profilingproxy.scenarios;

import org.jdesktop.swingx.JXTable;
import org.junit.jupiter.api.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.parosproxy.paros.network.HttpResponseHeader;
import org.zaproxy.addon.profilingproxy.extensionapp.ProfilingProxyListener;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.ProfilingProxyStatusPanel;
import org.zaproxy.addon.profilingproxy.utils.JSONLoader;
import org.zaproxy.addon.profilingproxy.ResourcesLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProfilingProxyScenarioTest {

    static String PNG_RESOURCES_PATH = JSONLoader.getLabel("pngResourcesPath");
    static String JSandCSS_RESOURCES_PATH = JSONLoader.getLabel("jsAndCssResourcesPath");

    /**
     * This method verifies that the metrics report is created and contains the desired information.
     */
    @Test
    void MetricsReportScenarioTest() throws HttpMalformedHeaderException {
        org.zaproxy.addon.profilingproxy.Parser parser = new org.zaproxy.addon.profilingproxy.Parser();
        ProfilingProxyStatusPanel statuspanel = new ProfilingProxyStatusPanel();
        ProfilingProxyListener proxy = new ProfilingProxyListener(statuspanel);
        HttpRequestHeader requestHeader = new HttpRequestHeader("GET https://www.example.com/ HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                "Accept-Language: nl,en-US;q=0.7,en;q=0.3\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n");
        HttpMessage msg = new HttpMessage(requestHeader);
        long oldTime = msg.getTimeSentMillis();
        proxy.onHttpRequestSend(msg);
        assertNotEquals(msg.getTimeSentMillis(), oldTime);
        HttpResponseHeader responseHeader = new HttpResponseHeader("HTTP/1.1 200 OK\n" +
                "X-GUploader-UploadID: ADPycdsko4g4L8emEkHCdr2I9DTXVheSii9ilAYa-IBNd3id_1CON42Epc7jcCdpnKsfW8-kSfFx2S-MINUnH8KIqMjBT4AM8wj8\n" +
                "Content-Type: image/png\n" +
                "Content-Disposition: attachment; filename=\"brain.jpg\"; filename*=UTF-8''brain.jpg\n" +
                "Access-Control-Allow-Origin: *\n" +
                "Access-Control-Allow-Credentials: false\n" +
                "Access-Control-Allow-Headers: Accept, Accept-Language, Authorization, Cache-Control, Content-Disposition, Content-Encoding, Content-Language, Content-Length, Content-MD5, Content-Range, Content-Type, Date, developer-token, financial-institution-id, X-Goog-Sn-Metadata, X-Goog-Sn-PatientId, GData-Version, google-cloud-resource-prefix, linked-customer-id, login-customer-id, x-goog-request-params, Host, If-Match, If-Modified-Since, If-None-Match, If-Unmodified-Since, Origin, OriginToken, Pragma, Range, request-id, Slug, Transfer-Encoding, hotrod-board-name, hotrod-chrome-cpu-model, hotrod-chrome-processors, Want-Digest, X-Ad-Manager-Impersonation, x-chrome-connected, X-ClientDetails, X-Client-Version, X-Firebase-Locale, X-Goog-Firebase-Installations-Auth, X-Firebase-Client, X-Firebase-Client-Log-Type, X-Firebase-GMPID, X-Firebase-Auth-Token, X-Firebase-AppCheck, X-Goog-Drive-Client-Version, X-Goog-Drive-Resource-Keys, X-GData-Client, X-GData-Key, X-GoogApps-Allowed-Domains, X-Goog-AdX-Buyer-Impersonation, X-Goog-Api-Client, X-Goog-Visibilities, X-Goog-AuthUser, x-goog-ext-124712974-jspb, x-goog-ext-467253834-jspb, x-goog-ext-251363160-jspb, x-goog-ext-259736195-jspb, x-goog-ext-477772811-jspb, X-Goog-PageId, X-Goog-Encode-Response-If-Executable, X-Goog-Correlation-Id, X-Goog-Request-Info, X-Goog-Request-Reason, X-Goog-Experiments, x-goog-iam-authority-selector, x-goog-iam-authorization-token, X-Goog-Spatula, X-Goog-Travel-Bgr, X-Goog-Travel-Settings, X-Goog-Upload-Command, X-Goog-Upload-Content-Disposition, X-Goog-Upload-Content-Length, X-Goog-Upload-Content-Type, X-Goog-Upload-File-Name, X-Goog-Upload-Header-Content-Encoding, X-Goog-Upload-Header-Content-Length, X-Goog-Upload-Header-Content-Type, X-Goog-Upload-Header-Transfer-Encoding, X-Goog-Upload-Offset, X-Goog-Upload-Protocol, x-goog-user-project, X-Goog-Visitor-Id, X-Goog-FieldMask, X-Google-Project-Override, X-Goog-Api-Key, X-HTTP-Method-Override, X-JavaScript-User-Agent, X-Pan-Versionid, X-Proxied-User-IP, X-Origin, X-Referer, X-Requested-With, X-Stadia-Client-Context, X-Upload-Content-Length, X-Upload-Content-Type, X-Use-Alt-Service, X-Use-HTTP-Status-Code-Override, X-Ios-Bundle-Identifier, X-Android-Package, X-Ariane-Xsrf-Token, X-YouTube-VVT, X-YouTube-Page-CL, X-YouTube-Page-Timestamp, X-Compass-Routing-Destination, x-framework-xsrf-token, X-Goog-Meeting-ABR, X-Goog-Meeting-Botguardid, X-Goog-Meeting-ClientInfo, X-Goog-Meeting-ClientVersion, X-Goog-Meeting-Debugid, X-Goog-Meeting-Identifier, X-Goog-Meeting-Interop-Cohorts, X-Goog-Meeting-Interop-Type, X-Goog-Meeting-RtcClient, X-Goog-Meeting-StartSource, X-Goog-Meeting-Token, X-Goog-Meeting-ViewerInfo, X-Goog-Meeting-Viewer-Token, X-Client-Data, x-sdm-id-token, X-Sfdc-Authorization, MIME-Version, Content-Transfer-Encoding, X-Earth-Engine-App-ID-Token, X-Earth-Engine-Computation-Profile, X-Earth-Engine-Computation-Profiling, X-Play-Console-Experiments-Override, X-Play-Console-Session-Id, x-alkali-account-key, x-alkali-application-key, x-alkali-auth-apps-namespace, x-alkali-auth-entities-namespace, x-alkali-auth-entity, x-alkali-client-locale, EES-S7E-MODE, cast-device-capabilities, X-Server-Timeout, x-foyer-client-environment, x-goog-greenenergyuserappservice-metadata, x-goog-sherlog-context\n" +
                "Access-Control-Allow-Methods: GET,HEAD,OPTIONS\n" +
                "Content-Length: 2875082\n" +
                "Date: Sat, 03 Dec 2022 22:38:14 GMT\n" +
                "Expires: Sat, 03 Dec 2022 22:38:14 GMT\n" +
                "Cache-Control: private, max-age=0\n" +
                "X-Goog-Hash: crc32c=yahwgg==\n" +
                "Server: UploadServer\n" +
                "Set-Cookie: AUTH_44fs6i0c68d4t1094g35uju70csiqe61=05444410299684938703|1670107050000|l029ht2j2vnfqlgna57uaj3s5cgilahb; Domain=.googleusercontent.com; Expires=Sat, 03-Dec-2022 22:43:14 GMT; Path=/docs/securesc/jjiuit0eqq25i4h2c3pfnejjtnfpbo48; Secure; HttpOnly; SameSite=none\n" +
                "Set-Cookie: AUTH_44fs6i0c68d4t1094g35uju70csiqe61_nonce=; Domain=doc-0s-5s-docs.googleusercontent.com; Expires=Sun, 08-Mar-2020 22:38:14 GMT; Path=/docs/securesc/jjiuit0eqq25i4h2c3pfnejjtnfpbo48; Secure; HttpOnly; SameSite=none\n" +
                "Alt-Svc: h3=\":443\"; ma=2592000,h3-29=\":443\"; ma=2592000,h3-Q050=\":443\"; ma=2592000,h3-Q046=\":443\"; ma=2592000,h3-Q043=\":443\"; ma=2592000,quic=\":443\"; ma=2592000; v=\"46,43\"\n");

        msg.setResponseHeader(responseHeader);
        proxy.onHttpResponseReceive(msg);
        JXTable metricsTable = statuspanel.getMetricsTable();
        int requestSizeColumn = 1;
        int responseSizeColumn = 2;
        int responseTimeColumn = 3;
        int smallestRow = 0;
        int largestRow = 1;
        int averageRow = 2;
        int medianRow = 3;
        int firstQuartileRow = 4;
        int thirdQuartileRow = 5;
        int standardDeviationRow = 6;
        org.zaproxy.addon.profilingproxy.Message parsedMsg = parser.parseMessage(msg);
        assertEquals(parsedMsg.getRequestSize(), metricsTable.getModel().getValueAt(smallestRow,requestSizeColumn));
        assertEquals(parsedMsg.getRequestSize(), metricsTable.getModel().getValueAt(largestRow,requestSizeColumn));
        assertEquals(parsedMsg.getRequestSize(), metricsTable.getModel().getValueAt(averageRow,requestSizeColumn));
        assertEquals(parsedMsg.getRequestSize(), metricsTable.getModel().getValueAt(medianRow,requestSizeColumn));
        assertEquals(parsedMsg.getRequestSize(), metricsTable.getModel().getValueAt(firstQuartileRow,requestSizeColumn));
        assertEquals(parsedMsg.getRequestSize(), metricsTable.getModel().getValueAt(thirdQuartileRow,requestSizeColumn));
        assertEquals(0, metricsTable.getModel().getValueAt(standardDeviationRow,requestSizeColumn));

        assertEquals(parsedMsg.getResponseSize(), metricsTable.getModel().getValueAt(smallestRow,responseSizeColumn));
        assertEquals(parsedMsg.getResponseSize(), metricsTable.getModel().getValueAt(largestRow,responseSizeColumn));
        assertEquals(parsedMsg.getResponseSize(), metricsTable.getModel().getValueAt(averageRow,responseSizeColumn));
        assertEquals(parsedMsg.getResponseSize(), metricsTable.getModel().getValueAt(medianRow,responseSizeColumn));
        assertEquals(parsedMsg.getResponseSize(), metricsTable.getModel().getValueAt(firstQuartileRow,responseSizeColumn));
        assertEquals(parsedMsg.getResponseSize(), metricsTable.getModel().getValueAt(thirdQuartileRow,responseSizeColumn));
        assertEquals(0, metricsTable.getModel().getValueAt(standardDeviationRow,responseSizeColumn));

        assertEquals(parsedMsg.getResponseTime(), metricsTable.getModel().getValueAt(smallestRow,responseTimeColumn));
        assertEquals(parsedMsg.getResponseTime(), metricsTable.getModel().getValueAt(largestRow,responseTimeColumn));
        assertEquals(parsedMsg.getResponseTime(), metricsTable.getModel().getValueAt(averageRow,responseTimeColumn));
        assertEquals(parsedMsg.getResponseTime(), metricsTable.getModel().getValueAt(medianRow,responseTimeColumn));
        assertEquals(parsedMsg.getResponseTime(), metricsTable.getModel().getValueAt(firstQuartileRow,responseTimeColumn));
        assertEquals(parsedMsg.getResponseTime(), metricsTable.getModel().getValueAt(thirdQuartileRow,responseTimeColumn));
        assertEquals(0, metricsTable.getModel().getValueAt(standardDeviationRow,responseTimeColumn));

    }

    /**
     * This method verifies that a size reduction is suggested as improvement when downloading a JS file, CSS file and PNG file
     * This method also verifies that a cache improvement is suggested when downloading the same file twice.
     */
    @Test
    void ImprovementsScenarioTest() throws IOException {
        ProfilingProxyStatusPanel statuspanel = new ProfilingProxyStatusPanel();
        statuspanel.getImprovementsTable();
        ProfilingProxyListener proxy = new ProfilingProxyListener(statuspanel);
        HttpRequestHeader requestHeader = new HttpRequestHeader("GET https://www.example.com/ HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                "Accept-Language: nl,en-US;q=0.7,en;q=0.3\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n");
        String PATH = ResourcesLoader.getResourcesPath(JSandCSS_RESOURCES_PATH);
        File file = new File(PATH + "js_template.js");
        HttpResponseHeader responseHeader = new HttpResponseHeader("HTTP/1.1 200 OK\n" +
                "X-GUploader-UploadID: ADPycdsJqPd9zvBNmBSzCojqGUv_WpDdF3ClfDZD5KXiIQv2IRQDnNLTSoeMxiq_JaOeN-RpyObPNalQaCHEEQoGV3yT1g\n" +
                "Content-Type: javascript\n" +
                "Content-Disposition: attachment; filename=\"js_template.js\"\"; filename*=UTF-8''js_template.js\"\n" +
                "Access-Control-Allow-Origin: *\n" +
                "Access-Control-Allow-Credentials: false\n" +
                "Access-Control-Allow-Headers: Accept, Accept-Language, Authorization, Cache-Control, Content-Disposition, Content-Encoding, Content-Language, Content-Length, Content-MD5, Content-Range, Content-Type, Date, developer-token, financial-institution-id, X-Goog-Sn-Metadata, X-Goog-Sn-PatientId, GData-Version, google-cloud-resource-prefix, linked-customer-id, login-customer-id, x-goog-request-params, Host, If-Match, If-Modified-Since, If-None-Match, If-Unmodified-Since, Origin, OriginToken, Pragma, Range, request-id, Slug, Transfer-Encoding, hotrod-board-name, hotrod-chrome-cpu-model, hotrod-chrome-processors, Want-Digest, X-Ad-Manager-Impersonation, x-chrome-connected, X-ClientDetails, X-Client-Version, X-Firebase-Locale, X-Goog-Firebase-Installations-Auth, X-Firebase-Client, X-Firebase-Client-Log-Type, X-Firebase-GMPID, X-Firebase-Auth-Token, X-Firebase-AppCheck, X-Goog-Drive-Client-Version, X-Goog-Drive-Resource-Keys, X-GData-Client, X-GData-Key, X-GoogApps-Allowed-Domains, X-Goog-AdX-Buyer-Impersonation, X-Goog-Api-Client, X-Goog-Visibilities, X-Goog-AuthUser, x-goog-ext-124712974-jspb, x-goog-ext-467253834-jspb, x-goog-ext-251363160-jspb, x-goog-ext-259736195-jspb, x-goog-ext-477772811-jspb, X-Goog-PageId, X-Goog-Encode-Response-If-Executable, X-Goog-Correlation-Id, X-Goog-Request-Info, X-Goog-Request-Reason, X-Goog-Experiments, x-goog-iam-authority-selector, x-goog-iam-authorization-token, X-Goog-Spatula, X-Goog-Travel-Bgr, X-Goog-Travel-Settings, X-Goog-Upload-Command, X-Goog-Upload-Content-Disposition, X-Goog-Upload-Content-Length, X-Goog-Upload-Content-Type, X-Goog-Upload-File-Name, X-Goog-Upload-Header-Content-Encoding, X-Goog-Upload-Header-Content-Length, X-Goog-Upload-Header-Content-Type, X-Goog-Upload-Header-Transfer-Encoding, X-Goog-Upload-Offset, X-Goog-Upload-Protocol, x-goog-user-project, X-Goog-Visitor-Id, X-Goog-FieldMask, X-Google-Project-Override, X-Goog-Api-Key, X-HTTP-Method-Override, X-JavaScript-User-Agent, X-Pan-Versionid, X-Proxied-User-IP, X-Origin, X-Referer, X-Requested-With, X-Stadia-Client-Context, X-Upload-Content-Length, X-Upload-Content-Type, X-Use-Alt-Service, X-Use-HTTP-Status-Code-Override, X-Ios-Bundle-Identifier, X-Android-Package, X-Ariane-Xsrf-Token, X-YouTube-VVT, X-YouTube-Page-CL, X-YouTube-Page-Timestamp, X-Compass-Routing-Destination, x-framework-xsrf-token, X-Goog-Meeting-ABR, X-Goog-Meeting-Botguardid, X-Goog-Meeting-ClientInfo, X-Goog-Meeting-ClientVersion, X-Goog-Meeting-Debugid, X-Goog-Meeting-Identifier, X-Goog-Meeting-Interop-Cohorts, X-Goog-Meeting-Interop-Type, X-Goog-Meeting-RtcClient, X-Goog-Meeting-StartSource, X-Goog-Meeting-Token, X-Goog-Meeting-ViewerInfo, X-Goog-Meeting-Viewer-Token, X-Client-Data, x-sdm-id-token, X-Sfdc-Authorization, MIME-Version, Content-Transfer-Encoding, X-Earth-Engine-App-ID-Token, X-Earth-Engine-Computation-Profile, X-Earth-Engine-Computation-Profiling, X-Play-Console-Experiments-Override, X-Play-Console-Session-Id, x-alkali-account-key, x-alkali-application-key, x-alkali-auth-apps-namespace, x-alkali-auth-entities-namespace, x-alkali-auth-entity, x-alkali-client-locale, EES-S7E-MODE, cast-device-capabilities, X-Server-Timeout, x-foyer-client-environment, x-goog-greenenergyuserappservice-metadata, x-goog-sherlog-context\n" +
                "Access-Control-Allow-Methods: GET,HEAD,OPTIONS\n" +
                "Content-Length: 612\n" +
                "Date: Sat, 03 Dec 2022 22:38:20 GMT\n" +
                "Expires: Sat, 03 Dec 2022 22:38:20 GMT\n" +
                "Cache-Control: private, max-age=0\n" +
                "X-Goog-Hash: crc32c=S8/wAg==\n" +
                "Server: UploadServer\n" +
                "Alt-Svc: h3=\":443\"; ma=2592000,h3-29=\":443\"; ma=2592000,h3-Q050=\":443\"; ma=2592000,h3-Q046=\":443\"; ma=2592000,h3-Q043=\":443\"; ma=2592000,quic=\":443\"; ma=2592000; v=\"46,43\"\n");

        HttpMessage msg = new HttpMessage(requestHeader);
        proxy.onHttpRequestSend(msg);
        msg.setResponseBody(Files.readAllBytes(file.toPath()));
        msg.setResponseHeader(responseHeader);

        proxy.onHttpResponseReceive(msg);
        JXTable improvementsTable = statuspanel.getImprovementsTable();
        assertEquals("Reduce JS File", improvementsTable.getValueAt(0,0));
        assertEquals("51", improvementsTable.getValueAt(0,1));
        assertEquals("https://www.example.com/",improvementsTable.getValueAt(0,2));
        HttpRequestHeader requestHeader2 = new HttpRequestHeader("GET https://www.example.com/ HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                "Accept-Language: nl,en-US;q=0.7,en;q=0.3\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n");
        File file2 = new File(PATH + "css_template.css");
        HttpResponseHeader responseHeader2 = new HttpResponseHeader("HTTP/1.1 200 OK\n" +
                "X-GUploader-UploadID: ADPycdsJqPd9zvBNmBSzCojqGUv_WpDdF3ClfDZD5KXiIQv2IRQDnNLTSoeMxiq_JaOeN-RpyObPNalQaCHEEQoGV3yT1g\n" +
                "Content-Type: css\n" +
                "Content-Disposition: attachment; filename=\"css_template.css\"\"; filename*=UTF-8''css_template.css\"\n" +
                "Access-Control-Allow-Origin: *\n" +
                "Access-Control-Allow-Credentials: false\n" +
                "Access-Control-Allow-Headers: Accept, Accept-Language, Authorization, Cache-Control, Content-Disposition, Content-Encoding, Content-Language, Content-Length, Content-MD5, Content-Range, Content-Type, Date, developer-token, financial-institution-id, X-Goog-Sn-Metadata, X-Goog-Sn-PatientId, GData-Version, google-cloud-resource-prefix, linked-customer-id, login-customer-id, x-goog-request-params, Host, If-Match, If-Modified-Since, If-None-Match, If-Unmodified-Since, Origin, OriginToken, Pragma, Range, request-id, Slug, Transfer-Encoding, hotrod-board-name, hotrod-chrome-cpu-model, hotrod-chrome-processors, Want-Digest, X-Ad-Manager-Impersonation, x-chrome-connected, X-ClientDetails, X-Client-Version, X-Firebase-Locale, X-Goog-Firebase-Installations-Auth, X-Firebase-Client, X-Firebase-Client-Log-Type, X-Firebase-GMPID, X-Firebase-Auth-Token, X-Firebase-AppCheck, X-Goog-Drive-Client-Version, X-Goog-Drive-Resource-Keys, X-GData-Client, X-GData-Key, X-GoogApps-Allowed-Domains, X-Goog-AdX-Buyer-Impersonation, X-Goog-Api-Client, X-Goog-Visibilities, X-Goog-AuthUser, x-goog-ext-124712974-jspb, x-goog-ext-467253834-jspb, x-goog-ext-251363160-jspb, x-goog-ext-259736195-jspb, x-goog-ext-477772811-jspb, X-Goog-PageId, X-Goog-Encode-Response-If-Executable, X-Goog-Correlation-Id, X-Goog-Request-Info, X-Goog-Request-Reason, X-Goog-Experiments, x-goog-iam-authority-selector, x-goog-iam-authorization-token, X-Goog-Spatula, X-Goog-Travel-Bgr, X-Goog-Travel-Settings, X-Goog-Upload-Command, X-Goog-Upload-Content-Disposition, X-Goog-Upload-Content-Length, X-Goog-Upload-Content-Type, X-Goog-Upload-File-Name, X-Goog-Upload-Header-Content-Encoding, X-Goog-Upload-Header-Content-Length, X-Goog-Upload-Header-Content-Type, X-Goog-Upload-Header-Transfer-Encoding, X-Goog-Upload-Offset, X-Goog-Upload-Protocol, x-goog-user-project, X-Goog-Visitor-Id, X-Goog-FieldMask, X-Google-Project-Override, X-Goog-Api-Key, X-HTTP-Method-Override, X-JavaScript-User-Agent, X-Pan-Versionid, X-Proxied-User-IP, X-Origin, X-Referer, X-Requested-With, X-Stadia-Client-Context, X-Upload-Content-Length, X-Upload-Content-Type, X-Use-Alt-Service, X-Use-HTTP-Status-Code-Override, X-Ios-Bundle-Identifier, X-Android-Package, X-Ariane-Xsrf-Token, X-YouTube-VVT, X-YouTube-Page-CL, X-YouTube-Page-Timestamp, X-Compass-Routing-Destination, x-framework-xsrf-token, X-Goog-Meeting-ABR, X-Goog-Meeting-Botguardid, X-Goog-Meeting-ClientInfo, X-Goog-Meeting-ClientVersion, X-Goog-Meeting-Debugid, X-Goog-Meeting-Identifier, X-Goog-Meeting-Interop-Cohorts, X-Goog-Meeting-Interop-Type, X-Goog-Meeting-RtcClient, X-Goog-Meeting-StartSource, X-Goog-Meeting-Token, X-Goog-Meeting-ViewerInfo, X-Goog-Meeting-Viewer-Token, X-Client-Data, x-sdm-id-token, X-Sfdc-Authorization, MIME-Version, Content-Transfer-Encoding, X-Earth-Engine-App-ID-Token, X-Earth-Engine-Computation-Profile, X-Earth-Engine-Computation-Profiling, X-Play-Console-Experiments-Override, X-Play-Console-Session-Id, x-alkali-account-key, x-alkali-application-key, x-alkali-auth-apps-namespace, x-alkali-auth-entities-namespace, x-alkali-auth-entity, x-alkali-client-locale, EES-S7E-MODE, cast-device-capabilities, X-Server-Timeout, x-foyer-client-environment, x-goog-greenenergyuserappservice-metadata, x-goog-sherlog-context\n" +
                "Access-Control-Allow-Methods: GET,HEAD,OPTIONS\n" +
                "Content-Length: 612\n" +
                "Date: Sat, 03 Dec 2022 22:38:20 GMT\n" +
                "Expires: Sat, 03 Dec 2022 22:38:20 GMT\n" +
                "Cache-Control: private, max-age=0\n" +
                "X-Goog-Hash: crc32c=S8/wAg==\n" +
                "Server: UploadServer\n" +
                "Alt-Svc: h3=\":443\"; ma=2592000,h3-29=\":443\"; ma=2592000,h3-Q050=\":443\"; ma=2592000,h3-Q046=\":443\"; ma=2592000,h3-Q043=\":443\"; ma=2592000,quic=\":443\"; ma=2592000; v=\"46,43\"\n");

        HttpMessage msg2 = new HttpMessage(requestHeader2);
        proxy.onHttpRequestSend(msg2);
        msg2.setResponseBody(Files.readAllBytes(file2.toPath()));
        msg2.setResponseHeader(responseHeader2);

        proxy.onHttpResponseReceive(msg2);
        assertEquals("Reduce JS File", improvementsTable.getValueAt(0,0));
        assertEquals("51", improvementsTable.getValueAt(0,1));
        assertEquals("https://www.example.com/",improvementsTable.getValueAt(0,2));
        assertEquals("Reduce CSS File", improvementsTable.getValueAt(1,0));
        assertEquals("14", improvementsTable.getValueAt(1,1));
        assertEquals("https://www.example.com/",improvementsTable.getValueAt(1,2));
        HttpRequestHeader requestHeader3 = new HttpRequestHeader("GET https://www.dummy.com/ HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                "Accept-Language: nl,en-US;q=0.7,en;q=0.3\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n");
        String PNGPATH = ResourcesLoader.getResourcesPath(PNG_RESOURCES_PATH);
        File file3= new File(PNGPATH + "doctor_50p_chance_die_make_100p.png");
        HttpResponseHeader responseHeader3 = new HttpResponseHeader("HTTP/1.1 200 OK\n" +
                "X-GUploader-UploadID: ADPycdsJqPd9zvBNmBSzCojqGUv_WpDdF3ClfDZD5KXiIQv2IRQDnNLTSoeMxiq_JaOeN-RpyObPNalQaCHEEQoGV3yT1g\n" +
                "Content-Type: image/png\n" +
                "Content-Disposition: attachment; filename=\"doctor_50p_chance_die_make_100p.png\"\"; filename*=UTF-8''doctor_50p_chance_die_make_100p.png\"\n" +
                "Access-Control-Allow-Origin: *\n" +
                "Access-Control-Allow-Credentials: false\n" +
                "Access-Control-Allow-Headers: Accept, Accept-Language, Authorization, Cache-Control, Content-Disposition, Content-Encoding, Content-Language, Content-Length, Content-MD5, Content-Range, Content-Type, Date, developer-token, financial-institution-id, X-Goog-Sn-Metadata, X-Goog-Sn-PatientId, GData-Version, google-cloud-resource-prefix, linked-customer-id, login-customer-id, x-goog-request-params, Host, If-Match, If-Modified-Since, If-None-Match, If-Unmodified-Since, Origin, OriginToken, Pragma, Range, request-id, Slug, Transfer-Encoding, hotrod-board-name, hotrod-chrome-cpu-model, hotrod-chrome-processors, Want-Digest, X-Ad-Manager-Impersonation, x-chrome-connected, X-ClientDetails, X-Client-Version, X-Firebase-Locale, X-Goog-Firebase-Installations-Auth, X-Firebase-Client, X-Firebase-Client-Log-Type, X-Firebase-GMPID, X-Firebase-Auth-Token, X-Firebase-AppCheck, X-Goog-Drive-Client-Version, X-Goog-Drive-Resource-Keys, X-GData-Client, X-GData-Key, X-GoogApps-Allowed-Domains, X-Goog-AdX-Buyer-Impersonation, X-Goog-Api-Client, X-Goog-Visibilities, X-Goog-AuthUser, x-goog-ext-124712974-jspb, x-goog-ext-467253834-jspb, x-goog-ext-251363160-jspb, x-goog-ext-259736195-jspb, x-goog-ext-477772811-jspb, X-Goog-PageId, X-Goog-Encode-Response-If-Executable, X-Goog-Correlation-Id, X-Goog-Request-Info, X-Goog-Request-Reason, X-Goog-Experiments, x-goog-iam-authority-selector, x-goog-iam-authorization-token, X-Goog-Spatula, X-Goog-Travel-Bgr, X-Goog-Travel-Settings, X-Goog-Upload-Command, X-Goog-Upload-Content-Disposition, X-Goog-Upload-Content-Length, X-Goog-Upload-Content-Type, X-Goog-Upload-File-Name, X-Goog-Upload-Header-Content-Encoding, X-Goog-Upload-Header-Content-Length, X-Goog-Upload-Header-Content-Type, X-Goog-Upload-Header-Transfer-Encoding, X-Goog-Upload-Offset, X-Goog-Upload-Protocol, x-goog-user-project, X-Goog-Visitor-Id, X-Goog-FieldMask, X-Google-Project-Override, X-Goog-Api-Key, X-HTTP-Method-Override, X-JavaScript-User-Agent, X-Pan-Versionid, X-Proxied-User-IP, X-Origin, X-Referer, X-Requested-With, X-Stadia-Client-Context, X-Upload-Content-Length, X-Upload-Content-Type, X-Use-Alt-Service, X-Use-HTTP-Status-Code-Override, X-Ios-Bundle-Identifier, X-Android-Package, X-Ariane-Xsrf-Token, X-YouTube-VVT, X-YouTube-Page-CL, X-YouTube-Page-Timestamp, X-Compass-Routing-Destination, x-framework-xsrf-token, X-Goog-Meeting-ABR, X-Goog-Meeting-Botguardid, X-Goog-Meeting-ClientInfo, X-Goog-Meeting-ClientVersion, X-Goog-Meeting-Debugid, X-Goog-Meeting-Identifier, X-Goog-Meeting-Interop-Cohorts, X-Goog-Meeting-Interop-Type, X-Goog-Meeting-RtcClient, X-Goog-Meeting-StartSource, X-Goog-Meeting-Token, X-Goog-Meeting-ViewerInfo, X-Goog-Meeting-Viewer-Token, X-Client-Data, x-sdm-id-token, X-Sfdc-Authorization, MIME-Version, Content-Transfer-Encoding, X-Earth-Engine-App-ID-Token, X-Earth-Engine-Computation-Profile, X-Earth-Engine-Computation-Profiling, X-Play-Console-Experiments-Override, X-Play-Console-Session-Id, x-alkali-account-key, x-alkali-application-key, x-alkali-auth-apps-namespace, x-alkali-auth-entities-namespace, x-alkali-auth-entity, x-alkali-client-locale, EES-S7E-MODE, cast-device-capabilities, X-Server-Timeout, x-foyer-client-environment, x-goog-greenenergyuserappservice-metadata, x-goog-sherlog-context\n" +
                "Access-Control-Allow-Methods: GET,HEAD,OPTIONS\n" +
                "Content-Length: 612\n" +
                "Date: Sat, 03 Dec 2022 22:38:20 GMT\n" +
                "Expires: Sat, 03 Dec 2022 22:38:20 GMT\n" +
                "Cache-Control: private, max-age=0\n" +
                "X-Goog-Hash: crc32c=S8/wAg==\n" +
                "Server: UploadServer\n" +
                "Alt-Svc: h3=\":443\"; ma=2592000,h3-29=\":443\"; ma=2592000,h3-Q050=\":443\"; ma=2592000,h3-Q046=\":443\"; ma=2592000,h3-Q043=\":443\"; ma=2592000,quic=\":443\"; ma=2592000; v=\"46,43\"\n");

        HttpMessage msg3 = new HttpMessage(requestHeader3);
        proxy.onHttpRequestSend(msg3);
        msg3.setResponseBody(Files.readAllBytes(file3.toPath()));
        msg3.setResponseHeader(responseHeader3);
        proxy.onHttpResponseReceive(msg3);
        assertEquals("Reduce JS File", improvementsTable.getValueAt(0,0));
        assertEquals("51", improvementsTable.getValueAt(0,1));
        assertEquals("https://www.example.com/",improvementsTable.getValueAt(0,2));
        assertEquals("Reduce CSS File", improvementsTable.getValueAt(1,0));
        assertEquals("14", improvementsTable.getValueAt(1,1));
        assertEquals("https://www.example.com/",improvementsTable.getValueAt(1,2));
        assertEquals("Reduce PNG File", improvementsTable.getValueAt(2,0));
        assertEquals("169327", improvementsTable.getValueAt(2,1));
        assertEquals("https://www.dummy.com/",improvementsTable.getValueAt(2,2));

        HttpRequestHeader requestHeader4 = new HttpRequestHeader("GET https://www.dummy2.com/ HTTP/1.1\n" +
                "Host: www.example.com\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\n" +
                "Accept-Language: nl,en-US;q=0.7,en;q=0.3\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n");
        HttpMessage msg4 = new HttpMessage(requestHeader4);
        proxy.onHttpRequestSend(msg3);
        msg4.setResponseBody(Files.readAllBytes(file3.toPath()));
        msg4.setResponseHeader(responseHeader3);
        proxy.onHttpResponseReceive(msg3);
        assertEquals("Reduce JS File", improvementsTable.getValueAt(0,0));
        assertEquals("51", improvementsTable.getValueAt(0,1));
        assertEquals("https://www.example.com/",improvementsTable.getValueAt(0,2));
        assertEquals("Reduce CSS File", improvementsTable.getValueAt(1,0));
        assertEquals("14", improvementsTable.getValueAt(1,1));
        assertEquals("https://www.example.com/",improvementsTable.getValueAt(1,2));
        assertEquals("Reduce PNG File", improvementsTable.getValueAt(2,0));
        assertEquals("169327", improvementsTable.getValueAt(2,1));
        assertEquals("https://www.dummy.com/",improvementsTable.getValueAt(2,2));
        assertEquals("Caching Optimization", improvementsTable.getValueAt(3,0));
    }
}


