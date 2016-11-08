
package uk.me.mjt.broadside;


public class TestResult {
    private long agentStartTimeUnixMillis;
    private long sourceBundleDownloadCompleteMillis;
    private long testEndTimeUnixMillis;
    
    private String awsRequestReference;
    private String surefireBooterStdout;

    public long getAgentStartTimeUnixMillis() {
        return agentStartTimeUnixMillis;
    }

    public void setAgentStartTimeUnixMillis(long agentStartTimeUnixMillis) {
        this.agentStartTimeUnixMillis = agentStartTimeUnixMillis;
    }

    public long getSourceBundleDownloadCompleteMillis() {
        return sourceBundleDownloadCompleteMillis;
    }

    public void setSourceBundleDownloadCompleteMillis(long sourceBundleDownloadCompleteMillis) {
        this.sourceBundleDownloadCompleteMillis = sourceBundleDownloadCompleteMillis;
    }

    public long getTestEndTimeUnixMillis() {
        return testEndTimeUnixMillis;
    }

    public void setTestEndTimeUnixMillis(long testEndTimeUnixMillis) {
        this.testEndTimeUnixMillis = testEndTimeUnixMillis;
    }

    public String getAwsRequestReference() {
        return awsRequestReference;
    }

    public void setAwsRequestReference(String awsRequestReference) {
        this.awsRequestReference = awsRequestReference;
    }

    public String getSurefireBooterStdout() {
        return surefireBooterStdout;
    }

    public void setSurefireBooterStdout(String surefireBooterStdout) {
        this.surefireBooterStdout = surefireBooterStdout;
    }
}
