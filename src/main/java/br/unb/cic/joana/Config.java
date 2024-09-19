package br.unb.cic.joana;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.List;

public class Config {
    private String applicationClassPath;

    private String entryPointMethod;
    private List<String> thirdPartyLibraries;

    private List<String> sourceMethods;

    private List<String> sinkMethods;

    public Config() {}
    public Config(String applicationClassPath, String entryPointMethod, List<String> thirdPartyLibraries, List<String> sourceMethods, List<String> sinkMethods) {
        this.applicationClassPath = applicationClassPath;
        this.entryPointMethod = entryPointMethod;
        this.thirdPartyLibraries = thirdPartyLibraries;
        this.sourceMethods = sourceMethods;
        this.sinkMethods = sinkMethods;
    }

    public String getApplicationClassPath() {
        return applicationClassPath;
    }

    public void setApplicationClassPath(String applicationClassPath) {
        this.applicationClassPath = applicationClassPath;
    }

    public String getEntryPointMethod() {
        return this.entryPointMethod;
    }

    public void setEntryPointMethod(String entryPointMethod) {
        this.entryPointMethod = entryPointMethod;
    }

    public List<String> getThirdPartyLibraries() {
        return thirdPartyLibraries;
    }

    public void setThirdPartyLibraries(List<String> thirdPartyLibraries) {
        this.thirdPartyLibraries = thirdPartyLibraries;
    }

    public List<String> getSourceMethods() {
        return sourceMethods;
    }

    public void setSourceMethods(List<String> sourceMethods) {
        this.sourceMethods = sourceMethods;
    }

    public List<String> getSinkMethods() {
        return sinkMethods;
    }

    public void setSinkMethods(List<String> sinkMethods) {
        this.sinkMethods = sinkMethods;
    }

    @Override
    public String toString(){
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.writeValueAsString(this);
        }catch (Throwable t) {
            return "Error converting a config object.";
        }
    }
}
