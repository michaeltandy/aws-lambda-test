
package uk.me.mjt.broadside;

import java.util.List;

public class TestSettings {
    private String testCodeBundle;
    private String surefirePropertiesFile;
    private List<String> arglineParameters;

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
}
