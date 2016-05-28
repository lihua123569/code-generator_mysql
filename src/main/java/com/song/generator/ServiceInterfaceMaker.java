
package com.song.generator;

import java.io.IOException;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class ServiceInterfaceMaker {

    private ParserInfo parserInfo;

    public ServiceInterfaceMaker(ParserInfo parserInfo) {
        this.parserInfo = parserInfo;
    }

    public void makeModel() throws IOException, Exception {
        Utils.writeContentToFile(
                Utils.createFile(
                        parserInfo.getServicePackage(),parserInfo.getModelName(), "Service.java"),
                Utils.generateDaoContent(parserInfo, "ServiceTemplate.ftl"));
    }

}
