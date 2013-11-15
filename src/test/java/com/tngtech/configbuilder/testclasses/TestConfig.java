package com.tngtech.configbuilder.testclasses;

import com.tngtech.configbuilder.annotation.configuration.LoadingOrder;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyExtension;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertySuffixes;
import com.tngtech.configbuilder.annotation.typetransformer.*;
import com.tngtech.configbuilder.annotation.validation.Validation;
import com.tngtech.configbuilder.annotation.valueextractor.*;
import com.tngtech.propertyloader.PropertyLoader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PropertyExtension("testproperties")
@PropertySuffixes(extraSuffixes = {"test"})
@PropertyLocations(resourcesForClasses = {PropertyLoader.class})
@PropertiesFiles("demoapp-configuration")
@LoadingOrder(value = {CommandLineValue.class, PropertyValue.class, EnvironmentVariableValue.class, SystemPropertyValue.class, DefaultValue.class})
public class TestConfig {

    public TestConfig() {

    }

    public Collection<Path> getPathCollection() {
        return pathCollection;
    }

    public void setPathCollection(Collection<Path> pathCollection) {
        this.pathCollection = pathCollection;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public ArrayList<TestConfig> getTestConfigList() {
        return testConfigList;
    }

    public class TestConfigFactory extends TypeTransformer<String,TestConfig> {
        public TestConfig transform(String input) {
            TestConfig testConfig = new TestConfig();
            testConfig.setSomeString(input);
            return testConfig;
        }
    }

    @DefaultValue("3")
    private int someNumber;

    @PropertyValue("a")
    private String someString;

    @CommandLineValue(shortOpt = "u", longOpt = "user")
    private boolean aBoolean;

    @LoadingOrder(value = {CommandLineValue.class})
    @CommandLineValue(shortOpt = "c", longOpt = "collection", hasArg = true, description = "command line option description")
    @TypeTransformers({CommaSeparatedStringToStringCollectionTransformer.class})
    private Collection<String> stringCollection;

    @DefaultValue("/etc,/usr")
    private Collection<Path> pathCollection;

    @DefaultValue("1,2,3,4,5")
    private List<Integer> integerList;

    @TypeTransformers(TestConfigFactory.class)
    private ArrayList<TestConfig> testConfigList;

    @EnvironmentVariableValue("HOME")
    private Path homeDir;

    @SystemPropertyValue("user.language")
    private String systemProperty;

    public void setSomeNumber(Integer someNumber) {
        this.someNumber = someNumber;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

    public void setBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public void setStringCollection(Collection<String> stringCollection) {
        this.stringCollection = stringCollection;
    }

    public void setHomeDir(Path homeDir) {
        this.homeDir = homeDir;
    }

    public void setSystemProperty(String systemProperty) {
        this.systemProperty = systemProperty;
    }

    @Validation
    private void validate() {

    }
}
