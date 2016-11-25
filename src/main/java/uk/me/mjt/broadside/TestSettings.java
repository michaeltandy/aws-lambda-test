
package uk.me.mjt.broadside;

import java.util.List;

public class TestSettings {
    private String testCodeBundle;
    private String surefirePropertiesFile;
    private List<String> arglineParameters;
    private int forkedProcessTimeoutInSeconds;

    public String getTestCodeBundle() {
        return testCodeBundle;
    }

    public void setTestCodeBundle(String testCodeBundle) {
        this.testCodeBundle = testCodeBundle;
    }

    public String getSurefirePropertiesFile() {
        return surefirePropertiesFile;
    }

    public void setSurefirePropertiesFile(String surefirePropertiesFile) {
        this.surefirePropertiesFile = surefirePropertiesFile;
    }

    public List<String>getArglineParameters() {
        return arglineParameters;
    }

    public void setArglineParameters(List<String> arglineParameters) {
        this.arglineParameters = arglineParameters;
    }

    public int getForkedProcessTimeoutInSeconds() {
        return forkedProcessTimeoutInSeconds;
    }

    public void setForkedProcessTimeoutInSeconds(int forkedProcessTimeoutInSeconds) {
        this.forkedProcessTimeoutInSeconds = forkedProcessTimeoutInSeconds;
    }
}
