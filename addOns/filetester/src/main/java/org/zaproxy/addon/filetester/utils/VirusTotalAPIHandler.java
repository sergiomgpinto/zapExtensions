package org.zaproxy.addon.filetester.utils;

import org.zaproxy.addon.filetester.utils.JSONLoader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;


/**
 * This class provides methods to work with the Virus Total API.
 */
public class VirusTotalAPIHandler {

    static final String apiKey = "64df711e8fbfba7a1147900906c0b7d0561d8eb3e1c49f660a93cda4477bb5cb";
    static final String scanEndpoint = "https://www.virustotal.com/vtapi/v2/file/scan";
    static final String reportEndpoint = "https://www.virustotal.com/vtapi/v2/file/report";


    /**
     *
     * This method allows you to send a file for scanning with VirusTotal.
     * @param filePath: the path of the file you want to scan
     * @return ScanInfo
     * example =
     * {
     *  'permalink': 'https://www.virustotal.com/file/d140c...244ef892e5/analysis/1359112395/',
     *  'resource': 'd140c244ef892e59c7f68bd0c6f74bb711032563e2a12fa9dda5b760daecd556',
     *  'response_code': 1,
     *  'scan_id': 'd140c244ef892e59c7f68bd0c6f74bb711032563e2a12fa9dda5b760daecd556-1359112395',
     *  'verbose_msg': 'Scan request successfully queued, come back later for the report',
     *  'sha256': 'd140c244ef892e59c7f68bd0c6f74bb711032563e2a12fa9dda5b760daecd556'
     * }
     * @throws IOException
     * @throws InterruptedException
     */
    public static String scan(String filePath) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(scanEndpoint)).header("accept", "text/plain")
                .header("content-type", "application/x-www-form-urlencoded")
                .method("POST", HttpRequest.BodyPublishers.ofString(MessageFormat.format("apikey={0}&file={1}", apiKey,filePath)))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println("response body = " + response.body());
        return response.body();

    }

    /**
     * This method retrieves the file scan report for a given resource.
     *
     * @param resource: the id of the resource you want to get the report from
     * @return report
     * example =
     * {
     *  'response_code': 1,
     *  'verbose_msg': 'Scan finished, scan information embedded in this object',
     *  'resource': '99017f6eebbac24f351415dd410d522d',
     *  'scan_id': '52d3df0ed60c46f336c131bf2ca454f73bafdc4b04dfa2aea80746f5ba9e6d1c-1273894724',
     *  'md5': '99017f6eebbac24f351415dd410d522d',
     *  'sha1': '4d1740485713a2ab3a4f5822a01f645fe8387f92',
     *  'sha256': '52d3df0ed60c46f336c131bf2ca454f73bafdc4b04dfa2aea80746f5ba9e6d1c',
     *  'scan_date': '2010-05-15 03:38:44',
     *  'permalink': 'https://www.virustotal.com/file/52d3df0ed60c46f336c131bf2ca454f73bafdc4b04dfa2aea80746f5ba9e6d1c/analysis/1273894724/',
     *  'positives': 40,
     *  'total': 40,
     *  'scans': {
     *    'nProtect': {
     *      'detected': true,
     *      'version': '2010-05-14.01',
     *      'result': 'Trojan.Generic.3611249',
     *      'update': '20100514'
     *    },
     *    'CAT-QuickHeal': {
     *      'detected': true,
     *      'version': '10.00',
     *      'result': 'Trojan.VB.acgy',
     *      'update': '20100514'
     *    },
     *    'McAfee': {
     *      'detected': true,
     *      'version': '5.400.0.1158',
     *      'result': 'Generic.dx!rkx',
     *      'update': '20100515'
     *    },
     *    'TheHacker': {
     *      'detected': true,
     *      'version': '6.5.2.0.280',
     *      'result': 'Trojan/VB.gen',
     *      'update': '20100514'
     *    },
     *    'VirusBuster': {
     *     'detected': true,
     *      'version': '5.0.27.0',
     *      'result': 'Trojan.VB.JFDE',
     *      'update': '20100514'
     *    }
     *  }
     * }
     * @throws IOException
     * @throws InterruptedException
     */
    public static String report(String resource) throws IOException, InterruptedException {
        // resource = "529d6b679d76a2cca3c35cd6c6f1b9d5677f90102b83d6c45b8352b529522d71";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MessageFormat.format(reportEndpoint + "?apikey={0}&resource={1}", apiKey, resource)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println("response body report = " + response.body());
        return response.body();
    }

    /**
     *
     * @param report: the report you want to check
     * @return positives: the amount of positive scans (positive = unsafe)
     */
    public static Integer checkReportForPositives(String report) {
        Integer positives = (Integer) JSONLoader.getAttributeFromJson(report,"positives");
        // System.out.println("positives = " + positives);
        return positives;
    }

    /**
     * This method retrieves the resource from a given scanInfo
     *
     * @param scanInfo:
     * @return resource
     */
    public static String getResourceIdFromScanInfo(String scanInfo) {
        String resource = (String) JSONLoader.getAttributeFromJson(scanInfo,"resource");
        return resource;
    }
}
