
package com.song.generator;

import java.io.IOException;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class ServiceImplMaker {
    
    private ParserInfo parserInfo;
    public ServiceImplMaker(ParserInfo parserInfo) {
        this.parserInfo = parserInfo;
    }
    public void makeModel() throws IOException, Exception {
        Utils.writeContentToFile(
                Utils.createFile(parserInfo.getServiceImplPackage(),
                        parserInfo.getModelName(), "ServiceImpl.java"),
                Utils.generateDaoContent(parserInfo, "ServiceTemplateImpl.ftl"));
    }

}
